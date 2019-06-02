package com.eg.rest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WidgetRepositoryImpl implements WidgetRepository {

    private final Map<Integer, WidgetModel> idIndex = new ConcurrentHashMap<>();
    private List<WidgetModel> sortedCache = Collections.emptyList();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public WidgetModel createWidget(int x, int y, int width, int height, int zOrder) {
        WidgetModel widget = saveWidget(idGenerator.incrementAndGet(), x, y, width, height, zOrder);
        idIndex.put(widget.getId(), widget);
        return widget;
    }

    @Override
    public boolean updateWidget(int id, int x, int y, int width, int height, int zOrder) {
        WidgetModel widget = idIndex.get(id);
        if (widget == null) {
            return false;
        }
        saveWidget(widget.getId(), x, y, width, height, zOrder);
        return true;
    }

    @Override
    public WidgetModel getWidget(int id) {
        return idIndex.get(id);
    }

    @Override
    public List<WidgetModel> getAllWidgets() {
        return sortedCache;
    }

    @Override
    public boolean deleteWidget(int id) {
        return idIndex.remove(id) != null;
    }

    private WidgetModel saveWidget(int id, int x, int y, int width, int height, int zOrder) {
        WidgetModel newWidget = new WidgetModel(id);
        newWidget.setX(x);
        newWidget.setY(y);
        newWidget.setHeight(height);
        newWidget.setWidth(width);
        newWidget.setzIndex(zOrder);

        idIndex.put(newWidget.getId(), newWidget);
        return newWidget;
    }
}
