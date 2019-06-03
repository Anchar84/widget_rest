package com.eg.rest;


import com.eg.rest.request.CreateModifyWidget;
import com.eg.rest.request.GetRealmWidgets;
import com.eg.rest.request.GetWidgetsAtPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/widgets/")
public class ApiController {

    private WidgetRepository repository;

    @Autowired
    public ApiController(WidgetRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public WidgetModel getWidget(@PathVariable int id) {
        WidgetModel widget = repository.getWidget(id);
        if (widget == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "widget not found");
        }
        return repository.getWidget(id);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public WidgetModel addWidget(@RequestBody CreateModifyWidget widget) {
        if (widget.getX() == null || widget.getY() == null || widget.getWidth() == null || widget.getHeight() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not all required parameters are specified");
        }
        return repository.createWidget(
                widget.getX(), //
                widget.getY(), //
                widget.getWidth(),//
                widget.getHeight(),//
                widget.getzIndex() != null ? widget.getzIndex() : 0);
    }

    @PostMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public WidgetModel changeWidget(@RequestBody CreateModifyWidget widget, @PathVariable int id) {
        WidgetModel current = repository.getWidget(id);
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "widget not found");
        }
        if (widget.getX() == null) {
            widget.setX(current.getX());
        }
        if (widget.getY() == null) {
            widget.setY(current.getY());
        }
        if (widget.getWidth() == null) {
            widget.setWidth(current.getWidth());
        }
        if (widget.getHeight() == null) {
            widget.setHeight(current.getHeight());
        }
        if (widget.getzIndex() == null) {
            widget.setzIndex(current.getzIndex());
        }
        return repository.updateWidget(
                id,
                widget.getX(), //
                widget.getY(), //
                widget.getWidth(),//
                widget.getHeight(),//
                widget.getzIndex());
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteWidget(@PathVariable int id) {
        WidgetModel current = repository.getWidget(id);
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "widget not found");
        }
        return repository.deleteWidget(id);
    }

    @GetMapping(value = "/all")
    public List<WidgetModel> getAllWidget() {
        return repository.getAllWidgets();
    }

    @GetMapping(value = "/page")
    public List<WidgetModel> getWidgetAtPage(@RequestBody GetWidgetsAtPage page) {
        List<WidgetModel> allWidgets = repository.getAllWidgets();
        return allWidgets.subList(page.getPage() * page.getSize(), (page.getPage() + 1) * page.getSize());
    }

    @GetMapping(value = "/realm")
    public List<WidgetModel> getRealmWidget(@RequestBody GetRealmWidgets realm) {
        List<WidgetModel> allWidgets = repository.getAllWidgets();
        return allWidgets.stream() //
                .filter(w -> w.getX() >= realm.getX() && //
                        w.getY() >= realm.getY() && //
                        w.getX() + w.getWidth() < realm.getX() + realm.getWidht() && //
                        w.getY() + w.getHeight() < realm.getY() + realm.getHeight()) //
                .collect(Collectors.toList());
    }
}
