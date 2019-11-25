package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 首页推荐-猜你喜欢一级json
 */
public class DoFindMaybeYouLikeData implements Serializable {
    private static final long serialVersionUID = -8612073709692494393L;
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
