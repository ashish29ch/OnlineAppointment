package com.workshop.OnlineAppointment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloController {
    @org.springframework.beans.factory.annotation.Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/")
    public String helloWorld(){
        return welcomeMessage;
    }
}
