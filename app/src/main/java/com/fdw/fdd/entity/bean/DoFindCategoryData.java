package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 *
 */
public class DoFindCategoryData implements Serializable {
    private static final long serialVersionUID = 7034099618187392441L;
    private String id;
    private String categoryId;
    private String serialNumber;
    private String nickName;
    private boolean clickable;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
