package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 商品详情三级json
 */
public class EvaluationBeans implements Serializable {
    private static final long serialVersionUID = -6908213198965440302L;
    private Evaluation evaluation;
    private SpecificationBeans specification;
    private ConsumerBean consumer;

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public SpecificationBeans getSpecification() {
        return specification;
    }

    public void setSpecification(SpecificationBeans specification) {
        this.specification = specification;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }
}
