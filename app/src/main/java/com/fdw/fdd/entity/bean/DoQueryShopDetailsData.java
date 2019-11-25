package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 店铺详情一级json
 */
public class DoQueryShopDetailsData implements Serializable {
    private static final long serialVersionUID = -6651066598519780215L;

    private ShopBean shop;
    private ArrayList<CommoditieBean> commodities;
    private String collection;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public ArrayList<CommoditieBean> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<CommoditieBean> commodities) {
        this.commodities = commodities;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
