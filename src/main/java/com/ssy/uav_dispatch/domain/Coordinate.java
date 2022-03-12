package com.ssy.uav_dispatch.domain;

/**
 * @author miaomiao
 * @date 2022/3/12 10:31
 */
public class Coordinate {
    /**
     * 经度
     */
    private float longitude;

    /**
     * 纬度
     */
    private float latitude;

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
