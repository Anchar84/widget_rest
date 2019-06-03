package com.eg.rest;

import java.util.List;

public interface WidgetRepository {

    WidgetModel createWidget(int x, int y, int width, int height, int zOrder);

    WidgetModel updateWidget(int id, int x, int y, int width, int height, int zOrder);

    WidgetModel getWidget(int id);

    List<WidgetModel> getAllWidgets();

    boolean deleteWidget(int id);
}
