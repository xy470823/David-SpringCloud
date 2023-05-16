package com.zbank.controller;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import com.zbank.service.NettyService;
import com.zbank.vo.ResponseTestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/netty")
public class NettySysConfigController {

    @Autowired
    private NettyService nettyClientService;

    @PostMapping("/sendMsg")
    public ResponseTestVO export(@RequestBody JSONObject request) {
        ResponseTestVO responseTestVO = new ResponseTestVO();
        responseTestVO.setMessage("SUCCESS");
        responseTestVO.setDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
        responseTestVO.setData(nettyClientService.sendMsg(request));
        return responseTestVO;
    }
}
