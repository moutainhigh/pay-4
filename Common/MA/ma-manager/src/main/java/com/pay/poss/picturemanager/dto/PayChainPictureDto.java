package com.pay.poss.picturemanager.dto;

import java.io.Serializable;
import java.util.Date;


/** 

* @Title: PayChainPicture.java
* @Package com.pay.poss.picturemanager.dto
* @Description:图片管理
* @author cf
* @date 2011-12-28 下午2:49:59
* @version V1.0 
*/ 
public class PayChainPictureDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long 		pictureId;  					 //图片id  
	private String	 	pictureName;				//图片名称
	private String		pictureUrl;					//图片URL
	private Integer 	pictureStatus;			//是否有效
	private Long 		pictureOwnerId;		//图片归属者ID 如：旅游产品ID
	private Integer 	pictureType;				//图片类型 如：1 logo  2环境  
	private String 		createTime;					//创建时间
	private String 		createdBy;					//创建人
	private String 		updateTime;				//更新时间
	private String 		updatedBy;					//更新人
	private Integer 	productType;				//产品类型 1旅游产品 2酒店产品 3 酒店房间 4 支付链
	private String 		zhName;						//商户名称
	private String 		startDate;					//查询时间起始段
	private String		 endDate;						//查询时间结束段
	private String 		strCreateDate;			//支付链生成时间
	private String 		strOverdueDate;		//支付链过期时间
	private String 		descriptions;				//描述
	private String  		payChainNumber;  //支付链编号
	private Integer		pageStartRow;			// 起始行
	private Integer 	pageEndRow;				// 结束行
	private String 		remark;							//备注
	private String       payChainName;			//支付链名称
	
	public String getPayChainName() {
		return payChainName;
	}

	public void setPayChainName(String payChainName) {
		this.payChainName = payChainName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public Integer getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getStrOverdueDate() {
		return strOverdueDate;
	}

	public void setStrOverdueDate(String strOverdueDate) {
		this.strOverdueDate = strOverdueDate;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getPayChainNumber() {
		return payChainNumber;
	}

	public void setPayChainNumber(String payChainNumber) {
		this.payChainNumber = payChainNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getZhName() {
		return zhName;
	}
	
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
