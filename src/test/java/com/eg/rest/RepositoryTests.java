package com.eg.rest;

import org.junit.Assert;
import org.junit.Test;

import com.eg.rest.repository.InMemoryWidgetRepository;
import com.eg.rest.repository.WidgetModel;
import com.eg.rest.repository.WidgetRepository;

public class RepositoryTests {

    private final WidgetRepository repository = InMemoryWidgetRepository.getInstance();

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

}
