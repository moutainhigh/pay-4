package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 产生批次所用DTO
 * 
 * @Description
 * @project poss-refund
 * @file RefundBatchDTO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-17 Volcano.Wu Create
 */
public class RefundBatchDTO extends BaseObject {

	private static final long serialVersionUID = 1850455424980407536L;

	private Long rechargeOrderSeq;// 充值流水 //交易号
	private Long rechargeBankSeq;// 充值银行流水 //交易流水号
	private String rechargeBank;// 充值银行。见码表“银行编码”，当不是通过银行充值，此值为“系统银行”。 //银行名称
	// 开户行名称;
	// 银行账户
	private BigDecimal applyAmount;// 此订单充退实际金额，必须<=APPLY_MAX。 //汇款金额
	private String memberCode;// 会员号
	private String memberName;// 会员姓名 //收款人
	// 省份
	// 城市
	private String applyRemark;// 充退批注 //交易备注

	// 银行备注
	// 银行用途
	// 失败原因
	
/**********************************************/
	
	/** 申请冲退时间 **/
	private Timestamp applyTimes;
	
	/** 交易明细流水 **/
	private String detailKy;
	
	/** 批次号  **/
	private String batchNum;
	
/***********************************************/
	
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Date getApplyTimes() {
		return applyTimes;
	}

	public String getDetailKy() {
		return detailKy;
	}

	public void setDetailKy(String detailKy) {
		this.detailKy = detailKy;
	}

	public void setApplyTimes(Timestamp applyTimes) {
		this.applyTimes = applyTimes;
	}

	public Long getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}

	public void setRechargeOrderSeq(Long rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}

	public Long getRechargeBankSeq() {
		return rechargeBankSeq;
	}

	public void setRechargeBankSeq(Long rechargeBankSeq) {
		this.rechargeBankSeq = rechargeBankSeq;
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

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}
}
