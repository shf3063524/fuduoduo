package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 商品详情二级json
 */
public class AttributesBean implements Serializable {
    private static final long serialVersionUID = -4632211415635436500L;
    private String attribute;
    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
