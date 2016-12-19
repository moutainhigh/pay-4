package com.pay.fi.dto;

import java.util.Date;

public class ChargeBackOrder {
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
	private String tradeDate;

	/**
	 * 交易金额
	 */
	private Long tradeAmount;

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
	 * 支付卡号
	 */
	private String cardNo;

	/**
	 * null
	 */
	private String operator;

	private Integer cpType;

	/**
	 * 持卡人邮箱
	 */
	private String cardHolderEmail;

	private String merchantCode;

	private String memberCode;

	private String merchantName;

	private String ip;

	private String auditOperator;

	private String auditMsg;

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

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
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

}