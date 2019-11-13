package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 个人售后全部订单查询二级json
 */
public class ReturnOrderBean implements Serializable {
    private static final long serialVersionUID = 8588300757283138214L;
    private String createTime;
    private String updateTime;
    private String id;
    private String returnOrderNumber;
    private String orderNumber;
    private String returnOrderDetailsId;
    private String orderDetailsId;
    private String supplierId;
    private String consumerId;
    private String price;
    private String returnNumber;
    private String originFreightId;
    private String returnReason;
    private String freightId;
    private String exchange;
    private String agree;
    private String endTime;
    private String saleState;
    private String totalPrice;

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

    public String getReturnOrderNumber() {
        return returnOrderNumber;
    }

    public void setReturnOrderNumber(String returnOrderNumber) {
        this.returnOrderNumber = returnOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getFreightId() {
        return freightId;
    }

    public void setFreightId(String freightId) {
        this.freightId = freightId;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public String getReturnOrderDetailsId() {
        return returnOrderDetailsId;
    }

    public void setReturnOrderDetailsId(String returnOrderDetailsId) {
        this.returnOrderDetailsId = returnOrderDetailsId;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getOriginFreightId() {
        return originFreightId;
    }

    public void setOriginFreightId(String originFreightId) {
        this.originFreightId = originFreightId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
