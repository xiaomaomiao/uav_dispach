package com.ssy.uav_dispatch.tcp.handler;

import com.ssy.uav_dispatch.websocket.WebSocketServer;
import com.ssy.uav_dispatch.websocket.handler.IndexPageWebSocketHandler;
import com.ssy.uav_dispatch.websocket.handler.MainPageWebSocketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class CoordinateTcpHandler extends ChannelInboundHandlerAdapter {
    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive message from client:" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        Integer deviceNumber = NewChannelConnectedHandler.CHANNEL_MAPPINGS.get(ctx.channel());
        webSocketServer.broadMessage(IndexPageWebSocketHandler.SESSIONS.get(deviceNumber.toString()), ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    }
}
