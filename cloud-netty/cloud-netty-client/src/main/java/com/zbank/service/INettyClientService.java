package com.zbank.service;

public interface INettyClientService {

    /**
     * Netty 消息发送
     * @param msg
     * @return
     */
    public boolean sendMsg(String msg);
}
