package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class DoqueryhotsaledetailsData implements Serializable {
    private static final long serialVersionUID = 6917064110489249009L;
    private HomePageSortBean hotSale;
    private ArrayList<MultipleListBean> multipleList;
    private ArrayList<SingleListBean> singleList;

    public HomePageSortBean getHotSale() {
        return hotSale;
    }

    public void setHotSale(HomePageSortBean hotSale) {
        this.hotSale = hotSale;
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
