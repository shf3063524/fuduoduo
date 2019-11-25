package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 商品详情二级json
 */
public class SpecificationsBean implements Serializable {
    private static final long serialVersionUID = -2454927534044399765L;
    private String id;
    private String commodityId;
    private String commoditySpecification;
    private String supplyPrice;
    private String salePrice;
    private String number;
    private String salesVolume;
    private String commodityCode;
    private String specificationImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommoditySpecification() {
        return commoditySpecification;
    }

    public void setCommoditySpecification(String commoditySpecification) {
        this.commoditySpecification = commoditySpecification;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getSpecificationImage() {
        return specificationImage;
    }

    public void setSpecificationImage(String specificationImage) {
        this.specificationImage = specificationImage;
    }
}
