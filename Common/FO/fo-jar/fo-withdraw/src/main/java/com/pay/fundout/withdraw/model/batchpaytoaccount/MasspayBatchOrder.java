/**
 *  File: MasspayBatchOrder.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.batchpaytoaccount;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * @author darv
 * 
 */
public class MasspayBatchOrder extends BaseOrderDTO {
	private String payerAcctCode;
	private String payerCode;
	private java.util.Date createTime;
	private Integer status;
	private java.util.Date updateTime;
	private Long payerMember;
	private Integer payerAcctType;
	private String payeeAcctCode;
	private String callSeq;
	private String batchNum;
	private String payeeCode;
	private String orgName;
	private Long payeeMember;
	private Long payTotalAmount;
	private Integer payTotalCount;
	private Integer payeeAcctType;
	private String operators;
	private Long sequenceId;
	private Integer oldStatus;

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

    /*吕宏修改:统一用父类订单状态*/
	public Integer getStatus() {
		return getInnerStatus();
	}

	public void setStatus(Integer status) {
		setInnerStatus(status);
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(Long payerMember) {
		this.payerMember = payerMember;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public String getCallSeq() {
		return callSeq;
	}

	public void setCallSeq(String callSeq) {
		this.callSeq = callSeq;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(Long payeeMember) {
		this.payeeMember = payeeMember;
	}

	public Long getPayTotalAmount() {
		return payTotalAmount;
	}

	public void setPayTotalAmount(Long payTotalAmount) {
		this.payTotalAmount = payTotalAmount;
	}

	public Integer getPayTotalCount() {
		return payTotalCount;
	}

	public void setPayTotalCount(Integer payTotalCount) {
		this.payTotalCount = payTotalCount;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}
	
}
