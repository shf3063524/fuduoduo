package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 商品详情一级json
 */
public class DoQueryCommodityDetailsData implements Serializable {
    private static final long serialVersionUID = -2910629478619819103L;
    private CommodityBean commodity;
    private FreightTemplateBean freightTemplate;
    private EvaluationsBean evaluations;
    private SupplierBean supplier;// 店铺地址相关内容
    private String collection;
    private ArrayList<SpecificationsBean> specifications;
    private ArrayList<AttributesBean> attributes;

    public ArrayList<AttributesBean> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<AttributesBean> attributes) {
        this.attributes = attributes;
    }

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public FreightTemplateBean getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(FreightTemplateBean freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    public SupplierBean getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierBean supplier) {
        this.supplier = supplier;
    }

    public EvaluationsBean getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(EvaluationsBean evaluations) {
        this.evaluations = evaluations;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public ArrayList<SpecificationsBean> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationsBean> specifications) {
        this.specifications = specifications;
    }
}
