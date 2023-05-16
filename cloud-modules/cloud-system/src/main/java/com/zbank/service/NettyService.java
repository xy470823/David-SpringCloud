package com.zbank.service;


import com.alibaba.fastjson2.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "netty-client", contextId = "system-NettyService")
public interface NettyService {

    @PostMapping(value = "/nettyClient/sendMsg")
    boolean sendMsg(@RequestBody JSONObject request);
}
