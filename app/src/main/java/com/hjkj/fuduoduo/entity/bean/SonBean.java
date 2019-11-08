package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 次级类目查询二级json
 */
public class SonBean implements Serializable {
    private static final long serialVersionUID = -8007388870723913729L;
    private String id;
    private String name;
    private String interestRate;
    private String parentId;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
