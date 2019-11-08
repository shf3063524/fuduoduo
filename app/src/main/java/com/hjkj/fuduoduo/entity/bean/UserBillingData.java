package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 购物车多个商品一起付款集合json
 */
public class UserBillingData implements Serializable {
    private static final long serialVersionUID = -4658820729583049232L;

    private ArrayList<UserBillingBean> userBillingBeans;

    public ArrayList<UserBillingBean> getUserBillingBeans() {
        return userBillingBeans;
    }

    public void setUserBillingBeans(ArrayList<UserBillingBean> userBillingBeans) {
        this.userBillingBeans = userBillingBeans;
    }
}
