package com.zbank.controller;

import com.zbank.service.NettyServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/nettyServer")
public class NettyServerController {


    @Autowired
    private NettyServerService nettyServerService;
    @PostMapping("/sendMsg")
    public boolean sendMsg(String msg) {
        return nettyServerService.sendMsg(msg);
    }
}
