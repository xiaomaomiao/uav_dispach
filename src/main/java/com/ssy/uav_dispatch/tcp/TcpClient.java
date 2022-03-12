package com.ssy.uav_dispatch.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author miaomiao
 * @date 2022/3/12 9:52
 */
@Component
public class TcpClient {

    private static final ExecutorService TCP_CLIENT_INIT_EXECUTOR = Executors.newSingleThreadExecutor();
    private ChannelFuture clientChannelFuture;

    @PostConstruct
    public void init() {

        TCP_CLIENT_INIT_EXECUTOR.submit(() -> {
            // 为客户端创建一个事件循环组
            NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

            // 创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            try {
                bootstrap.group(eventExecutors)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                                ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            }
                        });
                System.out.println("Client ready!");
                clientChannelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
                clientChannelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                System.out.println("Tcp Client start fail!");
                e.printStackTrace();
            } finally {
                eventExecutors.shutdownGracefully();
            }
        });


    }

    public ChannelFuture getClientChannelFuture() {
        return clientChannelFuture;
    }

}
