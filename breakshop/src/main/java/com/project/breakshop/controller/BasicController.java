package com.project.breakshop.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class BasicController {
    @GetMapping("/")
    public String hello(){
        return "Hello";
    }
}
