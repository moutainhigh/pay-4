/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterQueryDTO.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-6		Henry.Zeng			Create 
 */
package com.pay.poss.service.ordercenter.dto.list;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p>
 * 订单查询条件
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-6
 * @see
 */
public class OrderCenterQueryDTO extends BaseObject {
	private static final long serialVersionUID = -6715031470652148077L;

	/**
	 * 会员号
	 */
	private String memberCode; 

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 账户类型
	 */
	private String accType;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 系统交易号
	 */
	private String orderKy;

	/**
	 * 商家订单号
	 */
	private String merchantOrderKy;

	/**
	 * 银行订单号
	 */
	private String bankOrderKy;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 付款人MemberCode
	 */
	private String payerMemberCode;
	/**
	 * 付款人账户类型
	 */
	private String payerAccType;
	/**
	 * 收款人MemberCode
	 */
	private String payeeMemberCode;
	/**
	 * 收款人账户类型
	 */
	private String payeeAccType;

	private String workflowKy; // 工作流程实例ID
	/**
	 * 业务类型，参考WithdrawBusinessType定义
	 */
	private String busiType;
	/**
	 * 原订单号
	 */
	private String orderSrc;
	
	/** 业务批次号 **/
	private String batchNo;
	
	/** 总订单号 **/
	private String primaryOrderNo;
	
	/** 登陆标志 **/
	private String loginName;
	
	/** 出款渠道 **/
	private String channelId;
	
	/**
	 * 付款人组织类别
	 */
	private String paymentCatagory;
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPrimaryOrderNo() {
		return primaryOrderNo;
	}

	public void setPrimaryOrderNo(String primaryOrderNo) {
		this.primaryOrderNo = primaryOrderNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/*
	 * private String orderMky; // 批次订单号或主订单号
	 *//**
	 * @return the orderMky
	 */
	/*
	 * public String getOrderMky() { return orderMky; }
	 *//**
	 * @param orderMky
	 *            the orderMky to set
	 */
	/*
	 * public void setOrderMky(String orderMky) { this.orderMky = orderMky; }
	 */

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(String orderKy) {
		this.orderKy = orderKy;
	}

	public String getMerchantOrderKy() {
		return merchantOrderKy;
	}

	public void setMerchantOrderKy(String merchantOrderKy) {
		this.merchantOrderKy = merchantOrderKy;
	}

	public String getBankOrderKy() {
		return bankOrderKy;
	}

	public void setBankOrderKy(String bankOrderKy) {
		this.bankOrderKy = bankOrderKy;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(String payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public String getPayerAccType() {
		return payerAccType;
	}

	public void setPayerAccType(String payerAccType) {
		this.payerAccType = payerAccType;
	}

	public String getPayeeMemberCode() {
		return payeeMemberCode;
	}

	public void setPayeeMemberCode(String payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

	public String getPayeeAccType() {
		return payeeAccType;
	}

	public void setPayeeAccType(String payeeAccType) {
		this.payeeAccType = payeeAccType;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getOrderSrc() {
		return orderSrc;
	}

	public void setOrderSrc(String orderSrc) {
		this.orderSrc = orderSrc;
	}

	public String getPaymentCatagory() {
		return paymentCatagory;
	}

	public void setPaymentCatagory(String paymentCatagory) {
		this.paymentCatagory = paymentCatagory;
	}
	
}
