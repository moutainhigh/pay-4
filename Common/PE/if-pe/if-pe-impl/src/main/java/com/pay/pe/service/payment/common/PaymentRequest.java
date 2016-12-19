package com.pay.pe.service.payment.common;

import java.io.Serializable;

import com.pay.util.MfDateTime;
import com.pay.util.Money;

/**
 * 
 *
 */
// extends PaymentRequestInOrder
public final class PaymentRequest implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4939804852115279431L;
	/**
	 * 订单号.
	 */
	private String orderId;

	/**
	 * 付款方membercode.
	 */
	private String payer;

	/**
	 * 收款方membercode.
	 */
	private String payee;

	/**
	 * 付款方机构代码. 例如，网关付款，付款方为银行的机构号
	 */
	private String payerOrgCode;

	/**
	 * 付款方机构类型代码. 例如，网关付款，付款方机构号类型为银行
	 */
	private Integer payerOrgTypeCode;

	/**
	 * 收款方机构代码. 例如，银行提现，收款方为银行的机构号
	 */
	private String payeeOrgCode;

	/**
	 * 收款方机构类型代码. 例如，银行提现，收款方为银行的机构类型代码
	 */
	private Integer payeeOrgTypeCode;

	/**
	 * 付款方Member Account Code. 例如，帐号付款，付款方为会员帐号代码，此时payer的数据为会员标识
	 */
	private String payerMemberAccCode;

	/**
	 * 收款方Member Account Code. 例如，帐号付款，收款方为会员帐号代码，此时payee的数据为会员标识
	 */
	private String payeeMemberAccCode;

	/**
	 * 付款方Full Member Account Code 包含科目
	 */
	private String payerFullMemberAcctCode;

	/**
	 * 收款方Full Member Account Code 包含科目
	 */
	private String payeeFullMemberAcctCode;
	
	private String bankCode;

	/**
	 * 支付金额.
	 */
	private Money amount;

	/**
	 * 支付服务包代码.
	 */
	private Integer paymentServicePkgCode;

	/**
	 * 付款方帐号类型.可以为空.
	 */
	private Integer payerAccountType;

	/**
	 * 收款方帐号类型.可以为空.
	 */
	private Integer payeeAccountType;

	/**
	 * 收款方的服务等级.可以为空.
	 */
	private Integer payeeServiceLevel;

	/**
	 * 付款方的服务等级.可以为空.
	 */
	private Integer payerServiceLevel;

	/**
	 * 终端类型.
	 */
	private Integer terminalType;

	/**
	 * 扩展代码.
	 */
	private String reservedCode;

	/**
	 * 支付发生的时间.
	 */
	private MfDateTime date;

	/**
	 * 付款方币种缩写
	 */
	private String payerCurrencyCode;

	/**
	 * 收款方币种缩写
	 */
	private String payeeCurrencyCode;

	/**
	 * 付款方币种号码
	 */
	private String payerCurrencyNum;

	/**
	 * 收款方币种号码
	 */
	private String payeeCurrencyNum;

	/**
	 * 汇率
	 */
	private Long exchangeRate;

	/**
	 * 交易发生的时间
	 */
	private MfDateTime dealBeginDate;

	/**
	 * 收款方的费用.
	 */
	private Long payeeFee;

	/**
	 * 付款方的费用.
	 */
	private Long payerFee;

	/**
	 * 价格策略CODE
	 */
	private Long priceStrategyCode;

	/* 是否已经计收款方费 */
	private boolean hasCaculatedPayeePrice = Boolean.FALSE;
	/* 是否已经计付款方费 */
	private boolean hasCaculatedPayerPrice = Boolean.FALSE;

	public boolean isHasCaculatedPayeePrice() {
		return hasCaculatedPayeePrice;
	}

	public void setHasCaculatedPayeePrice(boolean hasCaculatedPayeePrice) {
		this.hasCaculatedPayeePrice = hasCaculatedPayeePrice;
	}

	public boolean isHasCaculatedPayerPrice() {
		return hasCaculatedPayerPrice;
	}

	public void setHasCaculatedPayerPrice(boolean hasCaculatedPayerPrice) {
		this.hasCaculatedPayerPrice = hasCaculatedPayerPrice;
	}

	public Long getPriceStrategyCode() {
		return priceStrategyCode;
	}

	public void setPriceStrategyCode(Long priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}

	public MfDateTime getDealBeginDate() {
		return dealBeginDate;
	}

	public void setDealBeginDate(MfDateTime dealBeginDate) {
		this.dealBeginDate = dealBeginDate;
	}

	public String getPayerCurrencyNum() {
		return payerCurrencyNum;
	}

	public void setPayerCurrencyNum(String payerCurrencyNum) {
		this.payerCurrencyNum = payerCurrencyNum;
	}

	public String getPayeeCurrencyNum() {
		return payeeCurrencyNum;
	}

	public void setPayeeCurrencyNum(String payeeCurrencyNum) {
		this.payeeCurrencyNum = payeeCurrencyNum;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return Returns the amount.
	 */
	public Money getAmount() {
		return amount;
	}

	/**
	 * @param money
	 *            The amount to set.
	 */
	public void setAmount(final Money money) {
		this.amount = money;
	}

	/**
	 * @return Returns the orderId.
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param id
	 *            The orderId to set.
	 */
	public void setOrderId(final String id) {
		this.orderId = id;
	}

	/**
	 * @return Returns the payee.
	 */
	public String getPayee() {
		return payee;
	}

	/**
	 * @param payeeId
	 *            The payee to set.
	 */
	public void setPayee(final String payeeId) {
		this.payee = payeeId;
	}

	/**
	 * @return Returns the payeeAccountType.
	 */
	public Integer getPayeeAccountType() {
		return payeeAccountType;
	}

	/**
	 * @param type
	 *            The payeeAccountType to set.
	 */
	public void setPayeeAccountType(final Integer type) {
		this.payeeAccountType = type;
	}

	/**
	 * @return Returns the payer.
	 */
	public String getPayer() {
		return payer;
	}

	/**
	 * @param payerId
	 *            The payer to set.
	 */
	public void setPayer(final String payerId) {
		this.payer = payerId;
	}

	/**
	 * @return Returns the payerAccountType.
	 */
	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	/**
	 * @param type
	 *            The payerAccountType to set.
	 */
	public void setPayerAccountType(final Integer type) {
		this.payerAccountType = type;
	}

	/**
	 * @return Returns the paymentServicePkgCode.
	 */
	public Integer getPaymentServicePkgCode() {
		return paymentServicePkgCode;
	}

	/**
	 * @param code
	 *            The paymentServicePkgCode to set.
	 */
	public void setPaymentServicePkgCode(final Integer code) {
		this.paymentServicePkgCode = code;
	}

	/**
	 * @return Returns the payeeOrgCode.
	 */
	public String getPayeeOrgCode() {
		return payeeOrgCode;
	}

	/**
	 * @param code
	 *            The payeeOrgCode to set.
	 */
	public void setPayeeOrgCode(final String code) {
		this.payeeOrgCode = code;
	}

	/**
	 * @return Returns the payerOrgCode.
	 */
	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	/**
	 * @param code
	 *            The payerOrgCode to set.
	 */
	public void setPayerOrgCode(final String code) {
		this.payerOrgCode = code;
	}

	/**
	 * @return Returns the payeeOrgTypeCode.
	 */
	public Integer getPayeeOrgTypeCode() {
		return payeeOrgTypeCode;
	}

	/**
	 * @param code
	 *            The payeeOrgTypeCode to set.
	 */
	public void setPayeeOrgTypeCode(final Integer code) {
		this.payeeOrgTypeCode = code;
	}

	/**
	 * @return Returns the payerOrgTypeCode.
	 */
	public Integer getPayerOrgTypeCode() {
		return payerOrgTypeCode;
	}

	/**
	 * @param code
	 *            The payerOrgTypeCode to set.
	 */
	public void setPayerOrgTypeCode(final Integer code) {
		this.payerOrgTypeCode = code;
	}

	/**
	 * @return Returns the date.
	 */
	public MfDateTime getDate() {
		return date;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate(final MfDateTime date) {
		this.date = date;
	}

	/**
	 * @return Returns the payeeMemberAccCode.
	 */
	public String getPayeeMemberAccCode() {
		return payeeMemberAccCode;
	}

	/**
	 * @param code
	 *            The payeeMemberAccCode to set.
	 */
	public void setPayeeMemberAccCode(final String code) {
		this.payeeMemberAccCode = code;
	}

	/**
	 * @return Returns the payerMemberAccCode.
	 */
	public String getPayerMemberAccCode() {
		return payerMemberAccCode;
	}

	/**
	 * @param code
	 *            The payerMemberAccCode to set.
	 */
	public void setPayerMemberAccCode(final String code) {
		this.payerMemberAccCode = code;
	}

	/**
	 * @return Returns the terminalType.
	 */
	public Integer getTerminalType() {
		return terminalType;
	}

	/**
	 * @param type
	 *            The terminalType to set.
	 */
	public void setTerminalType(final Integer type) {
		this.terminalType = type;
	}

	/**
	 * @return Returns the payeeServiceLevel.
	 */
	public Integer getPayeeServiceLevel() {
		return payeeServiceLevel;
	}

	/**
	 * @param level
	 *            The payeeServiceLevel to set.
	 */
	public void setPayeeServiceLevel(final Integer level) {
		this.payeeServiceLevel = level;
	}

	/**
	 * @return Returns the payerServiceLevel.
	 */
	public Integer getPayerServiceLevel() {
		return payerServiceLevel;
	}

	/**
	 * @param level
	 *            The payerServiceLevel to set.
	 */
	public void setPayerServiceLevel(final Integer level) {
		this.payerServiceLevel = level;
	}

	/**
	 * 得到扩展代码.
	 * 
	 * @return String
	 */
	public String getReservedCode() {
		return reservedCode;
	}

	/**
	 * 设置扩展代码.
	 * 
	 * @param code
	 */
	public void setReservedCode(final String code) {
		this.reservedCode = code;
	}

	public String getPayerFullMemberAcctCode() {
		return payerFullMemberAcctCode;
	}

	public void setPayerFullMemberAcctCode(String payerFullMemberAcctCode) {
		this.payerFullMemberAcctCode = payerFullMemberAcctCode;
	}

	public String getPayeeFullMemberAcctCode() {
		return payeeFullMemberAcctCode;
	}

	public void setPayeeFullMemberAcctCode(String payeeFullMemberAcctCode) {
		this.payeeFullMemberAcctCode = payeeFullMemberAcctCode;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("PaymentRequest [").append("orderId=").append(this.orderId)
				.append(",payee=").append(this.payee)
				.append(",payeeAccountType=").append(this.payeeAccountType)
				.append(",payeeCurrencyCode=").append(this.payeeCurrencyCode)
				.append(",payeeCurrencyNum=").append(this.payeeCurrencyNum)
				.append(",payeeFullMemberAcctCode=")
				.append(this.payeeFullMemberAcctCode)
				.append(",payeeMemberAccCode=").append(this.payeeMemberAccCode)
				.append(",payeeOrgCode=").append(this.payeeOrgCode)
				.append(",payeeOrgTypeCode=").append(this.payeeOrgTypeCode)
				.append(",payeeServiceLevel=").append(this.payeeServiceLevel)
				.append(",payeeFee=").append(this.payeeFee).append(",payer=")
				.append(this.payer).append(",payerAccountType=")
				.append(this.payerAccountType).append(",payerCurrencyCode=")
				.append(this.payerCurrencyCode).append(",payerCurrencyNum=")
				.append(this.payerCurrencyNum)
				.append(",payerFullMemberAcctCode=")
				.append(this.payerFullMemberAcctCode)
				.append(",payerMemberAccCode=").append(this.payerMemberAccCode)
				.append(",payerOrgCode=").append(this.payerOrgCode)
				.append(",payerOrgTypeCode=").append(this.payerOrgTypeCode)
				.append(",payerServiceLevel=").append(this.payerServiceLevel)
				.append(",payerFee=").append(this.payerFee).append("]");

		String s = sb.toString();
		sb = null;
		return s;
	}

}
