/**
 *  File: OrderDetailDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Volcano.Wu      Changes
 *  
 *
 */
package com.pay.poss.service.ordercenter.dto.detail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.inf.model.BaseObject;

/**
 * <p>
 * 订单详情
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-11-8
 * @see
 */
public class OrderDetailDTO extends BaseObject {

	private static final long serialVersionUID = -4938910173414171637L;

	List<WorkFlowHistory> wfHisList;// 操作历史记录

	/**
	 * 会员号
	 */
	private String memberCode;

	/* 付款人信息 */
	/**
	 * 付款人用户号
	 */
	private String payerKy;

	/**
	 * 付款人用户 姓名
	 */
	private String payerName;

	/**
	 * 付款人用户等级
	 */
	private String payerLevel;

	/**
	 * 付款人用户标识
	 */
	private String payerId;

	/**
	 * 付款人账户类型
	 */
	private String payerAcctType;

	/**
	 * 付款人账户
	 */
	private String payerAcct;

	/* 收款人信息 */
	/**
	 * 收款人用户号
	 */
	private String payeeKy;

	/**
	 * 收款人姓名
	 */
	private String payeeName;

	/**
	 * 收款人用户等级
	 */
	private String payeeLevel;

	/**
	 * 收款人用户标识
	 */
	private String payeeId;

	/**
	 * 收款人账户类型
	 */
	private String payeeAcctType;

	/**
	 * 收款人账户号
	 */

	private String payeeAcct;

	/* 订单信息 */
	/**
	 * 商家订单号
	 */
	private String orderId;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 订单创建时间
	 */
	private Date orderCreateTime;

	/**
	 * 订单结束时间
	 */
	private Date orderEndTime;

	/**
	 * 支付方式
	 */
	private String payment;

	/**
	 * 交易状态
	 */
	private String orderStatus;

	/**
	 * 交易金额
	 */
	private BigDecimal orderAccount;

	/**
	 * 付款方费用
	 */
	private BigDecimal payerFee;
	
	/**
	 * 收款方费用
	 */
	private BigDecimal payeeFee;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 付款人IP
	 */
	private String payerIp;

	/**
	 * 交易网址
	 */
	private String orderWebsite;

	/* 订单独特信息 */
	/**
	 * 收款人姓名
	 */

	/**
	 * 订单类型
	 */

	/**
	 * 银行卡号
	 */
	private String bankAcct;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 开户行
	 */
	private String bankBranch;

	/**
	 * 省份
	 */
	private String provinces;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 清算批次号
	 */
	private String liquidateBatchKy;

	/**
	 * 留言
	 */
	private String remarks;
	/**
	 * 失败原因
	 */
	private String failedReason;

	private Date webAuditTime;// 前台复核通过时间

	private String workOrderStatus; // 银企直联出款状态

	private String channelBank; // 渠道出款银行
	
	private Map<String, Object> carryOverOrderMap; //结转金额实体map，给神州行卡出现结转金额存放结转金额对应实体信息用

	public String getChannelBank() {
		return channelBank;
	}

	public void setChannelBank(String channelBank) {
		this.channelBank = channelBank;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	/**
	 * @return the webAuditTime
	 */
	public Date getWebAuditTime() {
		return webAuditTime;
	}

	/**
	 * @param webAuditTime
	 *            the webAuditTime to set
	 */
	public void setWebAuditTime(Date webAuditTime) {
		this.webAuditTime = webAuditTime;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the payerKy
	 */
	public String getPayerKy() {
		return payerKy;
	}

	/**
	 * @param payerKy
	 *            the payerKy to set
	 */
	public void setPayerKy(String payerKy) {
		this.payerKy = payerKy;
	}

	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * @param payerName
	 *            the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/**
	 * @return the payerId
	 */
	public String getPayerId() {
		return payerId;
	}

	/**
	 * @param payerId
	 *            the payerId to set
	 */
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	/**
	 * @return the payerAcctType
	 */
	public String getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @param payerAcctType
	 *            the payerAcctType to set
	 */
	public void setPayerAcctType(String payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	/**
	 * @return the payerAcct
	 */
	public String getPayerAcct() {
		return payerAcct;
	}

	/**
	 * @param payerAcct
	 *            the payerAcct to set
	 */
	public void setPayerAcct(String payerAcct) {
		this.payerAcct = payerAcct;
	}

	/**
	 * @return the payeeKy
	 */
	public String getPayeeKy() {
		return payeeKy;
	}

	/**
	 * @param payeeKy
	 *            the payeeKy to set
	 */
	public void setPayeeKy(String payeeKy) {
		this.payeeKy = payeeKy;
	}

	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 *            the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return the payeeId
	 */
	public String getPayeeId() {
		return payeeId;
	}

	/**
	 * @param payeeId
	 *            the payeeId to set
	 */
	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	/**
	 * @return the payeeAcctType
	 */
	public String getPayeeAcctType() {
		return payeeAcctType;
	}

	/**
	 * @param payeeAcctType
	 *            the payeeAcctType to set
	 */
	public void setPayeeAcctType(String payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	/**
	 * @return the payeeAcct
	 */
	public String getPayeeAcct() {
		return payeeAcct;
	}

	/**
	 * @param payeeAcct
	 *            the payeeAcct to set
	 */
	public void setPayeeAcct(String payeeAcct) {
		this.payeeAcct = payeeAcct;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the orderCreateTime
	 */
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	/**
	 * @param orderCreateTime
	 *            the orderCreateTime to set
	 */
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	/**
	 * @return the orderEndTime
	 */
	public Date getOrderEndTime() {
		return orderEndTime;
	}

	/**
	 * @param orderEndTime
	 *            the orderEndTime to set
	 */
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the orderAccount
	 */
	public BigDecimal getOrderAccount() {
		return orderAccount;
	}

	/**
	 * @param orderAccount
	 *            the orderAccount to set
	 */
	public void setOrderAccount(BigDecimal orderAccount) {
		this.orderAccount = orderAccount;
	}

	public BigDecimal getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(BigDecimal payerFee) {
		this.payerFee = payerFee;
	}

	public BigDecimal getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(BigDecimal payeeFee) {
		this.payeeFee = payeeFee;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the payerIp
	 */
	public String getPayerIp() {
		return payerIp;
	}

	/**
	 * @param payerIp
	 *            the payerIp to set
	 */
	public void setPayerIp(String payerIp) {
		this.payerIp = payerIp;
	}

	/**
	 * @return the orderWebsite
	 */
	public String getOrderWebsite() {
		return orderWebsite;
	}

	/**
	 * @param orderWebsite
	 *            the orderWebsite to set
	 */
	public void setOrderWebsite(String orderWebsite) {
		this.orderWebsite = orderWebsite;
	}

	/**
	 * @return the bankAcct
	 */
	public String getBankAcct() {
		return bankAcct;
	}

	/**
	 * @param bankAcct
	 *            the bankAcct to set
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the provinces
	 */
	public String getProvinces() {
		return provinces;
	}

	/**
	 * @param provinces
	 *            the provinces to set
	 */
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the liquidateBatchKy
	 */
	public String getLiquidateBatchKy() {
		return liquidateBatchKy;
	}

	/**
	 * @param liquidateBatchKy
	 *            the liquidateBatchKy to set
	 */
	public void setLiquidateBatchKy(String liquidateBatchKy) {
		this.liquidateBatchKy = liquidateBatchKy;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the payerLevel
	 */
	public String getPayerLevel() {
		return payerLevel;
	}

	/**
	 * @param payerLevel
	 *            the payerLevel to set
	 */
	public void setPayerLevel(String payerLevel) {
		this.payerLevel = payerLevel;
	}

	/**
	 * @return the payeeLevel
	 */
	public String getPayeeLevel() {
		return payeeLevel;
	}

	/**
	 * @param payeeLevel
	 *            the payeeLevel to set
	 */
	public void setPayeeLevel(String payeeLevel) {
		this.payeeLevel = payeeLevel;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	/**
	 * @param wfHisList
	 *            the wfHisList to set
	 */
	public void setWfHisList(List<WorkFlowHistory> wfHisList) {
		this.wfHisList = wfHisList;
	}

	/**
	 * @return the wfHisList
	 */
	public List<WorkFlowHistory> getWfHisList() {
		return wfHisList;
	}

	public Map<String, Object> getCarryOverOrderMap() {
		return carryOverOrderMap;
	}

	public void setCarryOverOrderMap(Map<String, Object> carryOverOrderMap) {
		this.carryOverOrderMap = carryOverOrderMap;
	}
	
}
