package com.ssy.uav_dispatch.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.ssy.uav_dispatch.domain.OperationInfo;
import com.ssy.uav_dispatch.tcp.handler.NewChannelConnectedHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author miaomiao
 * @date 2022/3/13 11:15
 */
@Component
public class MainPageWebSocketHandler implements WebSocketHandler {
    public static final CopyOnWriteArrayList<Session> SESSIONS = new CopyOnWriteArrayList<>();

    @Resource
    @Lazy
    private NewChannelConnectedHandler newChannelConnectedHandler;

    @Override
    public void onOpen(Session session, String uid) {
        SESSIONS.add(session);
    }

    @Override
    public void onClose(Session session, String uid) {
        SESSIONS.remove(session);
    }

    @Override
    public void onMessage(Session session, String message) {

        OperationInfo operationInfo = JSONObject.parseObject(message, OperationInfo.class);
        newChannelConnectedHandler.sendMessage(operationInfo.getOperationDeviceNumber(), message);
    }
}
