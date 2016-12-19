package com.pay.poss.refund.model;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**
 * 查询充退数据DTO
 * 
 * @Description
 * @project poss-refund
 * @file RefundHandworkBatchDTO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-19 Volcano.Wu Create
 */
public class RefundHandworkBatchDTO extends BaseObject {

	private static final long serialVersionUID = 2742191570773941465L;

	private Long workorderKy;// 工单号
	private Long orderKy;// 交易号
	private Long detailKy;// 交易流水号
	private String rechargeBank;// 银行名称
	private BigDecimal applyAmount;// 充退金额
	private String memberName;// 会员姓名
	private String applyRemark;// 交易备注
	private Integer status;// 状态
	private Long applyAmountLong;// 充退金额
	
	private String statusDesc;//状态描述

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Long getApplyAmountLong() {
		applyAmountLong = applyAmount.longValue();
		return applyAmountLong;
	}

	public void setApplyAmountLong(Long applyAmountLong) {
		this.applyAmountLong = applyAmountLong;
	}

	public Long getWorkorderKy() {
		return workorderKy;
	}

	public void setWorkorderKy(Long workorderKy) {
		this.workorderKy = workorderKy;
	}

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public Long getDetailKy() {
		return detailKy;
	}

	public void setDetailKy(Long detailKy) {
		this.detailKy = detailKy;
	}

	public String getRechargeBank() {
		return rechargeBank;
	}

	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
