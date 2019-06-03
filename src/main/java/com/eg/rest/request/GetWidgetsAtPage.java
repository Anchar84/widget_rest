package com.eg.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetWidgetsAtPage {
    @JsonProperty
    private int page = 0;
    @JsonProperty
    private Integer size = 10;

    public int getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
