package com.hjkj.fuduoduo.entity.bean;

/**
 * 商品规格查询一级json
 */
public class DoQuerySpecificationsData{
    private String id;
    private String commodityId;
    private String commoditySpecification;
    private String supplyPrice;
    private String salePrice;
    private String number;
    private String salesVolume;
    private String commodityCode;
    private String specificationImage;
    private boolean isSelected;

    public DoQuerySpecificationsData(String id, String commoditySpecification, String salePrice, String number,String specificationImage, boolean isSelected) {
        this.id = id;
        this.commoditySpecification = commoditySpecification;
        this.salePrice = salePrice;
        this.number = number;
        this.specificationImage = specificationImage;
        this.isSelected = isSelected;
    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
