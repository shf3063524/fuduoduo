package com.fdw.fdd.entity.bean;

import java.io.Serializable;

public class CollectionBean implements Serializable {
    private static final long serialVersionUID = 6834864787335453649L;
    private String id;
    private String userId;
    private String type;
    private String collectionsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollectionsId() {
        return collectionsId;
    }

    public void setCollectionsId(String collectionsId) {
        this.collectionsId = collectionsId;
    }
}
