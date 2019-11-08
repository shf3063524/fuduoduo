package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 个人信息查询一级json
 */
public class UserDoQueryData implements Serializable {
    private static final long serialVersionUID = -6232411313190056841L;
    private EnteerpriseBean enterprise;
    private ConsumerBean consumer;

    public EnteerpriseBean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnteerpriseBean enterprise) {
        this.enterprise = enterprise;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }
}
