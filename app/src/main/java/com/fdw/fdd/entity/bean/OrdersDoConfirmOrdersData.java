package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 购物车多规格确认订单一级json
 */
public class OrdersDoConfirmOrdersData implements Serializable {
    private static final long serialVersionUID = -491111056030354152L;
    private String size;
    private String price;
    private ArrayList<CommoditiesBeans> commodities;
    private DefaultAddressBean defaultAddress;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<CommoditiesBeans> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<CommoditiesBeans> commodities) {
        this.commodities = commodities;
    }
    public DefaultAddressBean getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(DefaultAddressBean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
