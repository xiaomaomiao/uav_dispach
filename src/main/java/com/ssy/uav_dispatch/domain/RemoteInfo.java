package com.ssy.uav_dispatch.domain;

/**
 * @author miaomiao
 * @date 2022/3/13 16:44
 */
public class RemoteInfo {
    private String extInfo;
    private Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
