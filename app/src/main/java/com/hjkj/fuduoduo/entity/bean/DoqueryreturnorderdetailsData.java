package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 查询退货订单详情一级json
 */
public class DoqueryreturnorderdetailsData implements Serializable {
    private static final long serialVersionUID = 3900988856111709203L;
    private OrderDetailsBeans orderDetails;
    private CommodityBean commodity;
    private CommoditySpecificationBeans commoditySpecification;
    private CommoditySpecificationBeans exchangeSpecification;
    private String refunding;
    private ReturnOrderDetailBean returnOrderDetails;
    private ReturnOrderBean returnOrder;
    private ExchangeBean exchange;
    private DefaultAddressBean freightAddress;
    private FreightMapBean freightMap;

    public FreightMapBean getFreightMap() {
        return freightMap;
    }

    public void setFreightMap(FreightMapBean freightMap) {
        this.freightMap = freightMap;
    }

    public OrderDetailsBeans getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsBeans orderDetails) {
        this.orderDetails = orderDetails;
    }

    public CommoditySpecificationBeans getExchangeSpecification() {
        return exchangeSpecification;
    }

    public void setExchangeSpecification(CommoditySpecificationBeans exchangeSpecification) {
        this.exchangeSpecification = exchangeSpecification;
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

    public ExchangeBean getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeBean exchange) {
        this.exchange = exchange;
    }

    public DefaultAddressBean getFreightAddress() {
        return freightAddress;
    }

    public void setFreightAddress(DefaultAddressBean freightAddress) {
        this.freightAddress = freightAddress;
    }

    public String getRefunding() {
        return refunding;
    }

    public void setRefunding(String refunding) {
        this.refunding = refunding;
    }

    public ReturnOrderDetailBean getReturnOrderDetails() {
        return returnOrderDetails;
    }

    public void setReturnOrderDetails(ReturnOrderDetailBean returnOrderDetails) {
        this.returnOrderDetails = returnOrderDetails;
    }

    public ReturnOrderBean getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(ReturnOrderBean returnOrder) {
        this.returnOrder = returnOrder;
    }
}
