package com.zbank.feignb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Service B!";
    }

    @GetMapping("/ticket")
    public String getTicket() {
        return "Ticket-12345";
    }
}
