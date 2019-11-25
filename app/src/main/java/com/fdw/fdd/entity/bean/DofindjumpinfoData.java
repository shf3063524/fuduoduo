package com.fdw.fdd.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  首页分类详情（京东优选）接口
 */
public class DofindjumpinfoData implements Serializable {
    private static final long serialVersionUID = 7320820982082443207L;
    private HomePageSortBean homePageSort;
    private ArrayList<MultipleListBean> multipleList;
    private ArrayList<SingleListBean> singleList;

    public HomePageSortBean getHomePageSort() {
        return homePageSort;
    }

    public void setHomePageSort(HomePageSortBean homePageSort) {
        this.homePageSort = homePageSort;
    }

    public ArrayList<MultipleListBean> getMultipleList() {
        return multipleList;
    }

    public void setMultipleList(ArrayList<MultipleListBean> multipleList) {
        this.multipleList = multipleList;
    }

    public ArrayList<SingleListBean> getSingleList() {
        return singleList;
    }

    public void setSingleList(ArrayList<SingleListBean> singleList) {
        this.singleList = singleList;
    }
}
