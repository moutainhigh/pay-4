/**
 *  File: OrderByMasspayToBankDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-28     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.orderconsistency.masspaytobank;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class OrderByMasspayToBankDTO extends BaseObject {
	
	private Long detailSeq;
	private Long massOrderSeq;
	private String businessNo;
	private Long payerMemberCode;
	private Integer totalNum;
	private Long totalAmount;
	private Long totalFee;
	private Integer status;
	private Date createDate;
	private Long uploadSeq;
	private Date uploadDate;
	private String openingBankName;
	private Long amount;
	private String uploadAmount;
	private Integer orderStatus;
	private String payeeBankAcct;
	private Integer validateStatus;

	public Long getDetailSeq() {
		return detailSeq;
	}

	public void setDetailSeq(Long detailSeq) {
		this.detailSeq = detailSeq;
	}

	public String getPayeeBankAcct() {
		return payeeBankAcct;
	}

	public void setPayeeBankAcct(String payeeBankAcct) {
		this.payeeBankAcct = payeeBankAcct;
	}

	public Integer getValidateStatus() {
		return validateStatus;
	}

	public void setValidateStatus(Integer validateStatus) {
		this.validateStatus = validateStatus;
	}

	public Long getMassOrderSeq() {
		return massOrderSeq;
	}

	public void setMassOrderSeq(Long massOrderSeq) {
		this.massOrderSeq = massOrderSeq;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUploadSeq() {
		return uploadSeq;
	}

	public void setUploadSeq(Long uploadSeq) {
		this.uploadSeq = uploadSeq;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getOpeningBankName() {
		return openingBankName;
	}

	public void setOpeningBankName(String openingBankName) {
		this.openingBankName = openingBankName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getUploadAmount() {
		return uploadAmount;
	}

	public void setUploadAmount(String uploadAmount) {
		this.uploadAmount = uploadAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
