package com.zbank.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "netty-client-NettyFeignApi")
public interface NettyFeignApi {

    @RequestMapping(value = "/nettyServer/sendMsg", method = RequestMethod.GET)
    boolean sendMsg(String str);
}
