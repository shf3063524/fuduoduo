package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 个人足迹浏览一级json
 */
public class DoQueryFootprintData implements Serializable {
    private static final long serialVersionUID = 8426554746286378411L;
    private String footprint; //时间
    private ArrayList<CommoditiesBean> commodities;

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public ArrayList<CommoditiesBean> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<CommoditiesBean> commodities) {
        this.commodities = commodities;
    }
}
