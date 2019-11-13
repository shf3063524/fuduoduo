package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 协商历史查询一级json
 */
public class DoqueryconsultData implements Serializable {
    private static final long serialVersionUID = 7518676907592421592L;
    private ConsumerBean consumer;
    private ConsultsBean consult;

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }

    public ConsultsBean getConsult() {
        return consult;
    }

    public void setConsult(ConsultsBean consult) {
        this.consult = consult;
    }
}
