package com.ssy.uav_dispatch.controller;

import com.alibaba.fastjson.JSON;
import com.ssy.uav_dispatch.domain.Coordinate;
import com.ssy.uav_dispatch.domain.RemoteInfo;
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
    public void sendTcpMessage(@RequestBody RemoteInfo remoteInfo) {
        tcpClient.getClientChannelFuture().channel().writeAndFlush(JSON.toJSONString(remoteInfo));
    }

    @RequestMapping("/demo")
    public void sendDemo(@RequestParam("deviceNumber") Integer deviceNumber) throws Exception{
        double[][] demo = {{116.478935, 39.997761}, {116.478939, 39.997825}, {116.478912, 39.998549}, {116.478912, 39.998549}, {116.478998, 39.998555}, {116.478998, 39.998555}, {116.479282, 39.99856}, {116.479658, 39.998528}, {116.480151, 39.998453}, {116.480784, 39.998302}, {116.480784, 39.998302}, {116.481149, 39.998184}, {116.481573, 39.997997}, {116.481863, 39.997846}, {116.482072, 39.997718}, {116.482362, 39.997718}, {116.483633, 39.998935}, {116.48367, 39.998968}, {116.484648, 39.999861}};
        for (double[] doubles : demo) {
            RemoteInfo remoteInfo = new RemoteInfo();
            remoteInfo.setCoordinate(new Coordinate(doubles[0],doubles[1]));
            tcpClient.getClientChannelFuture().channel().writeAndFlush(JSON.toJSONString(remoteInfo)).sync();
            Thread.sleep(500);
        }
    }
}
