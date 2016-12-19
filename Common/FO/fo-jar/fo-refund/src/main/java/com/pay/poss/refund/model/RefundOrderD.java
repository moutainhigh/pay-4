/** @Description 
 * @project 	poss-refund
 * @file 		RefundOrderD.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
 */
package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * 对应充退订单从表信息
 * 
 * @author sunsea_li
 * 
 */
public class RefundOrderD extends BaseOrderDTO {
	
	private static final long serialVersionUID = 1983348097302858988L;
	private Long detailKy;// 充退明细流水(主键)
	private Long masterKy;// (外键)关联REFUND_ORDER_M表
	private Long rechargeOrderSeq;// 充值流水(支付系统中的一个业务主键)
	private String rechargeBankSeq;// 充值银行流水(银行返回给支付的一个流水号)
	private BigDecimal rechargeAmount;// 充值金额
	private Date rechargeTime;// 充值交易时间
	private String rechargeChannel;// 充值渠道。见码表“交易渠道”，默认WEB渠道。
	private String rechargeBank;// 充值银行。见码表“银行编码”，当不是通过银行充值，此值为“系统银行”。
	private String applyRemark;// 充退批注
	private BigDecimal applyMax;// 可充退金额，以分为单位.
	private BigDecimal applyAmount;// 此订单充退实际金额，必须<=APPLY_MAX。
	private Integer showPosition;// 页面显示位置值，升序
	private Integer status;// 此订单中该充值的充退状态。对账之后更新
	private String errorTip;// 此订单中该充值的充退失败原因。对账之后更新

	private String depositTypeName;// 充值渠道。见fi.payment_channel.name。
	private String depositBackNo;// fi充退流水。

	private String rechargeBankOrder;// 银行和支付都认识的一个银行订单号

	private String refundOrderNo;

	public String getRechargeBankSeq() {
		return rechargeBankSeq;
	}

	public void setRechargeBankSeq(String rechargeBankSeq) {
		this.rechargeBankSeq = rechargeBankSeq;
	}

	public String getRechargeBankOrder() {
		return rechargeBankOrder;
	}

	public void setRechargeBankOrder(String rechargeBankOrder) {
		this.rechargeBankOrder = rechargeBankOrder;
	}

	public Long getDetailKy() {
		return detailKy;
	}

	public void setDetailKy(Long detailKy) {
		this.detailKy = detailKy;
	}

	public Long getMasterKy() {
		return masterKy;
	}

	public void setMasterKy(Long masterKy) {
		this.masterKy = masterKy;
	}

	public Long getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}

	public void setRechargeOrderSeq(Long rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getRechargeChannel() {
		return rechargeChannel;
	}

	public void setRechargeChannel(String rechargeChannel) {
		this.rechargeChannel = rechargeChannel;
	}

	public String getRechargeBank() {
		return rechargeBank;
	}

	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public BigDecimal getApplyMax() {
		return applyMax;
	}

	public void setApplyMax(BigDecimal applyMax) {
		this.applyMax = applyMax;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Integer getShowPosition() {
		return showPosition;
	}

	public void setShowPosition(Integer showPosition) {
		this.showPosition = showPosition;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorTip() {
		return errorTip;
	}

	public void setErrorTip(String errorTip) {
		this.errorTip = errorTip;
	}

	public String getDepositTypeName() {
		return depositTypeName;
	}

	public void setDepositTypeName(String depositTypeName) {
		this.depositTypeName = depositTypeName;
	}

	public String getDepositBackNo() {
		return depositBackNo;
	}

	public void setDepositBackNo(String depositBackNo) {
		this.depositBackNo = depositBackNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

}