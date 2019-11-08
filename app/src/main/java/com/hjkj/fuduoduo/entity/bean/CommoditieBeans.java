package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 购物车多个商品支付提交订单时上传的数据二级json
 */
public class CommoditieBeans implements Serializable {
    private static final long serialVersionUID = 5194061122568169875L;
    private String price;
    private String number;
    private String commodityId;//	商品id
    private String commoditySpecificationId;//商品规格id

    public CommoditieBeans(String price, String number,String commodityId, String commoditySpecificationId) {
        this.price = price;
        this.number = number;
        this.commodityId = commodityId;
        this.commoditySpecificationId = commoditySpecificationId;
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
}
