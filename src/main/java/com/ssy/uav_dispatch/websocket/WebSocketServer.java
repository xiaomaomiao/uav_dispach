package com.ssy.uav_dispatch.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author miaomiao
 * @date 2022/3/12 10:21
 */
@ServerEndpoint("/websocket/{uid}")
@Component
public class WebSocketServer {
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) {
        SESSIONS.put(uid, session);
        System.out.println(uid + "加入webSocket！");
    }

    @OnClose
    public void onClose(@PathParam(value = "uid") String uid) {
        SESSIONS.remove(uid);
        System.out.println(uid + "断开webSocket连接！");
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public void broadMessage(Object message) throws Exception {
        for (Session session : SESSIONS.values()) {
            session.getBasicRemote().sendText(message.toString());
        }
    }

}
