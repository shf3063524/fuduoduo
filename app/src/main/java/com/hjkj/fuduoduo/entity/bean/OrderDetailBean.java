package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 *
 */
public class OrderDetailBean implements Serializable {
    private static final long serialVersionUID = -3723719072431823229L;
    private String id;
    private String ordersId;
    private String commodityId;
    private String commoditySpecificationId;
    private String price;
    private String number;
    private String activityId;
    private String isState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getIsState() {
        return isState;
    }

    public void setIsState(String isState) {
        this.isState = isState;
    }
}
