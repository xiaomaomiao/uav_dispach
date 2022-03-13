package com.ssy.uav_dispatch.domain;

/**
 * @author miaomiao
 * @date 2022/3/13 16:48
 */
public class OperationInfo {
    private String operation;

    private Integer operationDeviceNumber;

    public Integer getOperationDeviceNumber() {
        return operationDeviceNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setOperationDeviceNumber(Integer operationDeviceNumber) {
        this.operationDeviceNumber = operationDeviceNumber;
    }
}
