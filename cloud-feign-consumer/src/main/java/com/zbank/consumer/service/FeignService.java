package com.zbank.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "feign-provider",contextId = "feign-consumer-FeignService",path = "/feignProvider")
public interface FeignService {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    JSONObject echo(@RequestBody JSONObject reques);
}
