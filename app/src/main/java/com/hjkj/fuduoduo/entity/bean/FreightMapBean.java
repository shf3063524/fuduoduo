package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 查询退货订单详情二级json
 */
public class FreightMapBean implements Serializable {
    private static final long serialVersionUID = -1914577544505197671L;
    private String freightCompany;
    private String freightCode;
    private String freightState;
    private ArrayList<ExpressBean> freightDate;

    public String getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }

    public String getFreightCode() {
        return freightCode;
    }

    public void setFreightCode(String freightCode) {
        this.freightCode = freightCode;
    }

    public String getFreightState() {
        return freightState;
    }

    public void setFreightState(String freightState) {
        this.freightState = freightState;
    }

    public ArrayList<ExpressBean> getFreightDate() {
        return freightDate;
    }

    public void setFreightDate(ArrayList<ExpressBean> freightDate) {
        this.freightDate = freightDate;
    }
}
