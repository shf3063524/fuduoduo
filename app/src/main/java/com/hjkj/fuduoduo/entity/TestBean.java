package com.hjkj.fuduoduo.entity;

import java.io.Serializable;

/**
 * 测试项目实例类
 * Author：Created by shihongfei on 2019/9/25 11:04
 * Email：1511808259@qq.com
 */
public class TestBean implements Serializable {

    private static final long serialVersionUID = -2244363936974401976L;
    private String text;

    private boolean clickable; // 显示与不显示
    private boolean check; // 选定与不选定

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public TestBean(boolean clickable) {
        this.clickable = clickable;
    }

    public TestBean() {
    }

    public TestBean(String text, boolean clickable,boolean check) {
        this.text = text;
        this.clickable = clickable;
        this.check = check;
    }

    public TestBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
