package com.pay.txncore.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拒付订单表 File: BouncedResultDTO.java Description: Copyright 2016-2030 IPAYLINKS
 * Corporation. All rights reserved. Date Author Changes 2016年5月10日 mmzhang
 * Create
 *
 */
public class BouncedOrderVO {

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

	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;

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

	/**
	 * null
	 */
	private Date createDate;

	/**
	 * 交易授权号
	 */
	private String authorisation;

	/**
	 * 支付卡号
	 */
	private String cardNo;

	/**
	 * null
	 */
	private String operator;

	private Integer cpType;
	
	private String cpTypes;
	private String mpscpTypes;
	private String bouncedFlag;
	
	/**
	 * 持卡人邮箱
	 */
	private String cardHolderEmail;

	private String merchantCode;

	private String memberCode;
	
	private String merchantNo;

	private String merchantName;

	private String ip;

	private String currencyCode;

	private Long settlementAmount;

	private String settlementCurrencyCode;

	private String auditOperator;

	private String auditMsg;

	private Date auditDate;
	
	// 1-全额拒付，2-部分拒付
	private String amountType;
	// 拒付记账：0-未记账，1-已记账
	private Integer accountingFlg;
	// 1-全额拒付，2-部分拒付
	private Integer cpFlg;
	// 罚款是否已记账
	private Integer fineFlg;
	// 拒付扣款
	private Long refundAmount;
	// 拒付罚款
	private Long fineAmount;

	private String chargeBackMsg1;

	private String cpCurrencyCode;

	// 退款时转换汇率
	private String transRate;
	// 拒付原因
	private String reasonCode;
	private String batchNo;
	// 显示原因
	private String visableCode;
	// 资金状态
	private String amountStatus;
	// 基本户扣款
	private BigDecimal baseAmount;
	// 保证金扣款
	private BigDecimal assureAmount;
	private BigDecimal payAmount;
	// 备注
	private String remark;
	private BigDecimal overRefundAmount; // 已退金额
	private BigDecimal doingRefundAmount; // 退款中金额
	private BigDecimal overBouncedAmount; // 已拒付金额
	private BigDecimal canBouncedAmount; // 可拒付金额
	private BigDecimal doingBouncedAmount; // 拒付中金额
	
	private String settlementRate;
	private String floatValue;
	private BigDecimal bouncedRate;
	private BigDecimal bouncedAmount;
	private String[] amountTypes;
	/**
	 * 交易开始时间
	 */
	private String tradeBeginTime;
	/***
	 * 交易结束时间
	 */
	private String tradeEndTime; 
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
	 * 最晚的回复时间
	 */
	private Date latestAnswerDate;
	/**
	 * 商户订单号
	 */
	private String orderNo;
	private String tranCurrencyCode;
	
	private String orderIds;
	
	/**
	 * statu使用in查询标志
	 */
	private String statusIn ;
	
	/**
	 * 申诉资料保存路径
	 */
	private String appealDbRelativePath ;
	private String cardOrg ;
	private String gcFlag;

	
	public String getGcFlag() {
		return gcFlag;
	}

	public void setGcFlag(String gcFlag) {
		this.gcFlag = gcFlag;
	}

	public String getAppealDbRelativePath() {
		return appealDbRelativePath;
	}

	public void setAppealDbRelativePath(String appealDbRelativePath) {
		this.appealDbRelativePath = appealDbRelativePath;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getLatestAnswerDate() {
		return latestAnswerDate;
	}

	public void setLatestAnswerDate(Date latestAnswerDate) {
		this.latestAnswerDate = latestAnswerDate;
	}

	public String getTradeBeginTime() {
		return tradeBeginTime;
	}

	public void setTradeBeginTime(String tradeBeginTime) {
		this.tradeBeginTime = tradeBeginTime;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
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

	public String getChargeBackAmount() {
		return chargeBackAmount;
	}

	public void setChargeBackAmount(String chargeBackAmount) {
		this.chargeBackAmount = chargeBackAmount;
	}

	public Integer getCpType() {
		return cpType;
	}

	public void setCpType(Integer cpType) {
		this.cpType = cpType;
	}

	public String getCpTypes() {
		return cpTypes;
	}

	public void setCpTypes(String cpTypes) {
		this.cpTypes = cpTypes;
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

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Integer getAccountingFlg() {
		return accountingFlg;
	}

	public void setAccountingFlg(Integer accountingFlg) {
		this.accountingFlg = accountingFlg;
	}

	public Integer getFineFlg() {
		return fineFlg;
	}

	public void setFineFlg(Integer fineFlg) {
		this.fineFlg = fineFlg;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Long fineAmount) {
		this.fineAmount = fineAmount;
	}

	public Integer getCpFlg() {
		return cpFlg;
	}

	public void setCpFlg(Integer cpFlg) {
		this.cpFlg = cpFlg;
	}

	public String getChargeBackMsg1() {
		return chargeBackMsg1;
	}

	public void setChargeBackMsg1(String chargeBackMsg1) {
		this.chargeBackMsg1 = chargeBackMsg1;
	}

	public String getCpCurrencyCode() {
		return cpCurrencyCode;
	}

	public void setCpCurrencyCode(String cpCurrencyCode) {
		this.cpCurrencyCode = cpCurrencyCode;
	}

	public String getTransRate() {
		return transRate;
	}

	public void setTransRate(String transRate) {
		this.transRate = transRate;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}

	public void setTranCurrencyCode(String tranCurrencyCode) {
		this.tranCurrencyCode = tranCurrencyCode;
	}

	public BigDecimal getOverRefundAmount() {
		return overRefundAmount;
	}

	public void setOverRefundAmount(BigDecimal overRefundAmount) {
		this.overRefundAmount = overRefundAmount;
	}

	public BigDecimal getDoingRefundAmount() {
		return doingRefundAmount;
	}

	public void setDoingRefundAmount(BigDecimal doingRefundAmount) {
		this.doingRefundAmount = doingRefundAmount;
	}

	public BigDecimal getOverBouncedAmount() {
		return overBouncedAmount;
	}

	public void setOverBouncedAmount(BigDecimal overBouncedAmount) {
		this.overBouncedAmount = overBouncedAmount;
	}

	public BigDecimal getCanBouncedAmount() {
		return canBouncedAmount;
	}

	public void setCanBouncedAmount(BigDecimal canBouncedAmount) {
		this.canBouncedAmount = canBouncedAmount;
	}

	public BigDecimal getDoingBouncedAmount() {
		return doingBouncedAmount;
	}

	public void setDoingBouncedAmount(BigDecimal doingBouncedAmount) {
		this.doingBouncedAmount = doingBouncedAmount;
	}

	/**
	 * @return the statusIn
	 */
	public String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(String statusIn) {
		this.statusIn = statusIn;
	}

	public String getSettlementRate() {
		return settlementRate;
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

	public String[] getAmountTypes() {
		return amountTypes;
	}

	public void setAmountTypes(String[] amountTypes) {
		this.amountTypes = amountTypes;
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

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
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

	
}