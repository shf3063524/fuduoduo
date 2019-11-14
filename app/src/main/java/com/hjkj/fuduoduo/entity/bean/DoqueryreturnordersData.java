package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 个人售后全部订单查询一级json
 */
public class DoqueryreturnordersData implements Serializable {
    private static final long serialVersionUID = -8149866986837421633L;
    private ShopBean shop;
    private String refunding;
    private ReturnDetailsListBean returnDetailsList;
    private ReturnOrderBean returnOrder;
    private String freightPrice;
    private DefaultAddressBean freightAddress;
    private OrderBean order;

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public DefaultAddressBean getFreightAddress() {
        return freightAddress;
    }

    public void setFreightAddress(DefaultAddressBean freightAddress) {
        this.freightAddress = freightAddress;
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

    public ReturnDetailsListBean getReturnDetailsList() {
        return returnDetailsList;
    }

    public void setReturnDetailsList(ReturnDetailsListBean returnDetailsList) {
        this.returnDetailsList = returnDetailsList;
    }

    public ReturnOrderBean getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(ReturnOrderBean returnOrder) {
        this.returnOrder = returnOrder;
    }
}
