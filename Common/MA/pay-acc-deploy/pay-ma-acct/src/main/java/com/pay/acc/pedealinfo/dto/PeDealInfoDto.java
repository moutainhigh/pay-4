package com.pay.acc.pedealinfo.dto;

import java.io.Serializable;
import java.util.Date;

public class PeDealInfoDto implements Serializable {
	private Long id;
	
	private Integer dealCode;
	
	private Integer orderCode;
	
	
	
	private Long orderAmount;
	
	private String submitAcctCode;
	private Integer payMethod;
	
	private Long payMemberCode;
	
	private String payAcctCode;
	
	
	private Integer payAcctType;
	
	private Integer payOrgType;
	
	private Long payOrgCode;
	
	private Long revMemberCode;
	
	private String revAcctCode;
	
	private Integer	revAcctType;
	
	private Integer revOrgType;
	private Long revOrgCode;
	
	private Long payFee;
	private Long revFee;
	
	private Long serialNo;
	private Date createDate;
	private Date updateDate;
	
	private Integer dealType;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the dealCode
	 */
	public Integer getDealCode() {
		return dealCode;
	}
	/**
	 * @param dealCode the dealCode to set
	 */
	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}
	/**
	 * @return the orderCode
	 */
	public Integer getOrderCode() {
		return orderCode;
	}
	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}
	
	
	/**
	 * @return the dealType
	 */
	public Integer getDealType() {
		return dealType;
	}
	/**
	 * @param dealType the dealType to set
	 */
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	/**
	 * @return the orderAmount
	 */
	public Long getOrderAmount() {
		return orderAmount;
	}
	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	/**
	 * @return the submitAcctCode
	 */
	public String getSubmitAcctCode() {
		return submitAcctCode;
	}
	/**
	 * @param submitAcctCode the submitAcctCode to set
	 */
	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}
	/**
	 * @return the payMethod
	 */
	public Integer getPayMethod() {
		return payMethod;
	}
	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	/**
	 * @return the payMemberCode
	 */
	public Long getPayMemberCode() {
		return payMemberCode;
	}
	/**
	 * @param payMemberCode the payMemberCode to set
	 */
	public void setPayMemberCode(Long payMemberCode) {
		this.payMemberCode = payMemberCode;
	}
	/**
	 * @return the payAcctCode
	 */
	public String getPayAcctCode() {
		return payAcctCode;
	}
	/**
	 * @param payAcctCode the payAcctCode to set
	 */
	public void setPayAcctCode(String payAcctCode) {
		this.payAcctCode = payAcctCode;
	}
	/**
	 * @return the payAcctType
	 */
	public Integer getPayAcctType() {
		return payAcctType;
	}
	/**
	 * @param payAcctType the payAcctType to set
	 */
	public void setPayAcctType(Integer payAcctType) {
		this.payAcctType = payAcctType;
	}
	/**
	 * @return the payOrgType
	 */
	public Integer getPayOrgType() {
		return payOrgType;
	}
	/**
	 * @param payOrgType the payOrgType to set
	 */
	public void setPayOrgType(Integer payOrgType) {
		this.payOrgType = payOrgType;
	}
	/**
	 * @return the payOrgCode
	 */
	public Long getPayOrgCode() {
		return payOrgCode;
	}
	/**
	 * @param payOrgCode the payOrgCode to set
	 */
	public void setPayOrgCode(Long payOrgCode) {
		this.payOrgCode = payOrgCode;
	}
	/**
	 * @return the revMemberCode
	 */
	public Long getRevMemberCode() {
		return revMemberCode;
	}
	/**
	 * @param revMemberCode the revMemberCode to set
	 */
	public void setRevMemberCode(Long revMemberCode) {
		this.revMemberCode = revMemberCode;
	}
	/**
	 * @return the revAcctCode
	 */
	public String getRevAcctCode() {
		return revAcctCode;
	}
	/**
	 * @param revAcctCode the revAcctCode to set
	 */
	public void setRevAcctCode(String revAcctCode) {
		this.revAcctCode = revAcctCode;
	}
	/**
	 * @return the revAcctType
	 */
	public Integer getRevAcctType() {
		return revAcctType;
	}
	/**
	 * @param revAcctType the revAcctType to set
	 */
	public void setRevAcctType(Integer revAcctType) {
		this.revAcctType = revAcctType;
	}
	/**
	 * @return the revOrgType
	 */
	public Integer getRevOrgType() {
		return revOrgType;
	}
	/**
	 * @param revOrgType the revOrgType to set
	 */
	public void setRevOrgType(Integer revOrgType) {
		this.revOrgType = revOrgType;
	}
	/**
	 * @return the revOrgCode
	 */
	public Long getRevOrgCode() {
		return revOrgCode;
	}
	/**
	 * @param revOrgCode the revOrgCode to set
	 */
	public void setRevOrgCode(Long revOrgCode) {
		this.revOrgCode = revOrgCode;
	}
	/**
	 * @return the payFee
	 */
	public Long getPayFee() {
		return payFee;
	}
	/**
	 * @param payFee the payFee to set
	 */
	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}
	/**
	 * @return the revFee
	 */
	public Long getRevFee() {
		return revFee;
	}
	/**
	 * @param revFee the revFee to set
	 */
	public void setRevFee(Long revFee) {
		this.revFee = revFee;
	}
	/**
	 * @return the serialNo
	 */
	public Long getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PeDealInfo [createDate=" + createDate + ", dealCode=" + dealCode + ", id=" + id + ", orderAmount=" + orderAmount
				+ ", orderCode=" + orderCode + ", payAcctCode=" + payAcctCode + ", payAcctType=" + payAcctType + ", payFee=" + payFee
				+ ", payMemberCode=" + payMemberCode + ", payMethod=" + payMethod + ", payOrgCode=" + payOrgCode + ", payOrgType="
				+ payOrgType + ", revAcctCode=" + revAcctCode + ", revAcctType=" + revAcctType + ", revFee=" + revFee + ", revMemberCode="
				+ revMemberCode + ", revOrgCode=" + revOrgCode + ", revOrgType=" + revOrgType + ", serialNo=" + serialNo
				+ ", submitAcctCode=" + submitAcctCode + ", updateDate=" + updateDate + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((dealCode == null) ? 0 : dealCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderAmount == null) ? 0 : orderAmount.hashCode());
		result = prime * result + ((orderCode == null) ? 0 : orderCode.hashCode());
		result = prime * result + ((payAcctCode == null) ? 0 : payAcctCode.hashCode());
		result = prime * result + ((payAcctType == null) ? 0 : payAcctType.hashCode());
		result = prime * result + ((payFee == null) ? 0 : payFee.hashCode());
		result = prime * result + ((payMemberCode == null) ? 0 : payMemberCode.hashCode());
		result = prime * result + ((payMethod == null) ? 0 : payMethod.hashCode());
		result = prime * result + ((payOrgCode == null) ? 0 : payOrgCode.hashCode());
		result = prime * result + ((payOrgType == null) ? 0 : payOrgType.hashCode());
		result = prime * result + ((revAcctCode == null) ? 0 : revAcctCode.hashCode());
		result = prime * result + ((revAcctType == null) ? 0 : revAcctType.hashCode());
		result = prime * result + ((revFee == null) ? 0 : revFee.hashCode());
		result = prime * result + ((revMemberCode == null) ? 0 : revMemberCode.hashCode());
		result = prime * result + ((revOrgCode == null) ? 0 : revOrgCode.hashCode());
		result = prime * result + ((revOrgType == null) ? 0 : revOrgType.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		result = prime * result + ((submitAcctCode == null) ? 0 : submitAcctCode.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		return result;
	}
	
	
	
	
	

}
