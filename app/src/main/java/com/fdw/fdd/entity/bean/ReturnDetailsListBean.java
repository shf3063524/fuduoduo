package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 个人售后全部订单查询二级json
 */
public class ReturnDetailsListBean implements Serializable {
    private static final long serialVersionUID = 7178866875500911507L;
    private CommodityBean commodity;
    private ReturnOrderDetailsBean returnOrderDetails;
    private SpecificationsBean specification;
    private ExchangeBean exchange;

    public ExchangeBean getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeBean exchange) {
        this.exchange = exchange;
    }

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public ReturnOrderDetailsBean getReturnOrderDetails() {
        return returnOrderDetails;
    }

    public void setReturnOrderDetails(ReturnOrderDetailsBean returnOrderDetails) {
        this.returnOrderDetails = returnOrderDetails;
    }

    public SpecificationsBean getSpecification() {
        return specification;
    }

    public void setSpecification(SpecificationsBean specification) {
        this.specification = specification;
    }
}
