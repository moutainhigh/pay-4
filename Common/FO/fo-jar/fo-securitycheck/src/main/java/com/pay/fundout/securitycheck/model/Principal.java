/** @Description 
 * @project 	fo-securitycheck
 * @file 		Principal.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.model;

import java.util.Date;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * <p>
 * 验证主体
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public class Principal {
	private String busiType;// 必须设置。 业务类型编码, 建议参考WithdrawBusinessType
	private String orderSeq;// 订单编号
	private String workorderSeq;// 工单编号
	private String orderSeqSrc;// 原订单编号(退款业务)
	private Date tradeTime;

	private long orderAmount;// 订单金额,用户录入金额
	private long amount;// 出款金额，收款方实收金额
	private long fee;// 费用
	private boolean payerPayFee = true;// true为付款方付手续费，false收款方付手续费
	private long payAmount;// 实付金额，付款方实付金额
	private int orderStatus = 101;// 订单状态
	private int workorderStatus = 1;// 工单状态

	private String payerMemberCode; // 必须设置。
	private int payerMemberType = -1;// 必须设置。
	private String payerAcctCode;// 必须设置。
	private String payerAcctType;// 必须设置。
	private String payerCode;
	private int payerAuthLevel = 0;
	private int payerBWFlag = -2;// 1为白名单，-1为黑名单，0为不黑不白
	private int payerMemberStatus = -1;// 0为创建，1正常状态（激活成功），2冻结状态，3.未激活，4删除状态
	// 5临时账号

	private String payeeMemberCode;// 必须设置
	private int payeeMemberType = -1;
	private String payeeAcctCode;
	private String payeeAcctType;// 必须设置
	private String payeeCode;
	private int payeeAuthLevel = 0;
	private int payeeBWFlag = -2;// 1为白名单，-1为黑名单，0为不黑不白
	private int payeeMemberStatus = -1;// 0为创建，1正常状态（激活成功），2冻结状态，3.未激活，4删除状态
	// 5临时账号

	private AcctAttribDto payer;
	private AcctAttribDto payee;
	private MemberBaseInfoBO payerMember;
	private MemberBaseInfoBO payeeMember;
	private BankAcctAttribDto bankInfo;
	private RCLimitResultDTO riskRule;// 缓存用

	public Principal(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiType() {
		return busiType;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getPayerMemberCode() {
		return payerMemberCode;
	}

	public int getPayerMemberType() {
		return payerMemberType;
	}

	public void setPayerMemberType(int payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	public int getPayeeMemberType() {
		return payeeMemberType;
	}

	public void setPayeeMemberType(int payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	public void setPayerMemberCode(String payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public String getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(String payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getPayeeMemberCode() {
		return payeeMemberCode;
	}

	public void setPayeeMemberCode(String payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public String getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(String payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public int getPayerAuthLevel() {
		return payerAuthLevel;
	}

	public void setPayerAuthLevel(int payerAuthLevel) {
		this.payerAuthLevel = payerAuthLevel;
	}

	public int getPayeeAuthLevel() {
		return payeeAuthLevel;
	}

	public void setPayeeAuthLevel(int payeeAuthLevel) {
		this.payeeAuthLevel = payeeAuthLevel;
	}

	public String getWorkorderSeq() {
		return workorderSeq;
	}

	public void setWorkorderSeq(String workorderSeq) {
		this.workorderSeq = workorderSeq;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getWorkorderStatus() {
		return workorderStatus;
	}

	public void setWorkorderStatus(int workorderStatus) {
		this.workorderStatus = workorderStatus;
	}

	public String getOrderSeqSrc() {
		return orderSeqSrc;
	}

	public void setOrderSeqSrc(String orderSeqSrc) {
		this.orderSeqSrc = orderSeqSrc;
	}

	public long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public boolean isPayerPayFee() {
		return payerPayFee;
	}

	public void setPayerPayFee(boolean payerPayFee) {
		this.payerPayFee = payerPayFee;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public RCLimitResultDTO getRiskRule() {
		return riskRule;
	}

	public void setRiskRule(RCLimitResultDTO riskRule) {
		this.riskRule = riskRule;
	}

	public AcctAttribDto getPayer() {
		return payer;
	}

	public void setPayer(AcctAttribDto payer) {
		this.payer = payer;
	}

	public AcctAttribDto getPayee() {
		return payee;
	}

	public void setPayee(AcctAttribDto payee) {
		this.payee = payee;
	}

	public int getPayerBWFlag() {
		return payerBWFlag;
	}

	public void setPayerBWFlag(int payerBWFlag) {
		this.payerBWFlag = payerBWFlag;
	}

	public int getPayeeBWFlag() {
		return payeeBWFlag;
	}

	public void setPayeeBWFlag(int payeeBWFlag) {
		this.payeeBWFlag = payeeBWFlag;
	}

	public BankAcctAttribDto getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankAcctAttribDto bankInfo) {
		this.bankInfo = bankInfo;
	}

	public int getPayerMemberStatus() {
		return payerMemberStatus;
	}

	public void setPayerMemberStatus(int payerMemberStatus) {
		this.payerMemberStatus = payerMemberStatus;
	}

	public int getPayeeMemberStatus() {
		return payeeMemberStatus;
	}

	public void setPayeeMemberStatus(int payeeMemberStatus) {
		this.payeeMemberStatus = payeeMemberStatus;
	}

	public MemberBaseInfoBO getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(MemberBaseInfoBO payerMember) {
		this.payerMember = payerMember;
	}

	public MemberBaseInfoBO getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(MemberBaseInfoBO payeeMember) {
		this.payeeMember = payeeMember;
	}

	public String toString() {
		StringBuffer result = new StringBuffer("PRINCIPAL [");
		result.append("busiType=").append(busiType);
		result.append(";payerMemberCode=").append(payerMemberCode);
		result.append(";payerMemberType=").append(payerMemberType);
		result.append(";payerAcctType=").append(payerAcctType);
		result.append(";payeeMemberCode=").append(payeeMemberCode);
		result.append(";payeeMemberType=").append(payeeMemberType).append("]");
		return result.toString();
	}
}
