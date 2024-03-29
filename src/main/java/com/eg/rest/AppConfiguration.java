package com.eg.rest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eg.rest.repository.InMemoryWidgetRepository;
import com.eg.rest.repository.WidgetRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public WidgetRepository getWidgetRepository() {
        return new InMemoryWidgetRepository();
    }
}
