package com.entrepriseArch.gateway_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fallbacks {
    @GetMapping("/fallback")
    public String fallback(){
        return "404 not found";
    }

}
