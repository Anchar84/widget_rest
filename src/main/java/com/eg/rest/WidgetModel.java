package com.eg.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WidgetModel implements Comparable<WidgetModel> {

    @JsonProperty
    private final int id;
    @JsonProperty
    private final int x;
    @JsonProperty
    private final int y;
    @JsonProperty
    private final int width;
    @JsonProperty
    private final int height;
    @JsonProperty
    private final LocalDateTime lastModifyTime = LocalDateTime.now();
    @JsonProperty
    private int zIndex = 0;
    @JsonProperty
    private LocalDateTime createTime = LocalDateTime.now();

    // конструктор для jenkins'а
    public WidgetModel() {
        id = 0;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    WidgetModel(int id, int x, int y, int width, int height, int zIndex) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.zIndex = zIndex;
    }

    int getId() {
        return id;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getzIndex() {
        return zIndex;
    }

    LocalDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    LocalDateTime getCreateTime() {
        return createTime;
    }

    void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    void incZIndex() {
        zIndex++;
    }

    @Override
    public int compareTo(WidgetModel o) {
        return InMemoryWidgetRepository.compare(this, o);
    }

    @Override
    public String toString() {
        return "WidgetModel{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", zIndex=" + zIndex +
                ", lastModifyTime=" + lastModifyTime +
                ", createTime=" + createTime +
                '}';
    }
}
