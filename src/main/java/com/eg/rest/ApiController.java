package com.eg.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/widgets/")
public class ApiController {

    @GetMapping(value = "/{id}", produces = "application/json")
    public WidgetModel getWidget(@PathVariable int id) {
        WidgetModel widget = new WidgetModel();
        widget.setId(1);
        widget.setName("test_widget");
        return widget;
    }
}
