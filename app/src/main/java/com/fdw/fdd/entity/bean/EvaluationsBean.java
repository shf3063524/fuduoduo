package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 商品详情二级json
 */
public class EvaluationsBean implements Serializable {
    private static final long serialVersionUID = 4505532541880845248L;
    private ArrayList<EvaluationBeans> evaluation;
    private String number;
//    private SpecificationBeans specification;
//    private ConsumerBean consumer;

    public ArrayList<EvaluationBeans> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(ArrayList<EvaluationBeans> evaluation) {
        this.evaluation = evaluation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
