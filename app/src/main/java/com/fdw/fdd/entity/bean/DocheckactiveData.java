package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 激活验证接口
 */
public class DocheckactiveData implements Serializable {
    private static final long serialVersionUID = 3428932680999934796L;
    private String address;
    private ConsumerBean consumer;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }
}
