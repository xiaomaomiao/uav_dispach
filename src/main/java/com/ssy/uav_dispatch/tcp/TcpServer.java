package com.ssy.uav_dispatch.tcp;

import com.ssy.uav_dispatch.tcp.handler.CoordinateTcpHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author miaomiao
 * @date 2022/3/11 19:37
 */
@Component
public class TcpServer {

    @Resource
    private CoordinateTcpHandler coordinateTcpHandler;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            // 创建BossGroup 和 WorkerGroup
            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            // 创建服务器端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            try {
                bootstrap.group(bossGroup, workerGroup)
                        // 指定传输Channel
                        .channel(NioServerSocketChannel.class)
                        // 指定BossGroup中Channel的handler
                        .handler(new LoggingHandler())
                        // 指定WorkerGroup中Channel的handler
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(coordinateTcpHandler);
                            }
                        });

                System.out.println("Server ready!");
                // 异步绑定服务器，调用sync阻塞等待直到绑定完成
                ChannelFuture channelFuture = bootstrap.bind(8888).sync();
                // 阻塞等待服务器关闭
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                System.out.println("start tcp server failed!");
                e.printStackTrace();
            } finally {
                // 释放资源
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }
}
