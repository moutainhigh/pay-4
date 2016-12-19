package com.pay.poss.controller.fi.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChargeBackOrder {

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
	private Long tradeAmount;

	private String tradeAmountDesc;

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

	private String currencyCode;

	private Long settlementAmount;

	private String settlementAmountDesc;

	private String settlementCurrencyCode;

	private String auditOperator;

	private String auditMsg;
	
	//1-全额拒付，2-部分拒付
	private String amountType;

	private String chargeBackMsg1;

	// 1-全额拒付，2-部分拒付
	private Integer cpFlg;

	private String cpCurrencyCode;
	private Date auditDate;
	private Integer accountingFlg;

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

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
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

	public String getTradeAmountDesc() {

		if (null != tradeAmount) {
			return new BigDecimal(tradeAmount).divide(new BigDecimal("1000"))
					.toString();
		} else {
			return tradeAmountDesc;
		}

	}

	public void setTradeAmountDesc(String tradeAmountDesc) {
		this.tradeAmountDesc = tradeAmountDesc;
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

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getAccountingFlg() {
		return accountingFlg;
	}

	public void setAccountingFlg(Integer accountingFlg) {
		this.accountingFlg = accountingFlg;
	}

}