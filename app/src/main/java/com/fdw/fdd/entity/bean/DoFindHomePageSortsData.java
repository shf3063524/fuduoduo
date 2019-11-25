package com.fdw.fdd.entity.bean;

import java.io.Serializable;

/**
 * 轮播下分类一级json
 */
public class DoFindHomePageSortsData implements Serializable {
    private static final long serialVersionUID = 7580659991229345716L;
    private String id;
    private String name;
    private String sortImage;
    private String backImage;
    private String colour;
    private String jumpType;
    private String jumpAddress;
    private String single;
    private String multiple;
    private String valid;

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

    public String getSortImage() {
        return sortImage;
    }

    public void setSortImage(String sortImage) {
        this.sortImage = sortImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpAddress() {
        return jumpAddress;
    }

    public void setJumpAddress(String jumpAddress) {
        this.jumpAddress = jumpAddress;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
