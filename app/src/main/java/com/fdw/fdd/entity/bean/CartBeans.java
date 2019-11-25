package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 用户购物车查询三级json
 */
public class CartBeans implements Serializable {
    private static final long serialVersionUID = -8632441880261321582L;
    private String createTime;
    private String updateTime;
    private String id;
    private String consumerId;
    private String shopId;
    private String commodityId;
    private String commoditySpecificationId;
    private String number;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
