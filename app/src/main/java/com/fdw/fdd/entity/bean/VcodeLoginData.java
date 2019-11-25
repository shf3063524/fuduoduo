package com.fdw.fdd.entity.bean;

import java.io.Serializable;

public class VcodeLoginData implements Serializable {
    private static final long serialVersionUID = -3252478761820704602L;
    private String vcode; // 类型

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
