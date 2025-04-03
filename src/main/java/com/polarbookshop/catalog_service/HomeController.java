package com.polarbookshop.catalog_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String Greeting() {
        return "Welcome to the book catalog!";
    }
}
