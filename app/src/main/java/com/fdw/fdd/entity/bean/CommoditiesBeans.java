package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 购物车多规格确认订单二级json
 */
public class CommoditiesBeans implements Serializable {
    private static final long serialVersionUID = 3977734968197348318L;
    private ShopBean shop;
    private ArrayList<SpecBean> specifications;
    private String freightPrice; // 运费价格

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public ArrayList<SpecBean> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecBean> specifications) {
        this.specifications = specifications;
    }
}
