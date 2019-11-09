package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 全部订单查询二级json
 */
public class FreightBean implements Serializable {
    private static final long serialVersionUID = 3670923491351453381L;
    private String createTime; // 发货时间
    private String updateTime;
    private String id;
    private String consumerId;
    private String supplierId;
    private String freightNumber; // 物流编号
    private String orderNumber; // 物流编号
    private String freightCompany; //物流名称
    private String freightCompanyCode;
    private String freightCode;
    private String state;
    private String isReturn;
    private String reMarks;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }

    public String getFreightCompanyCode() {
        return freightCompanyCode;
    }

    public void setFreightCompanyCode(String freightCompanyCode) {
        this.freightCompanyCode = freightCompanyCode;
    }

    public String getFreightCode() {
        return freightCode;
    }

    public void setFreightCode(String freightCode) {
        this.freightCode = freightCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReMarks() {
        return reMarks;
    }

    public void setReMarks(String reMarks) {
        this.reMarks = reMarks;
    }

    public String getFreightNumber() {
        return freightNumber;
    }

    public void setFreightNumber(String freightNumber) {
        this.freightNumber = freightNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }
}
