package com.ssy.uav_dispatch.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssy.uav_dispatch.domain.Coordinate;
import com.ssy.uav_dispatch.tcp.TcpClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author miaomiao
 * @date 2022/3/12 10:11
 */
@RestController
public class TcpController {
    @Resource
    private TcpClient tcpClient;

    @RequestMapping("/send")
    public void sendTcpMessage(@RequestBody Coordinate coordinate) {
        tcpClient.getClientChannelFuture().channel().writeAndFlush(JSON.toJSONString(coordinate));
    }
}
