package com.pay.base.model;


import java.util.Date;




public class ContextPicture {
	private static final long serialVersionUID = 3754721396826756317L;
	private Long pictureId;
	private String pictureName;
	private String pictureUrl;
	/**
	 * 0:无效 1:有效
	 */
	private Integer pictureStatus;
	private Long pictureOwnerId;
	/**
	 * 图片类型 如：1 logo 2环境
	 */
	private Integer pictureType;
	private Date createTime;
	private String createdBy;
	private Date updateTime;
	private String updatedBy;
	private Integer productType;




	/**
	 * @return the pictureId
	 */
	public Long getPictureId() {
		return pictureId;
	}

	/**
	 * @param pictureId
	 *            the pictureId to set
	 */
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	/**
	 * @return the pictureName
	 */
	public String getPictureName() {
		return pictureName;
	}

	/**
	 * @param pictureName
	 *            the pictureName to set
	 */
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * @param pictureUrl
	 *            the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	/**
	 * @return the pictureStatus
	 */
	public Integer getPictureStatus() {
		return pictureStatus;
	}

	/**
	 * @param pictureStatus
	 *            the pictureStatus to set
	 */
	public void setPictureStatus(Integer pictureStatus) {
		this.pictureStatus = pictureStatus;
	}

	/**
	 * @return the pictureOwnerId
	 */
	public Long getPictureOwnerId() {
		return pictureOwnerId;
	}

	/**
	 * @param pictureOwnerId
	 *            the pictureOwnerId to set
	 */
	public void setPictureOwnerId(Long pictureOwnerId) {
		this.pictureOwnerId = pictureOwnerId;
	}

	/**
	 * @return the pictureType
	 */
	public Integer getPictureType() {
		return pictureType;
	}

	/**
	 * @param pictureType
	 *            the pictureType to set
	 */
	public void setPictureType(Integer pictureType) {
		this.pictureType = pictureType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
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