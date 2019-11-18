package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 购物车国外物品查询
 */
public class DoconfirmforeigncommoditiesData implements Serializable {
    private static final long serialVersionUID = -4537241694301299981L;
    private String commodityIn;
    private String in;// 囯内
    private String commodityOut;
    private String out;// 国外

    public String getCommodityIn() {
        return commodityIn;
    }

    public void setCommodityIn(String commodityIn) {
        this.commodityIn = commodityIn;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getCommodityOut() {
        return commodityOut;
    }

    public void setCommodityOut(String commodityOut) {
        this.commodityOut = commodityOut;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
