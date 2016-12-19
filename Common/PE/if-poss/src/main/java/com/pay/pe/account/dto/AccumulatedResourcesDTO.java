/**
 * 
 */
package com.pay.pe.account.dto;

import java.util.Date;

/**
 * @author 戴德荣
 * 
 */
public class AccumulatedResourcesDTO {
	
	private Long id;
	private Integer orderType;
	private Integer dealCode;
	private Integer dealType;
	private Integer paymentServiceCode;
	private Integer takeOn;
	private Date createDate;
	private Date updateDate;
	private String createdBy;
	private String modifiedBy;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getDealCode() {
		return dealCode;
	}
	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}
	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}
	public Integer getTakeOn() {
		return takeOn;
	}
	public void setTakeOn(Integer takeOn) {
		this.takeOn = takeOn;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	
	
	
	


}
