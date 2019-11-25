package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 收藏商品一级json
 */
public class DoQueryCollectionsShopData implements Serializable {
    private static final long serialVersionUID = 3133402113941184946L;
    private CollectionsBean collections;
    private CollectionBean collection;
    private boolean clickcheck = false; // 显示与不显示
    private boolean isCheck = false;
    public CollectionsBean getCollections() {
        return collections;
    }

    public void setCollections(CollectionsBean collections) {
        this.collections = collections;
    }

    public CollectionBean getCollection() {
        return collection;
    }

    public void setCollection(CollectionBean collection) {
        this.collection = collection;
    }
    public boolean isClickcheck() {
        return clickcheck;
    }

    public void setClickcheck(boolean clickcheck) {
        this.clickcheck = clickcheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
