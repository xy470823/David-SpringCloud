package com.zbank.service.impl;

import com.zbank.service.NettyServerService;
import org.springframework.stereotype.Service;

@Service
public class NettyServerServiceImpl implements NettyServerService {
    @Override
    public boolean sendMsg(String msg) {
        return false;
    }
}
