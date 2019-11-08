package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 个人信息查询二级json
 */
public class EnteerpriseBean implements Serializable {
    private static final long serialVersionUID = -1192733132021577726L;
    private String id;
    private String name;
    private String account;
    private String password;
    private String salt;
    private String logo;
    private String balance;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
