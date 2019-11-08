package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 次级类目查询一级json
 */
public class DoQueryCategoryDetailsData implements Serializable {
    private static final long serialVersionUID = 3915592319043630813L;
    private ParentBean parent;
    private ArrayList<SonBean> son;

    public ParentBean getParent() {
        return parent;
    }

    public void setParent(ParentBean parent) {
        this.parent = parent;
    }

    public ArrayList<SonBean> getSon() {
        return son;
    }

    public void setSon(ArrayList<SonBean> son) {
        this.son = son;
    }
}
