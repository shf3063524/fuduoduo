package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 查询退货订单详情一级json
 */
public class DoqueryreturnorderdetailsData implements Serializable {
    private static final long serialVersionUID = 3900988856111709203L;
    private String refunding;
    private ReturnOrderDetailBean returnOrderDetails;
    private ReturnOrderBean returnOrder;

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
