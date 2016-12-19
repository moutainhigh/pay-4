package com.pay.poss.featuremenu.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

public class FeatureDto implements MutableDto {
    private Long featureId;
    private String name;
    private String description;
    private int appScale;
    private int securLevel;
    private int status;
    private Date createDate;
    private Date updateDate;
    
    private int hiddenSecurLevel;
    

	private int hiddenAppScale;

    public int getHiddenAppScale() {
		return hiddenAppScale;
	}
	public void setHiddenAppScale(int hiddenAppScale) {
		this.hiddenAppScale = hiddenAppScale;
	}
	public int getHiddenSecurLevel() {
		return hiddenSecurLevel;
	}
	public void setHiddenSecurLevel(int hiddenSecurLevel) {
		this.hiddenSecurLevel = hiddenSecurLevel;
	}
	public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getName() {
    	if(name!=null && !name.equals("")){
			String str = name.trim();
			return str;
		}else{
			return name;
		}
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
