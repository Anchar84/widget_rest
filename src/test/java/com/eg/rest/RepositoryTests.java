package com.eg.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.eg.rest.repository.InMemoryWidgetRepository;
import com.eg.rest.repository.WidgetModel;
import com.eg.rest.repository.WidgetRepository;

public class RepositoryTests {

    private WidgetRepository repository;

    @Before
    public void testInit() {
        repository = new InMemoryWidgetRepository();
    }

    @Test
    public void testAddWidget() {
        WidgetModel widget = repository.createWidget(1, 1, 100, 100, 0);
        WidgetModel widgetFromRepo = repository.getWidget(widget.getId());
        Assert.assertEquals(widget, widgetFromRepo);
    }

    @Test
    public void testModifyWidget() {
        WidgetModel widget = repository.createWidget(1, 1, 100, 100, 1);
        repository.updateWidget(widget.getId(), 1, 1, 150, 150, 2);
        WidgetModel widgetFromRepo = repository.getWidget(widget.getId());
        Assert.assertEquals(widgetFromRepo.getWidth(), 150);
        Assert.assertEquals(widgetFromRepo.getHeight(), 150);
        Assert.assertEquals(widgetFromRepo.getzIndex(), 2);
    }

    @Test
    public void testDeleteWidget() {
        WidgetModel widget = repository.createWidget(1, 1, 100, 100, 1);
        WidgetModel widgetFromRepo = repository.getWidget(widget.getId());
        Assert.assertNotNull(widgetFromRepo);
        repository.deleteWidget(widget.getId());
        widgetFromRepo = repository.getWidget(widget.getId());
        Assert.assertNull(widgetFromRepo);
    }

    @Test
    public void testWidgetList() {
        repository.clear();
        repository.createWidget(1, 1, 100, 100, 1);
        repository.createWidget(1, 1, 100, 100, 2);
        Assert.assertTrue(repository.getAllWidgets().size() == 2);
    }

    @Test
    public void testWidgetSequence() {
        repository.clear();
        repository.createWidget(1, 1, 100, 100, 1); //id 1, zIndex 2
        repository.createWidget(1, 1, 100, 100, 2); //id 2, zIndex 3
        repository.createWidget(1, 1, 100, 100, 3); //id 3, zIndex 4
        repository.createWidget(1, 1, 100, 100, 1); //id 4, zIndex 1

        List<WidgetModel> widgets = repository.getAllWidgets();
        Assert.assertTrue(widgets.get(0).getId() == 4);
        Assert.assertTrue(widgets.get(1).getId() == 1);
        Assert.assertTrue(widgets.get(2).getId() == 2);
        Assert.assertTrue(widgets.get(3).getId() == 3);
    }

}
