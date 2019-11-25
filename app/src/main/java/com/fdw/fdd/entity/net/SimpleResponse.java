package com.fdw.fdd.entity.net;

import java.io.Serializable;

/**
 * 密码登录-实体类
 * Created by kayroc on 2018/8/2
 * Email：kayroc@126.com
 */

public class SimpleResponse implements Serializable{
    private String createTime;
    private String updateTime;
    private String id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String logo;
    private String enterpriseId;
    private String phoneNumber;
    private String jobNumber;
    private String valid;
    private String balance;
    private String vip;
    private String freightId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getFreightId() {
        return freightId;
    }

    public void setFreightId(String freightId) {
        this.freightId = freightId;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", enterpriseId='" + enterpriseId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", valid='" + valid + '\'' +
                ", balance='" + balance + '\'' +
                ", vip='" + vip + '\'' +
                ", freightId='" + freightId + '\'' +
                '}';
    }
}
