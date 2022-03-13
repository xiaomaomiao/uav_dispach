package com.ssy.uav_dispatch.websocket;

import com.ssy.uav_dispatch.websocket.handler.WebSocketHandlerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;

/**
 * @author miaomiao
 * @date 2022/3/12 10:21
 */
@ServerEndpoint("/websocket/{uid}")
@Component
public class WebSocketServer implements ApplicationContextAware {

    private static WebSocketHandlerFactory webSocketHandlerFactory;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) {
        webSocketHandlerFactory.getWebSocketHandler(uid).onOpen(session, uid);
        System.out.println(uid + "加入webSocket！");
    }

    @OnClose
    public void onClose(Session session, @PathParam(value = "uid") String uid) {
        webSocketHandlerFactory.getWebSocketHandler(uid).onClose(session, uid);
        System.out.println(uid + "断开webSocket连接！");
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam(value = "uid") String uid) throws IOException {
        System.out.println("websocket收到消息：" + message);
        webSocketHandlerFactory.getWebSocketHandler(uid).onMessage(session, message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public void broadMessage(Collection<Session> sessionList, Object message) throws Exception {
        for (Session session : sessionList) {
            session.getBasicRemote().sendText(message.toString());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        webSocketHandlerFactory = applicationContext.getBean(WebSocketHandlerFactory.class);
    }
}
