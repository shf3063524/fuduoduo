package com.hjkj.fuduoduo.entity.bean;

import java.io.Serializable;

/**
 * 首页热卖活动信息获取
 */
public class DoqueryhotsaleData implements Serializable {
    private static final long serialVersionUID = 4540474382900590153L;
    private String id;
    private String sortImage;
    private String name;
    private String backImage;
    private String colour;
    private String jumpType;
    private String jumpAddress;
    private String single;
    private String multiple;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSortImage() {
        return sortImage;
    }

    public void setSortImage(String sortImage) {
        this.sortImage = sortImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
