package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 商品详情四级json
 */
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 8610040314789470856L;
    private String createTime;
    private String updateTime;
    private String id;
    private String consumerId;
    private String shopId;
    private String supplierId;
    private String commodityId;
    private String stars;
    private String evaluationContent;
    private String extraEvaluation;

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

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public String getExtraEvaluation() {
        return extraEvaluation;
    }

    public void setExtraEvaluation(String extraEvaluation) {
        this.extraEvaluation = extraEvaluation;
    }
}
