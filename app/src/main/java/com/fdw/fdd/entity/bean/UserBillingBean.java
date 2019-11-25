package com.fdw.fdd.entity.bean;

import java.io.Serializable;

public class UserBillingBean implements Serializable {
    private static final long serialVersionUID = -1685690105765741955L;
    private String commoditySpecificationId;
    private String number;

    public UserBillingBean(String commoditySpecificationId, String number) {
        this.commoditySpecificationId = commoditySpecificationId;
        this.number = number;
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
