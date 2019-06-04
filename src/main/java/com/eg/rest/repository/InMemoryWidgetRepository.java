package com.eg.rest.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация репозитория хранения виджетов в памяти.
 * Виджет рассматриваются как неизменяемые объекты, в случае редактирования создается новый экземпляр,
 * который заменяет существующий
 */
public class InMemoryWidgetRepository implements WidgetRepository {

    private final Map<Integer, WidgetModel> idIndex = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private List<WidgetModel> widgets = new CopyOnWriteArrayList<>();

    @Override
    public WidgetModel createWidget(int x, int y, int width, int height, int zOrder) {
        WidgetModel widget = saveWidget(idGenerator.incrementAndGet(), x, y, width, height, zOrder);
        idIndex.put(widget.getId(), widget);
        return widget;
    }

    static int compare(WidgetModel w1, WidgetModel w2) {
        if (w1 == w2) {
            return 0;
        }
        int res = w1.getzIndex() - w2.getzIndex();
        if (res != 0) {
            return res;
        }
        if (w1.getCreateTime().isBefore(w2.getCreateTime())) {
            return 1;
        } else if (w1.getCreateTime().isAfter(w2.getCreateTime())) {
            return -1;
        }
        return 0;
    }

    @Override
    public WidgetModel getWidget(int id) {
        return idIndex.get(id);
    }

    @Override
    public WidgetModel updateWidget(int id, int x, int y, int width, int height, int zOrder) {
        WidgetModel widget = idIndex.get(id);
        if (widget == null) {
            return null;
        }
        return saveWidget(widget.getId(), x, y, width, height, zOrder);
    }

    @Override
    public List<WidgetModel> getAllWidgets() {
        return Collections.unmodifiableList(widgets);
    }

    @Override
    public boolean deleteWidget(int id) {
        WidgetModel widget;
        synchronized (this) {
            widget = idIndex.remove(id);
            if (widget != null) {
                widgets.remove(widget);
            }
        }
        return widget != null;
    }

    @Override
    public void clear() {
        synchronized (this) {
            idIndex.clear();
            widgets.clear();
        }
    }

    private WidgetModel saveWidget(int id, int x, int y, int width, int height, int zOrder) {
        WidgetModel newWidget = new WidgetModel(id, x, y, width, height, zOrder);
        //TODO: проверить производительность, пока решение "в лоб"
        synchronized (this) {
            if (idIndex.containsKey(id)) { // модификация существющего
                WidgetModel current = idIndex.get(id);
                newWidget.setCreateTime(idIndex.get(id).getCreateTime());
                idIndex.remove(id);
                widgets.remove(current);
            }
            idIndex.put(newWidget.getId(), newWidget);
            widgets.add(newWidget);
            //сдвиг всех элементов
            widgets.sort(InMemoryWidgetRepository::compare);
            int pos = Collections.binarySearch(widgets, newWidget);
            //TODO: возможну нужно увеличивать zindex не у всех подряд, а только когда есть "конфликт", но это потребует более сложного алгоритма
            while (++pos < widgets.size()) {
                widgets.get(pos).incZIndex();
            }
        }
        return newWidget;
    }
}
