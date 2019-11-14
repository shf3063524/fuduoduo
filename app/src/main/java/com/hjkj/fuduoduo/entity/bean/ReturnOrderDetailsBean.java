package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 个人售后查询三级json
 */
public class ReturnOrderDetailsBean implements Serializable {
    private static final long serialVersionUID = 2706450936983880161L;
    private String createTime;
    private String updateTime;
    private String id;
    private String returnOrderNumber;
    private String orderDetailsId;
    private String commodityId;
    private String commoditySpecificationId;
    private String exchangeSpecificationId;
    private String price;
    private String number;
    private String returnReason;
    private String freightState;
    private String exchange;
    private String agree;
    private String images;
    private String description;
    private String saleState;
    private String activityId;


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

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommoditySpecificationId() {
        return commoditySpecificationId;
    }

    public void setCommoditySpecificationId(String commoditySpecificationId) {
        this.commoditySpecificationId = commoditySpecificationId;
    }

    public String getExchangeSpecificationId() {
        return exchangeSpecificationId;
    }

    public void setExchangeSpecificationId(String exchangeSpecificationId) {
        this.exchangeSpecificationId = exchangeSpecificationId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getFreightState() {
        return freightState;
    }

    public void setFreightState(String freightState) {
        this.freightState = freightState;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
