package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 查询订单的物流信息一级json
 */
public class DoqueryorderexpressesData implements Serializable {
    private static final long serialVersionUID = -8562448182005878991L;
    private ArrayList<SpecificationsBean> images;
    private ExpressBean freightData;
    private FreightBean freight;
    private String freightState;

    public ArrayList<SpecificationsBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<SpecificationsBean> images) {
        this.images = images;
    }

    public ExpressBean getFreightData() {
        return freightData;
    }

    public void setFreightData(ExpressBean freightData) {
        this.freightData = freightData;
    }

    public FreightBean getFreight() {
        return freight;
    }

    public void setFreight(FreightBean freight) {
        this.freight = freight;
    }

    public String getFreightState() {
        return freightState;
    }

    public void setFreightState(String freightState) {
        this.freightState = freightState;
    }
}
