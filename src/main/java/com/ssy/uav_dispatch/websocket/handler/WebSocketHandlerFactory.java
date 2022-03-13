package com.ssy.uav_dispatch.websocket.handler;

import com.ssy.uav_dispatch.websocket.handler.IndexPageWebSocketHandler;
import com.ssy.uav_dispatch.websocket.handler.MainPageWebSocketHandler;
import com.ssy.uav_dispatch.websocket.handler.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author miaomiao
 * @date 2022/3/13 11:15
 */
@Configuration
public class WebSocketHandlerFactory {
    @Resource
    private IndexPageWebSocketHandler indexPageWebSocketHandler;

    @Resource
    private MainPageWebSocketHandler mainPageWebSocketHandler;

    public WebSocketHandler getWebSocketHandler(String uid) {
        if ("main".equals(uid)) {
            return mainPageWebSocketHandler;
        }
        return indexPageWebSocketHandler;
    }
}
