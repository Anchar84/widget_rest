package com.eg.rest.request;

import java.util.List;

import com.eg.rest.repository.WidgetModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WidgetsAtPage {
    @JsonProperty
    private final List<WidgetModel> widgets;
    @JsonProperty
    private final int totalPages;

    public WidgetsAtPage(List<WidgetModel> widgets, int totalPages) {
        this.widgets = widgets;
        this.totalPages = totalPages;
    }

    public List<WidgetModel> getWidgets() {
        return widgets;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
