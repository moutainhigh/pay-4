package com.pay.base.model;

import java.util.Date;

public class Feature implements Model{
    
    private Long featureId;
    private String name;
    private String description;
    private int appScale;
    private int securLevel;
    private int status;
    private Date createDate;
    private Date updateDate;
    
    @Override
    public void setPrimaryKey(Long id) {
        setFeatureId(id);
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppScale() {
        return appScale;
    }

    public void setAppScale(int appScale) {
        this.appScale = appScale;
    }

    public int getSecurLevel() {
        return securLevel;
    }

    public void setSecurLevel(int securLevel) {
        this.securLevel = securLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
}
