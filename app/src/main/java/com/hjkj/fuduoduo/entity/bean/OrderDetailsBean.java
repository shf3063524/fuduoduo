package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 全部订单查询二级json
 */
public class OrderDetailsBean implements Serializable {
    private static final long serialVersionUID = 4007502738990134241L;
    private CommodityBean commodity;
    private OrderDetailBean orderDetail;
    private SpecificationsBean specification;

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
}
