package com.eg.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRealmWidgets {
    @JsonProperty
    private int x;
    @JsonProperty
    private int y;
    @JsonProperty
    private int widht;
    @JsonProperty
    private int height;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidht() {
        return widht;
    }

    public int getHeight() {
        return height;
    }
}
