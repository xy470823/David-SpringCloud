package com.zbank.feignb.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zbank.feignb.service")
public class FeignAutoConfig {
}
