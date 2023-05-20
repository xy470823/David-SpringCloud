package com.zbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.zbank")
@EnableDiscoveryClient
public class GateWayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayMainApplication.class, args);
    }
}