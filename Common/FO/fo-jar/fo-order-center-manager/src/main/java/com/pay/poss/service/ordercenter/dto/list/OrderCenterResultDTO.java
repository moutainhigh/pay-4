/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterResultDTO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-6		Henry.Zeng			Create 
 */
package com.pay.poss.service.ordercenter.dto.list;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.fundout.service.OrderStatus;
import com.pay.inf.model.BaseObject;
import com.pay.util.DateUtil;

/**
 * <p>
 * 订单查询结果
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-6
 * @see
 */
public class OrderCenterResultDTO extends BaseObject {
	private static final long serialVersionUID = 5653457743293708112L;
	/**
	 * 系统交易号
	 */
	private Long orderKy;
	private String orderKyStr;

	/**
	 * 银行订单号
	 */
	private String bankOrderKy;

	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 商户订单金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 实付金额
	 */
	private BigDecimal orderRealAmount;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	private String orderStatusStr;
	/**
	 * 付款人账号
	 */
	private String payerAccountNo;
	/**
	 * 付款人组织号
	 */
	private String payerBankNo;
	/**
	 * 付款人组织名称
	 */
	private String payerBankName;
	/**
	 * 收款人账号
	 */
	private String payeeAccountNo;
	/**
	 * 收款人组织组织号
	 */
	private String payeeBankNo;
	/**
	 * 收款人组织组织名称
	 */
	private String payeeBankName;
	/**
	 * 订单时间
	 */
	private Date orderDate;
	private String orderDateStr;

	// 工作流程实例ID
	private String workflowKy;

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
	/**
	 * 业务类型，参考WithdrawBusinessType定义
	 */
	private String busiType;
	/**
	 * 原订单号
	 */
	private String orderSrc;
	
	/**
	 * 付款人组织类别
	 */
	private String paymentCatagory;
	
	private String debitFlag;
	
	public String getDebitFlag() {
		return debitFlag;
	}

	public void setDebitFlag(String debitFlag) {
		this.debitFlag = debitFlag;
	}

	public String getOrderKyStr() {
		return orderKy.toString();
	}

	public void setOrderKyStr(String orderKyStr) {
		this.orderKyStr = orderKyStr;
	}

	public String getOrderDateStr() {
		return DateUtil.dateToStr(orderDate, "yyyy-MM-dd HH:mm:ss");
	}

	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public String getBankOrderKy() {
		return bankOrderKy;
	}

	public void setBankOrderKy(String bankOrderKy) {
		this.bankOrderKy = bankOrderKy;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public BigDecimal getOrderRealAmount() {
		return orderRealAmount;
	}

	public void setOrderRealAmount(BigDecimal orderRealAmount) {
		this.orderRealAmount = orderRealAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getPayerBankNo() {
		return payerBankNo;
	}

	public void setPayerBankNo(String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	public String getPayeeBankNo() {
		return payeeBankNo;
	}

	public void setPayeeBankNo(String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the payerMemberCode
	 */
	public String getPayerMemberCode() {
		return payerMemberCode;
	}

	/**
	 * @param payerMemberCode
	 *            the payerMemberCode to set
	 */
	public void setPayerMemberCode(String payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	/**
	 * @return the payerAccType
	 */
	public String getPayerAccType() {
		return payerAccType;
	}

	/**
	 * @param payerAccType
	 *            the payerAccType to set
	 */
	public void setPayerAccType(String payerAccType) {
		this.payerAccType = payerAccType;
	}

	/**
	 * @return the payeeMemberCode
	 */
	public String getPayeeMemberCode() {
		return payeeMemberCode;
	}

	/**
	 * @param payeeMemberCode
	 *            the payeeMemberCode to set
	 */
	public void setPayeeMemberCode(String payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

	/**
	 * @return the payeeAccType
	 */
	public String getPayeeAccType() {
		return payeeAccType;
	}

	/**
	 * @param payeeAccType
	 *            the payeeAccType to set
	 */
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

	public String getOrderStatusStr() {
		return OrderStatus.get(orderStatus).getDesc();
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getPaymentCatagory() {
		return paymentCatagory;
	}

	public void setPaymentCatagory(String paymentCatagory) {
		this.paymentCatagory = paymentCatagory;
	}
	
}
