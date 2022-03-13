package com.ssy.uav_dispatch.websocket.handler;

import javax.websocket.Session;

public interface WebSocketHandler {
    void onOpen(Session session, String uid);

    void onClose(Session session, String uid);

    default void onMessage(Session session, String message) {
    }
}
