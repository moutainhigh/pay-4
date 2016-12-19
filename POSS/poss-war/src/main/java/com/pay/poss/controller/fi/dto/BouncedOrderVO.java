package com.pay.poss.controller.fi.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拒付订单表
 * File: BouncedResultDTO.java Description: 
 * Copyright 2016-2030 IPAYLINKS Corporation. 
 * All rights reserved. 
 * Date Author Changes 2016年5月10日
 * mmzhang Create
 *
 */
public class BouncedOrderVO {


	// 商户号，会员号，商户名称，交易日期，录入日期，分类（内部、银行、拒付），原交易金额、原交易币种、结算金额、结算币种
	public static String[] columnHeader = { "商户号", "会员号", "商户名称", "交易日期",
			"录入日期", "分类(内部、银行、拒付)", "原交易金额", "原交易币种", "结算金额", "结算币种" };

	public static String[] properties = { "merchantCode", "memberCode",
			"merchantName", "tradeDateDesc", "createDateDesc", "cpTypeDesc",
			"tradeAmountDesc", "currencyCode", "settlementAmountDesc",
			"settlementCurrencyCode" };

	/**
	 * 渠道编号
	 */
	private String orgCode;
	/**
	 * 拒付订单号
	 */
	private Long orderId;

	/**
	 * 档号
	 */
	private String refNo;

	/**
	 * 渠道订单号
	 */
	private Long channelOrderId;

	/**
	 * 网关订单号
	 */
	private Long tradeOrderNo;

	/**
	 * 交易日期
	 */
	private Date tradeDate;

	private String tradeDateDesc;

	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	private BigDecimal payAmount;
	


	/**
	 * 交易授权号
	 */
	private String authorisation;

	/**
	 * 退单金额
	 */
	private String chargeBackAmount;

	/**
	 * 拒付原因
	 */
	private String chargeBackMsg;

	/**
	 * 渠道原档号
	 */
	private String oldRefNo;

	/**
	 * CPD日期
	 */
	private String cpdDate;

	/**
	 * 状态：0-初始
	 */
	private Integer status;
	
	private String statusDesc;

	/**
	 * null
	 */
	private Date createDate;

	/**
	 * 录入日期
	 */
	private String createDateDesc;

	/**
	 * 支付卡号
	 */
	private String cardNo;

	/**
	 * null
	 */
	private String operator;

	private Integer cpType;

	private String cpTypeDesc;

	/**
	 * 持卡人邮箱
	 */
	private String cardHolderEmail;

	private String merchantCode;

	private String memberCode;

	private String merchantName;

	private String ip;

	//支付币种
	private String currencyCode;

	private Long settlementAmount;

	private String settlementAmountDesc;

	private String settlementCurrencyCode;

	private String auditOperator;

	private String auditMsg;
	

	private String chargeBackMsg1;

	// 1-全额拒付，2-部分拒付
	private Integer cpFlg;

	private String cpCurrencyCode;
	//拒付原因
	private String reasonCode;
	//显示原因
	private String visableCode;
	//资金状态
	private String amountStatus;
	//基本户扣款
	private BigDecimal baseAmount;
	//保证金扣款
	private BigDecimal assureAmount;
	//备注
	private String remark;
	private Date auditDate;
	//最晚回复时间
	private Date latestAnswerDate;
	private String batchNo;
	private String beginCreateDate;
	private String endCreateDate;
	private String tradeEndTime;
	private String tradeBeginTime;
	private String tranCurrencyCode;
	private String orderNo;
	private Integer accountingFlg;
	private String cardOrg;
	// 拒付笔数
	private BigDecimal bouncedCount;
	// 欺诈金额
	private BigDecimal fraudAmount;
	
	/**
	 * 创建开始时间
	 */
	private String createBeginTime;
	/***
	 * 创建结束时间
	 */
	private String createEndTime;
	
	/***
	 * 拒付开始时间
	 */
	private String cpdBeginTime;
	/***
	 * 拒付结束时间
	 */
	private String cpdEndTime;
		/**
	 * in status
	 */
	private String statusIn;
	
	private String cpTypes;
	private String mpscpTypes;
	private String bouncedFlag;
	
	public String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(String statusIn) {
		this.statusIn = statusIn;
	}
	
	//交易汇率
	private String transRate;
	 //清算汇率
	private String settlementRate;
	private String floatValue;
	private BigDecimal bouncedRate;
	private BigDecimal bouncedAmount;
	private String merchantNo;
	private String gcFlag;
	
	public String getGcFlag() {
		return gcFlag;
	}

	public void setGcFlag(String gcFlag) {
		this.gcFlag = gcFlag;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Long getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(Long channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}


	public String getChargeBackAmount() {
		return chargeBackAmount;
	}

	public void setChargeBackAmount(String chargeBackAmount) {
		this.chargeBackAmount = chargeBackAmount;
	}

	public String getChargeBackMsg() {
		return chargeBackMsg;
	}

	public void setChargeBackMsg(String chargeBackMsg) {
		this.chargeBackMsg = chargeBackMsg;
	}

	public String getOldRefNo() {
		return oldRefNo;
	}

	public void setOldRefNo(String oldRefNo) {
		this.oldRefNo = oldRefNo;
	}

	public String getCpdDate() {
		return cpdDate;
	}

	public void setCpdDate(String cpdDate) {
		this.cpdDate = cpdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getCpType() {
		return cpType;
	}

	public void setCpType(Integer cpType) {
		this.cpType = cpType;
	}

	public String getCardHolderEmail() {
		return cardHolderEmail;
	}

	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAuditOperator() {
		return auditOperator;
	}

	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}


	public String getCpTypeDesc() {

		if (null != cpType) {
			if (cpType == 1) {
				return "内部调查单";
			} else if (cpType == 2) {
				return "银行调查单";
			} else if (cpType == 3) {
				return "拒付订单";
			}
		}

		return cpTypeDesc;
	}

	public void setCpTypeDesc(String cpTypeDesc) {
		this.cpTypeDesc = cpTypeDesc;
	}


	public String getSettlementAmountDesc() {
		if (null != settlementAmount) {
			return new BigDecimal(settlementAmount).divide(
					new BigDecimal("1000")).toString();
		} else {
			return settlementAmountDesc;
		}
	}

	public void setSettlementAmountDesc(String settlementAmountDesc) {
		this.settlementAmountDesc = settlementAmountDesc;
	}

	public String getCreateDateDesc() {
		if (null != createDate) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(createDate);
		} else {
			return createDateDesc;
		}
	}

	public void setCreateDateDesc(String createDateDesc) {
		this.createDateDesc = createDateDesc;
	}

	public String getTradeDateDesc() {
		if (null != tradeDate) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(tradeDate);
		} else {
			return tradeDateDesc;
		}
	}

	public void setTradeDateDesc(String tradeDateDesc) {
		this.tradeDateDesc = tradeDateDesc;
	}

	public String getChargeBackMsg1() {
		return chargeBackMsg1;
	}

	public void setChargeBackMsg1(String chargeBackMsg1) {
		this.chargeBackMsg1 = chargeBackMsg1;
	}

	public Integer getCpFlg() {
		return cpFlg;
	}

	public void setCpFlg(Integer cpFlg) {
		this.cpFlg = cpFlg;
	}

	public String getCpCurrencyCode() {
		return cpCurrencyCode;
	}

	public void setCpCurrencyCode(String cpCurrencyCode) {
		this.cpCurrencyCode = cpCurrencyCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getVisableCode() {
		return visableCode;
	}

	public void setVisableCode(String visableCode) {
		this.visableCode = visableCode;
	}

	public String getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(String amountStatus) {
		this.amountStatus = amountStatus;
	}

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(BigDecimal assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLatestAnswerDate() {
		return latestAnswerDate;
	}

	public void setLatestAnswerDate(Date latestAnswerDate) {
		this.latestAnswerDate = latestAnswerDate;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}


	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}

	public void setTranCurrencyCode(String tranCurrencyCode) {
		this.tranCurrencyCode = tranCurrencyCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(String floatValue) {
		this.floatValue = floatValue;
	}

	public BigDecimal getBouncedRate() {
		return bouncedRate;
	}

	public void setBouncedRate(BigDecimal bouncedRate) {
		this.bouncedRate = bouncedRate;
	}

	public BigDecimal getBouncedAmount() {
		return bouncedAmount;
	}

	public void setBouncedAmount(BigDecimal bouncedAmount) {
		this.bouncedAmount = bouncedAmount;
	}

	public void setTransRate(String transRate) {
		this.transRate = transRate;
	}

	public String getTransRate() {
		return transRate;
	}

	public Integer getAccountingFlg() {
		return accountingFlg;
	}

	public void setAccountingFlg(Integer accountingFlg) {
		this.accountingFlg = accountingFlg;
	}

	public String getCpTypes() {
		return cpTypes;
	}

	public void setCpTypes(String cpTypes) {
		this.cpTypes = cpTypes;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	public String getTradeBeginTime() {
		return tradeBeginTime;
	}

	public void setTradeBeginTime(String tradeBeginTime) {
		this.tradeBeginTime = tradeBeginTime;
	}

	public String getCreateBeginTime() {
		return createBeginTime;
	}

	public void setCreateBeginTime(String createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getCpdBeginTime() {
		return cpdBeginTime;
	}

	public void setCpdBeginTime(String cpdBeginTime) {
		this.cpdBeginTime = cpdBeginTime;
	}

	public String getCpdEndTime() {
		return cpdEndTime;
	}

	public void setCpdEndTime(String cpdEndTime) {
		this.cpdEndTime = cpdEndTime;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public BigDecimal getBouncedCount() {
		return bouncedCount;
	}

	public void setBouncedCount(BigDecimal bouncedCount) {
		this.bouncedCount = bouncedCount;
	}

	public BigDecimal getFraudAmount() {
		return fraudAmount;
	}

	public void setFraudAmount(BigDecimal fraudAmount) {
		this.fraudAmount = fraudAmount;
	}

	public String getMpscpTypes() {
		return mpscpTypes;
	}

	public void setMpscpTypes(String mpscpTypes) {
		this.mpscpTypes = mpscpTypes;
	}

	public String getBouncedFlag() {
		return bouncedFlag;
	}

	public void setBouncedFlag(String bouncedFlag) {
		this.bouncedFlag = bouncedFlag;
	}

	@Override
	public String toString() {
		return "BouncedOrderVO [orgCode=" + orgCode + ", orderId=" + orderId + ", refNo=" + refNo
				+ ", channelOrderId=" + channelOrderId + ", tradeOrderNo=" + tradeOrderNo
				+ ", tradeDate=" + tradeDate + ", tradeDateDesc=" + tradeDateDesc
				+ ", tradeAmount=" + tradeAmount + ", payAmount=" + payAmount + ", authorisation="
				+ authorisation + ", chargeBackAmount=" + chargeBackAmount + ", chargeBackMsg="
				+ chargeBackMsg + ", oldRefNo=" + oldRefNo + ", cpdDate=" + cpdDate + ", status="
				+ status + ", statusDesc=" + statusDesc + ", createDate=" + createDate
				+ ", createDateDesc=" + createDateDesc + ", cardNo=" + cardNo + ", operator="
				+ operator + ", cpType=" + cpType + ", cpTypeDesc=" + cpTypeDesc
				+ ", cardHolderEmail=" + cardHolderEmail + ", merchantCode=" + merchantCode
				+ ", memberCode=" + memberCode + ", merchantName=" + merchantName + ", ip=" + ip
				+ ", currencyCode=" + currencyCode + ", settlementAmount=" + settlementAmount
				+ ", settlementAmountDesc=" + settlementAmountDesc + ", settlementCurrencyCode="
				+ settlementCurrencyCode + ", auditOperator=" + auditOperator + ", auditMsg="
				+ auditMsg + ", chargeBackMsg1=" + chargeBackMsg1 + ", cpFlg=" + cpFlg
				+ ", cpCurrencyCode=" + cpCurrencyCode + ", reasonCode=" + reasonCode
				+ ", visableCode=" + visableCode + ", amountStatus=" + amountStatus
				+ ", baseAmount=" + baseAmount + ", assureAmount=" + assureAmount + ", remark="
				+ remark + ", auditDate=" + auditDate + ", latestAnswerDate=" + latestAnswerDate
				+ ", batchNo=" + batchNo + ", beginCreateDate=" + beginCreateDate
				+ ", endCreateDate=" + endCreateDate + ", tradeEndTime=" + tradeEndTime
				+ ", tradeBeginTime=" + tradeBeginTime + ", tranCurrencyCode=" + tranCurrencyCode
				+ ", orderNo=" + orderNo + ", accountingFlg=" + accountingFlg + ", cardOrg="
				+ cardOrg + ", bouncedCount=" + bouncedCount + ", fraudAmount=" + fraudAmount
				+ ", createBeginTime=" + createBeginTime + ", createEndTime=" + createEndTime
				+ ", cpdBeginTime=" + cpdBeginTime + ", cpdEndTime=" + cpdEndTime + ", statusIn="
				+ statusIn + ", cpTypes=" + cpTypes + ", mpscpTypes=" + mpscpTypes
				+ ", bouncedFlag=" + bouncedFlag + ", transRate=" + transRate + ", settlementRate="
				+ settlementRate + ", floatValue=" + floatValue + ", bouncedRate=" + bouncedRate
				+ ", bouncedAmount=" + bouncedAmount + ", merchantNo=" + merchantNo + ", gcFlag="
				+ gcFlag + "]";
	}

	


}