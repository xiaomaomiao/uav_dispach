package com.ssy.uav_dispatch.tcp.handler;

import com.ssy.uav_dispatch.websocket.WebSocketServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 坐标处理
 *
 * @author miaomiao
 * @date 2022/3/11 19:39
 */
@Component
public class CoordinateTcpHandler extends ChannelInboundHandlerAdapter {
    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive message from client:" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));

        webSocketServer.broadMessage(((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    }
}
