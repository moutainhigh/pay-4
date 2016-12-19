/**
 *  File: MassPayToBankFileQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.masspaytobank;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pay.fundout.util.AmountUtils;
import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class MassPayToBankFileQueryDTO extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5529856839042053148L;

	private String payerAcctCode;

	private Long totalFee;


	private Integer status;

	private String remark;

	private Long payerMemberCode;

	private Long uploadSeq; // 上传流水

	private Integer totalNum; // 总记录

	private Integer validNum; // 有效笔数

	private Integer payerAcctType; // 付款账户类型

	private String businessNo; // 批次号

	private Long totalAmount; // 总金额

	private Long validAmount; // 有效金额

	private String operatorid; // 操作员

	private Date uploadDate; // 上传日期

	private String uploadFileName; // 文件名称

	private Long payerMomberCode; // 付款账户

	private String payerOperator;
	
	private Date updateDate;  //更新日期
	
	private String auditOperator;//复核员
	
	private String auditRemark;

	private DecimalFormat format = new DecimalFormat("0.00");

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getPayerOperator() {
		return payerOperator;
	}

	public void setPayerOperator(String payerOperator) {
		this.payerOperator = payerOperator;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public Long getUploadSeq() {
		return uploadSeq;
	}

	public void setUploadSeq(Long uploadSeq) {
		this.uploadSeq = uploadSeq;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getValidNum() {
		return validNum;
	}

	public void setValidNum(Integer validNum) {
		this.validNum = validNum;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Long getPayerMomberCode() {
		return payerMomberCode;
	}

	public void setPayerMomberCode(Long payerMomberCode) {
		this.payerMomberCode = payerMomberCode;
	}

	public String getUploadDateStr() {
		return dateFormat.format(this.uploadDate);
	}

	public String getTotalAmountStr() {
		
		return AmountUtils.numberFormat(totalAmount);
	}

	public String getValidAmountStr() {
		
		return AmountUtils.numberFormat(validAmount);
	}

	public String getErrorAmountStr() {
		
		return AmountUtils.numberFormat(totalAmount - validAmount);
	}

	public Integer getErrorNum() {
		return totalNum - validNum;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAuditOperator() {
		return auditOperator;
	}

	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	
}
