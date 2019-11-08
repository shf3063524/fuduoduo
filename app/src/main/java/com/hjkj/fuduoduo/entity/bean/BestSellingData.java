package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 爆款热卖一级json
 */
public class BestSellingData implements Serializable {
    private static final long serialVersionUID = -4583851612005334118L;
    private String image;
    private String salesNumber;
    private String price;
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(String salesNumber) {
        this.salesNumber = salesNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
