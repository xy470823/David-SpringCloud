package com.zbank.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.zbank.service.INettyClientService;
import com.zbank.socket.NettyClient;
import com.zbank.socket.NettyPacket;
import com.zbank.vo.RequestTestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service("NettyClientService")
public class INettyClientServiceImpl implements INettyClientService{

    @Autowired
    private NettyClient nettyClient;

    /**
     * netty消息发送
     * @param msg
     * @return
     */
    @Override
    public boolean sendMsg(String msg) {
        RequestTestVO param = RequestTestVO.builder().key("hello").value(msg).date(new Date()).build();
        NettyPacket<RequestTestVO> nettyRequest = NettyPacket.buildRequest(param);
        return nettyClient.sendMsg(JSONObject.toJSONString(nettyRequest));
    }
}
