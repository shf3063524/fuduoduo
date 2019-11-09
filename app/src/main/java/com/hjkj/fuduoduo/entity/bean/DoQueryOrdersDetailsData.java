package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 全部订单查询一级json
 */
public class DoQueryOrdersDetailsData implements Serializable {
    private static final long serialVersionUID = 5745492771462470808L;
    private ArrayList<OrderDetailsBean> orderDetails;
    private ShopBean shop;
    private String refunding;
    private FreightBean freight;// 物流信息
    private OrderBean order;
    private ArrayList<ExpressBean> express;
    private DefaultAddressBean freightAddress;
    private boolean clickable; // 显示与不显示
    private boolean check; // 选定与不选定

    public ArrayList<ExpressBean> getExpress() {
        return express;
    }

    public void setExpress(ArrayList<ExpressBean> express) {
        this.express = express;
    }

    public FreightBean getFreight() {
        return freight;
    }

    public void setFreight(FreightBean freight) {
        this.freight = freight;
    }

    public DefaultAddressBean getFreightAddress() {
        return freightAddress;
    }

    public void setFreightAddress(DefaultAddressBean freightAddress) {
        this.freightAddress = freightAddress;
    }

    public ArrayList<OrderDetailsBean> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailsBean> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public String getRefunding() {
        return refunding;
    }

    public void setRefunding(String refunding) {
        this.refunding = refunding;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
