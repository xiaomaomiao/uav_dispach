package com.ssy.uav_dispatch.websocket.handler;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author miaomiao
 * @date 2022/3/13 11:15
 */
@Component
public class IndexPageWebSocketHandler implements WebSocketHandler {
    public static final ConcurrentHashMap<String, List<Session>> SESSIONS = new ConcurrentHashMap<>();

    //public static final CopyOnWriteArrayList<Session> SESSIONS = new CopyOnWriteArrayList<>();

    @Override
    public void onOpen(Session session, String uid) {
        if (!SESSIONS.containsKey(uid)) {
            SESSIONS.put(uid, new ArrayList<>());
        }
        SESSIONS.get(uid).add(session);
    }

    @Override
    public void onClose(Session session, String uid) {
        SESSIONS.get(uid).remove(session);
    }


}
