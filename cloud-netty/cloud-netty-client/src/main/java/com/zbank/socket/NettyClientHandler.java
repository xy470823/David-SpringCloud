package com.zbank.socket;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.zbank.enums.NettyPacketType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Andon
 * 2022/7/22
 * <p>
 * Netty客户端处理器
 */
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger("NettyClient心跳检测");

    @Lazy
    @Resource
    private NettyClient nettyClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("建立Netty连接!!");
        ctx.fireChannelActive();
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.warn("Netty连接关闭!!");
        reconnect(ctx);
    }

    /**
     * 心跳处理，每5秒发送一次心跳请求
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                //logger.info("已经5秒没有发送消息给服务端!!");
                // 向服务端发送心跳包
                NettyPacket<String> nettyRequest = NettyPacket.buildRequest("client heartbeat " + new Date().toString());
                nettyRequest.setNettyPacketType(NettyPacketType.HEARTBEAT.getValue());
                // 发送心跳消息，并在发送失败时关闭该连接
                ctx.writeAndFlush(JSONObject.toJSONString(nettyRequest));
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 收到服务端消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // 报文解析处理
            NettyPacket<Object> nettyPacket = JSONObject.parseObject(msg.toString(), new TypeReference<NettyPacket<Object>>() {
            }.getType());
            // 发布自定义Netty数据包处理事件
            applicationEventPublisher.publishEvent(new NettyPacketEvent(ctx.channel().id(), nettyPacket));
        } catch (Exception e) {
            logger.error("channelId:【{}】 报文解析失败!! msg:{} error:{}", ctx.channel().id(), msg.toString(), e.getMessage());
            NettyPacket<String> nettyResponse = NettyPacket.buildRequest("报文解析失败!!");
            ctx.writeAndFlush(JSONObject.toJSONString(nettyResponse));
        }
    }

    /**
     * 当连接发生异常时触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        ctx.close();
    }

    private void reconnect(ChannelHandlerContext ctx) {
        logger.info("准备30s后断线重连!!");
        ctx.channel().eventLoop().schedule(() -> nettyClient.run(), 30, TimeUnit.SECONDS);
    }
}

