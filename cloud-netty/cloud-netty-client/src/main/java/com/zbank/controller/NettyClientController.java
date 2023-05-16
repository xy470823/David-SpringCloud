package com.zbank.controller;

import com.alibaba.fastjson2.JSONObject;
import com.zbank.service.INettyClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/nettyClient")
public class NettyClientController {

    @Autowired
    private INettyClientService nettyClientService;

    @PostMapping("/sendMsg")
    public boolean sendMsg(@RequestBody JSONObject request) {
        return nettyClientService.sendMsg(request.getString("msg"));
    }
}
