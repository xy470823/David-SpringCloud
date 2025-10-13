package com.zbank.feigna.feigncient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cloud-eureka-feign-b",path = "/test") // 与B服务的application.name一致
public interface TestServiceClient {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/ticket")
    String getTicket();
}
