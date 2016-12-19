package com.pay.txncore.model;

import java.util.Date;

public class ChargeBackOrder {

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

	/**
	 * 持卡人邮箱
	 */
	private String cardHolderEmail;

	private String merchantCode;

	private String memberCode;

	private String merchantName;

	private String ip;

	private String currencyCode;

	private Long settlementAmount;

	private String settlementCurrencyCode;

	private String auditOperator;

	private String auditMsg;

	private Date auditDate;

	// 资金状态
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
	/**
	 * 申诉资料保存路径
	 */
	private String appealDbRelativePath ;

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

	public void setAppealDbRelativePath(String appealDbRelativePath) {
		this.appealDbRelativePath = appealDbRelativePath;
	}

	/**
	 * @return the appealDbRelativePath
	 */
	public String getAppealDbRelativePath() {
		return appealDbRelativePath;
	}
	

}