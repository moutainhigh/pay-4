package com.pay.poss.dto;

public class CurrencyExchangeRate {

	/**
	 * null
	 */
	private Long id;

	private String partnerId;

	private String orgCode;
	
	private String status;

	/**
	 * 币种代码
	 */
	private String currency;

	private String currencyDesc;

	/**
	 * 交易单位，例如：人民币：100元，日元：1000元等
	 */
	private Integer currencyUnit;

	/**
	 * 兑换的币种
	 */
	private String targetCurrency;

	/**
	 * null
	 */
	private String exchangeRate;

	private String reverseExchangeRate;

	/**
	 * null
	 */
	private String effectDate;

	/**
	 * null
	 */
	private String expireDate;

	/**
	 * null
	 */
	private String operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(Integer currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReverseExchangeRate() {
		return reverseExchangeRate;
	}

	public void setReverseExchangeRate(String reverseExchangeRate) {
		this.reverseExchangeRate = reverseExchangeRate;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CurrencyExchangeRate [id=" + id + ", partnerId=" + partnerId
				+ ", orgCode=" + orgCode + ", status=" + status + ", currency="
				+ currency + ", currencyDesc=" + currencyDesc
				+ ", currencyUnit=" + currencyUnit + ", targetCurrency="
				+ targetCurrency + ", exchangeRate=" + exchangeRate
				+ ", reverseExchangeRate=" + reverseExchangeRate
				+ ", effectDate=" + effectDate + ", expireDate=" + expireDate
				+ ", operator=" + operator + "]";
	}
	
	

}