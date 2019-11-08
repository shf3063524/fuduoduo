package com.hjkj.fuduoduo.entity.bean;

import com.hjkj.fuduoduo.base.BaseInfo;

import java.io.Serializable;

/**
 * 首页推荐-猜你喜欢二级json
 */
public class ShopBean extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 313425924178257789L;
    private String province;
    private String city;
    private String area;
    private String street;
    private String id;
    private String supplierId;
    private String name;
    private String shopLogo;
    private String shopImage;
    private String shopFeature;
    private String shopIntroduce;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopFeature() {
        return shopFeature;
    }

    public void setShopFeature(String shopFeature) {
        this.shopFeature = shopFeature;
    }

    public String getShopIntroduce() {
        return shopIntroduce;
    }

    public void setShopIntroduce(String shopIntroduce) {
        this.shopIntroduce = shopIntroduce;
    }
}
