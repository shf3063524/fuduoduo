package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 确认订单一级json
 */
public class DoconfirmordersData implements Serializable {
    private static final long serialVersionUID = -5149376669381521720L;
    private CommodityBean commodity;
    private CommoditySpecificationBeans commoditySpecification;
    private String number;
    private FreightTemplateBean freightTemplate;
    private ShopBean shop;
    private DefaultAddressBean defaultAddress;

    public FreightTemplateBean getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(FreightTemplateBean freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public DefaultAddressBean getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(DefaultAddressBean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
