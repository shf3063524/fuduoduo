package com.hjkj.fuduoduo.entity.bean;

/**
 * Author：Created by shihongfei on 2019/9/29 17:11
 * Email：1511808259@qq.com
 */
public class MoneyEntity {
    private String money;
    private String integral;// 积分
    private boolean isSelected;

    public MoneyEntity(String money, String integral, boolean isSelected) {
        this.money = money;
        this.integral = integral;
        this.isSelected = isSelected;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
