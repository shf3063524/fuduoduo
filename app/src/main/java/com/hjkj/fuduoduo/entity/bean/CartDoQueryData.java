package com.hjkj.fuduoduo.entity.bean;

import com.hjkj.fuduoduo.base.BaseInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户购物车查询一级json
 */
public class CartDoQueryData implements Serializable {
    private static final long serialVersionUID = 5463524849488303538L;
    private ShopBean shop;
    private ArrayList<CartBean> cart;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public ArrayList<CartBean> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartBean> cart) {
        this.cart = cart;
    }
}
