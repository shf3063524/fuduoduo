package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 查询交易明细一级json
 */
public class DoQueryTransactionRecordData implements Serializable {
    private static final long serialVersionUID = -3166630971774685810L;
    private String id;
    private String userId;
    private String type;
    private String price;
    private String number;
    private String dealTime;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
