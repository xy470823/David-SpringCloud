package com.zbank.feignb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zbank.feignb.service")
@EnableEurekaClient
public class EurekaFeignbApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignbApplication.class, args);
    }
}
