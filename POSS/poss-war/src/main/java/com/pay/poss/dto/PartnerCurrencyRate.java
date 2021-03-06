package com.pay.poss.dto;

import java.util.Date;


/**
 * 清算汇率
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class PartnerCurrencyRate {
	/**
	 *ID
	 */
	private Long id;
	
	/**
	 * 商户会员号
	 */
	private String memberCode;
	
	/**
	 * 0:针对所有商户 memberCode 没有值
	 * 1：针对单个商户 memberCode 有值
	 */
	private String rateTag;
	
	private String currencyDesc;

	/**
	 * 币种代码
	 */
	private String currency;

	/**
	 * 交易单位，例如：人民币：100元，日元：1000元等
	 */
	private Integer currencyUnit;

	/**
	 * 兑换的币种
	 */
	private String targetCurrency;

	/**
	 * 交换汇率
	 */
	private String exchangeRate;
    
	/**
	 * 逆向汇率
	 */
	private String reverseExchangeRate;

	/**
	 * 有效起始日期
	 */
	private Date effectDate;

	/**
	 * 有效截止日期
	 */
	private Date expireDate;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 0：已过期 1：正常 2：已作废 3：待审核 4：审核未通过
	 */
	private String status;
	
	private String cardOrg;
	
	private String startPoint;
	
	private String endPoint;
	
	private String leastTransAmount;
	
	private String ltaCurrencyCode;

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getLeastTransAmount() {
		return leastTransAmount;
	}

	public void setLeastTransAmount(String leastTransAmount) {
		this.leastTransAmount = leastTransAmount;
	}

	public String getLtaCurrencyCode() {
		return ltaCurrencyCode;
	}

	public void setLtaCurrencyCode(String ltaCurrencyCode) {
		this.ltaCurrencyCode = ltaCurrencyCode;
	}

	/**
	 * 操作人
	 */
	private String operator;
	
	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

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

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getRateTag() {
		return rateTag;
	}

	public void setRateTag(String rateTag) {
		this.rateTag = rateTag;
	}

	@Override
	public String toString() {
		return "SettlementRate [id=" + id + ", memberCode=" + memberCode
				+ ", rateTag=" + rateTag + ", currency=" + currency
				+ ", currencyUnit=" + currencyUnit + ", targetCurrency="
				+ targetCurrency + ", exchangeRate=" + exchangeRate
				+ ", reverseExchangeRate=" + reverseExchangeRate
				+ ", effectDate=" + effectDate + ", expireDate=" + expireDate
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", status=" + status + ", operator=" + operator + "]";
	}
}