package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 查询物流公司一级json
 */
public class DoqueryexpresscompanyData implements Serializable {
    private static final long serialVersionUID = -8498605509724395178L;
    private String id;
    private String name;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
