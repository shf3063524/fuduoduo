package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 购物车多个商品支付提交订单时上传的数据
 */
public class DosubmitordersData implements Serializable {
    private static final long serialVersionUID = 2457868712878924836L;
    private String supplierId;// 	供货商id
    private String freightPrice;// 运费
    private ArrayList<CommoditieBeans> commodities;//商品集合上传
    private String note;

    public DosubmitordersData(String supplierId, String freightPrice, ArrayList<CommoditieBeans> commodities, String note) {
        this.supplierId = supplierId;
        this.freightPrice = freightPrice;
        this.commodities = commodities;
        this.note = note;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public ArrayList<CommoditieBeans> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<CommoditieBeans> commodities) {
        this.commodities = commodities;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
