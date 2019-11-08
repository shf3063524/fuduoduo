package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 购物车多规格确认订单三级json
 */
public class SpecBean implements Serializable {
    private static final long serialVersionUID = 3914895008077573537L;
    private CommodityBean commodity;
    private String number;
    private SpecificationBeans specification;

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public SpecificationBeans getSpecification() {
        return specification;
    }

    public void setSpecification(SpecificationBeans specification) {
        this.specification = specification;
    }
}
