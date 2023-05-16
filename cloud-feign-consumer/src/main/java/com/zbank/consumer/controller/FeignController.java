package com.zbank.consumer.controller;

import com.alibaba.fastjson2.JSONObject;
import com.zbank.consumer.service.FeignService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/feignConsumer")
public class FeignController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private FeignService feignService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public JSONObject testEcho(@RequestBody JSONObject reques) {
        return feignService.echo(reques);
    }

    /**
     * 获取所有服务提供者节点信息
     */
    @GetMapping(value = "/getServices")
    public Object getServices() {
        return discoveryClient.getInstances("springboot-feign-provider");
    }

    /**
     * 轮训获取服务提供者节点
     */
    @GetMapping(value = "/chooseService")
    public Object chooseService() {
        return loadBalancerClient.choose("springboot-feign-provider").getUri().toString();
    }
}
