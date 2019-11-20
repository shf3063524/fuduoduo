package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 全部订单查询二级json
 */
public class OrderDetailsBean implements Serializable {
    private static final long serialVersionUID = 4007502738990134241L;
    private CommodityBean commodity;
    private OrderDetailBean orderDetail;
    private String refunding;
    private SpecificationsBean specification;
    private boolean clickable;

    public OrderDetailsBean(CommodityBean commodity, OrderDetailBean orderDetail, String refunding, SpecificationsBean specification) {
        this.commodity = commodity;
        this.orderDetail = orderDetail;
        this.refunding = refunding;
        this.specification = specification;
    }

    public OrderDetailsBean(boolean clickable) {
        this.clickable = clickable;
    }

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public SpecificationsBean getSpecification() {
        return specification;
    }

    public void setSpecification(SpecificationsBean specification) {
        this.specification = specification;
    }

    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public String getRefunding() {
        return refunding;
    }

    public void setRefunding(String refunding) {
        this.refunding = refunding;
    }
}
