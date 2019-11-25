package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 轮播-图片
 */
public class DoquerycarouselData implements Serializable {
    private static final long serialVersionUID = -461034406117840617L;
    private String id;
    private String image;
    private String commodityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}
