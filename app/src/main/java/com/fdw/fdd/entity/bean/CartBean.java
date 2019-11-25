package com.fdw.fdd.entity.bean;

import com.fdw.fdd.base.BaseInfo;

import java.io.Serializable;

/**
 * 用户购物车查询二级json
 */
public class CartBean extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -8712964214474981241L;
    private CommodityBean commodity ;
    private CommoditySpecificationBeans commoditySpecification ;
    private  CartBeans cart;

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public CommoditySpecificationBeans getCommoditySpecification() {
        return commoditySpecification;
    }

    public void setCommoditySpecification(CommoditySpecificationBeans commoditySpecification) {
        this.commoditySpecification = commoditySpecification;
    }

    public CartBeans getCart() {
        return cart;
    }

    public void setCart(CartBeans cart) {
        this.cart = cart;
    }
}
