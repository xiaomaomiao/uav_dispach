package com.ssy.uav_dispatch.tcp.handler;

import com.ssy.uav_dispatch.websocket.WebSocketServer;
import com.ssy.uav_dispatch.websocket.handler.MainPageWebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author miaomiao
 * @date 2022/3/13 10:17
 */
@Component
@ChannelHandler.Sharable
public class NewChannelConnectedHandler extends ChannelInboundHandlerAdapter {

    private static final AtomicInteger CONNECTION_SIZE = new AtomicInteger();

    public static final ConcurrentHashMap<Integer, Channel> CONNECTION_MAPPINGS = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Channel, Integer> CHANNEL_MAPPINGS = new ConcurrentHashMap<>();


    @Resource
    private WebSocketServer webSocketServer;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int currentChannelNumber = CONNECTION_SIZE.addAndGet(1);
        System.out.println("有新连接" + ctx.channel() + "连接,当前存在连接：" + CONNECTION_SIZE.get());
        CONNECTION_MAPPINGS.put(currentChannelNumber, ctx.channel());
        CHANNEL_MAPPINGS.put(ctx.channel(), currentChannelNumber);
        webSocketServer.broadMessage(MainPageWebSocketHandler.SESSIONS, String.format("新设备接入[%s] 设备编号:%s，当前设备数量：%s", ctx.channel().remoteAddress(), currentChannelNumber, CONNECTION_SIZE.get()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CONNECTION_SIZE.addAndGet(-1);
        Integer currentChannelNumber = CHANNEL_MAPPINGS.get(ctx.channel());
        CONNECTION_MAPPINGS.remove(currentChannelNumber);
        CHANNEL_MAPPINGS.remove(ctx.channel());
        System.out.println(ctx.channel() + "断开连接,当前存在连接：" + CONNECTION_SIZE.get());
        webSocketServer.broadMessage(MainPageWebSocketHandler.SESSIONS, String.format("设备[%s]断开连接，设备编号:%s，当前设备数量：%s", ctx.channel().remoteAddress(), currentChannelNumber, CONNECTION_SIZE.get()));
    }

    public void sendMessage(Integer channlNumber, String message) {
        CONNECTION_MAPPINGS.get(channlNumber).writeAndFlush(message);
    }
}
