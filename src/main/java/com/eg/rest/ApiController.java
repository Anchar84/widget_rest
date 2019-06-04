package com.eg.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eg.rest.repository.WidgetModel;
import com.eg.rest.repository.WidgetRepository;
import com.eg.rest.request.CreateModifyWidget;
import com.eg.rest.request.WidgetsAtPage;

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

    @PostMapping(value = "/search/page/{pageNumber}/{pageSize}")
    public WidgetsAtPage getWidgetAtPage(@PathVariable int pageSize, @PathVariable int pageNumber) {
        if (pageNumber < 0 || pageSize <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page number and page size must be positive");
        }
        List<WidgetModel> allWidgets = repository.getAllWidgets();
        int pageCount = allWidgets.size() / pageSize;
        if (allWidgets.size() % pageSize > 0) {
            pageCount++;
        }
        int startIndex = pageNumber * pageSize;
        if (startIndex >= allWidgets.size()) {
            return new WidgetsAtPage(Collections.emptyList(), pageCount);
        }
        int endIndex = (pageNumber + 1) * pageSize;
        if (endIndex >= allWidgets.size()) {
            endIndex = allWidgets.size();
        }
        return new WidgetsAtPage(allWidgets.subList(startIndex, endIndex), pageCount);
    }

    @PostMapping(value = "/search/page/{pageNumber}")
    public WidgetsAtPage getWidgetAtPageDefault(@PathVariable int pageNumber) {
        return getWidgetAtPage(10, pageNumber);
    }

    @GetMapping(value = "/search/realm/{x}/{y}/{width}/{height}")
    public List<WidgetModel> getRealmWidget(@PathVariable int x, @PathVariable int y, @PathVariable int width, @PathVariable int height) {
        if (width <= 0 || height <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "width and height must be positive");
        }
        List<WidgetModel> allWidgets = repository.getAllWidgets();
        return allWidgets.stream() //
                         .filter(w -> w.getX() >= x && //
                                      w.getY() >= y && //
                                      w.getX() + w.getWidth() < x + width && //
                                      w.getY() + w.getHeight() < y + height) //
                         .collect(Collectors.toList());
    }
}
