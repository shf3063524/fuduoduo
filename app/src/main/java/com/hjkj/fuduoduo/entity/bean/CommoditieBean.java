package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 店铺详情二级json
 */
public class CommoditieBean implements Serializable {
    private static final long serialVersionUID = 1820294681357888045L;
    private CommodityBean commodity;
    private ShopBean shop;

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }
}
