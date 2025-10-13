package com.zbank.feigna.controller;

import com.zbank.feigna.feigncient.TestServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestServiceClient testServiceClient;

    @GetMapping("/hello")
    public String hello() {
        return testServiceClient.hello();
    }

    @GetMapping("/ticket")
    public String getTicket() {
        return testServiceClient.getTicket();
    }
}
