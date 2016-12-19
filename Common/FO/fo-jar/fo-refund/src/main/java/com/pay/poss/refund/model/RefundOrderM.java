/** @Description 
 * @project 	poss-refund
 * @file 		RefundOrderM.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
 */
package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * 对应充退订单主表信息
 * 
 * @author sunsea_li
 * 
 */
public class RefundOrderM extends BaseOrderDTO {
	private static final long serialVersionUID = 9121354694989838850L;
	private Long orderKy;// 订单号(主键)
	private String memberCode;// 会员号
	private String memberName;// 会员姓名
	private String memberType;// 申请人会员类型。默认个人会员
	private String memberAcc;// 账户号（登录名）
	private Integer memberAccType;// 帐户类型。默认人民币账户
	private Date applyTime;// 充退申请时间
	private BigDecimal applyAmount;// 充退申请金额，以分为单位
	private String applyReason;// 充退申请理由。
	private String applyFrom;// 申请发起系统标识，目前有APP和POSS
	private Integer status;// 充退受理状态
	/*
	 * 0：充退处理中 1：充退成功 2：充退失败
	 */

	private List<RefundOrderD> listDetails;// 充退明细列表
	private String workflowKy; // 工作流程实例ID
	private String workOrderStatus; // 工单状态
	private Long workOrderKy; // 工单编号

	private BigDecimal balance;// 账户余额
	private Integer memberLevel; // 会员等级
	private String operatorIp; // 交易ip地址

	private Long detailKy;// 充退明细流水(主键)
	private Long fileKy;
	private String batchNum;
	private String bankCode;
	private Long rechargeOrderSeq;// 充值流水
	private Integer batchStatus;

	private String overStatus;// 1:清算拒绝 2：完成
	private String handler;

	private String depositBackNo;// fi充退流水。

	private String payee; // 退款商户CODE

	private String payeeName; // 退款商户NAME

	private String refundOrderNo; //退款订单号

	private String tradeOrderNo; //网关订单号
	
	private String  orgCode; //渠道

	private String  currencyCode; //币种
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getDepositBackNo() {
		return depositBackNo;
	}

	public void setDepositBackNo(String depositBackNo) {
		this.depositBackNo = depositBackNo;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getOverStatus() {
		return overStatus;
	}

	public void setOverStatus(String overStatus) {
		this.overStatus = overStatus;
	}

	public Integer getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Long getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}

	public void setRechargeOrderSeq(Long rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum
	 *            the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	public Long getDetailKy() {
		return detailKy;
	}

	public void setDetailKy(Long detailKy) {
		this.detailKy = detailKy;
	}

	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getWorkOrderKy() {
		return workOrderKy;
	}

	public void setWorkOrderKy(Long workOrderKy) {
		this.workOrderKy = workOrderKy;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Integer getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Integer memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getApplyFrom() {
		return applyFrom;
	}

	public void setApplyFrom(String applyFrom) {
		this.applyFrom = applyFrom;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RefundOrderD> getListDetails() {
		return listDetails;
	}

	public void setListDetails(List<RefundOrderD> listDetails) {
		this.listDetails = listDetails;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

}