package com.pay.poss.picturemanager.model;

import java.util.Date;


/** 

* @Title: PayChainPicture.java
* @Package com.pay.poss.picturemanager.dto
* @Description:图片管理
* @author cf
* @date 2011-12-28 下午2:49:59
* @version V1.0 
*/ 
public class PayChainPicture {
	
	private Long 		pictureId;  					 //图片id  
	private String	 	pictureName;				//图片名称
	private String		pictureUrl;					//图片URL
	private Integer 	pictureStatus;			//是否有效
	private Long 		pictureOwnerId;		//图片归属者ID 如：旅游产品ID
	private Integer 	pictureType;				//图片类型 如：1 logo  2环境  
	private Date 			createTime;					//创建时间
	private String 		createdBy;					//创建人
	private Date 			updateTime;				//更新时间
	private String 		updatedBy;					//更新人
	private Integer 	productType;				//产品类型 1旅游产品 2酒店产品 3 酒店房间 4 支付链

	
	
	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Integer getPictureStatus() {
		return pictureStatus;
	}
	public void setPictureStatus(Integer pictureStatus) {
		this.pictureStatus = pictureStatus;
	}
	public Long getPictureOwnerId() {
		return pictureOwnerId;
	}
	public void setPictureOwnerId(Long pictureOwnerId) {
		this.pictureOwnerId = pictureOwnerId;
	}
	public Integer getPictureType() {
		return pictureType;
	}
	public void setPictureType(Integer pictureType) {
		this.pictureType = pictureType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	
}
