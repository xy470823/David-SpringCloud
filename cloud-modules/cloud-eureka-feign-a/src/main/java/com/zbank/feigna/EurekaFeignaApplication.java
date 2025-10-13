package com.zbank.feigna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zbank.feigna.feigncient")
@EnableEurekaClient
public class EurekaFeignaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignaApplication.class, args);
    }
}
