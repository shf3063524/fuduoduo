package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 客服云
 */
public class DocreatenewimData implements Serializable {
    private static final long serialVersionUID = 1300783563485281379L;
    private String  error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
