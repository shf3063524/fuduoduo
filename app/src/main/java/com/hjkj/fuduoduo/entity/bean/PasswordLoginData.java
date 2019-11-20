package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 密码登录一级json
 */
public class PasswordLoginData implements Serializable {

    private static final long serialVersionUID = 6790115547067570948L;
    private String page4;
    private String page3;
    private String page2;
    private String page1;
    private String address;
    private ConsumerBean consumer;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPage4() {
        return page4;
    }

    public void setPage4(String page4) {
        this.page4 = page4;
    }

    public String getPage3() {
        return page3;
    }

    public void setPage3(String page3) {
        this.page3 = page3;
    }

    public String getPage2() {
        return page2;
    }

    public void setPage2(String page2) {
        this.page2 = page2;
    }

    public String getPage1() {
        return page1;
    }

    public void setPage1(String page1) {
        this.page1 = page1;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }
}
