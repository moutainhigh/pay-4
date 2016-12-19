/**
 * 
 */
package com.pay.acc.rate.dto;

import java.util.Date;

/**
 * @author chaoyue
 *
 */
public class MerchantRateDto {
	private Long id;
	private Long memberCode;
	private String countryCode;
	private String organisation;
	private Integer transType;
	private String cardType;
	private String transMode;
	private Date createDate;
	private String operator;
	private Integer status;
	private String chargeRate;
	private String fixedCharge;
	private Integer feeType;
	private String currency;
	private Integer dealCode;
	private String localPayCode;//computop本地化支付方式编号，add by davis.guo at 2016-07-28
	/**添加费率分档字段**/
	
	private Long monthAmountLevel;
	private String levelCurrencyCode;
	private String fixedCurrencyCode;
	
	private Long monthAmountLevel1;
	private String level1CurrencyCode;
	private String chargeRate1;
	private String fixedCharge1;
	private String fixed1CurrencyCode;
	
	private Long monthAmountLevel2;
	private String level2CurrencyCode;
	private String chargeRate2;
	private String fixedCharge2;
	private String fixed2CurrencyCode;

	private Long monthAmountLevel3;
	private String level3CurrencyCode;
	private String chargeRate3;
	private String fixedCharge3;
	private String fixed3CurrencyCode;
	
	private String monthChargeRate;
	
	public Long getMonthAmountLevel() {
		return monthAmountLevel;
	}

	public void setMonthAmountLevel(Long monthAmountLevel) {
		this.monthAmountLevel = monthAmountLevel;
	}

	public String getLevelCurrencyCode() {
		return levelCurrencyCode;
	}

	public void setLevelCurrencyCode(String levelCurrencyCode) {
		this.levelCurrencyCode = levelCurrencyCode;
	}

	public String getFixedCurrencyCode() {
		return fixedCurrencyCode;
	}

	public void setFixedCurrencyCode(String fixedCurrencyCode) {
		this.fixedCurrencyCode = fixedCurrencyCode;
	}

	public String getMonthChargeRate() {
		return monthChargeRate;
	}

	public void setMonthChargeRate(String monthChargeRate) {
		this.monthChargeRate = monthChargeRate;
	}

	public Long getMonthAmountLevel1() {
		return monthAmountLevel1;
	}

	public void setMonthAmountLevel1(Long monthAmountLevel1) {
		this.monthAmountLevel1 = monthAmountLevel1;
	}

	public String getLevel1CurrencyCode() {
		return level1CurrencyCode;
	}

	public void setLevel1CurrencyCode(String level1CurrencyCode) {
		this.level1CurrencyCode = level1CurrencyCode;
	}

	public String getChargeRate1() {
		return chargeRate1;
	}

	public void setChargeRate1(String chargeRate1) {
		this.chargeRate1 = chargeRate1;
	}

	public String getFixedCharge1() {
		return fixedCharge1;
	}

	public void setFixedCharge1(String fixedCharge1) {
		this.fixedCharge1 = fixedCharge1;
	}

	public String getFixed1CurrencyCode() {
		return fixed1CurrencyCode;
	}

	public void setFixed1CurrencyCode(String fixed1CurrencyCode) {
		this.fixed1CurrencyCode = fixed1CurrencyCode;
	}

	public Long getMonthAmountLevel2() {
		return monthAmountLevel2;
	}

	public void setMonthAmountLevel2(Long monthAmountLevel2) {
		this.monthAmountLevel2 = monthAmountLevel2;
	}

	public String getLevel2CurrencyCode() {
		return level2CurrencyCode;
	}

	public void setLevel2CurrencyCode(String level2CurrencyCode) {
		this.level2CurrencyCode = level2CurrencyCode;
	}

	public String getChargeRate2() {
		return chargeRate2;
	}

	public void setChargeRate2(String chargeRate2) {
		this.chargeRate2 = chargeRate2;
	}

	public String getFixedCharge2() {
		return fixedCharge2;
	}

	public void setFixedCharge2(String fixedCharge2) {
		this.fixedCharge2 = fixedCharge2;
	}

	public String getFixed2CurrencyCode() {
		return fixed2CurrencyCode;
	}

	public void setFixed2CurrencyCode(String fixed2CurrencyCode) {
		this.fixed2CurrencyCode = fixed2CurrencyCode;
	}

	public Long getMonthAmountLevel3() {
		return monthAmountLevel3;
	}

	public void setMonthAmountLevel3(Long monthAmountLevel3) {
		this.monthAmountLevel3 = monthAmountLevel3;
	}

	public String getLevel3CurrencyCode() {
		return level3CurrencyCode;
	}

	public void setLevel3CurrencyCode(String level3CurrencyCode) {
		this.level3CurrencyCode = level3CurrencyCode;
	}

	public String getChargeRate3() {
		return chargeRate3;
	}

	public void setChargeRate3(String chargeRate3) {
		this.chargeRate3 = chargeRate3;
	}

	public String getFixedCharge3() {
		return fixedCharge3;
	}

	public void setFixedCharge3(String fixedCharge3) {
		this.fixedCharge3 = fixedCharge3;
	}

	public String getFixed3CurrencyCode() {
		return fixed3CurrencyCode;
	}

	public void setFixed3CurrencyCode(String fixed3CurrencyCode) {
		this.fixed3CurrencyCode = fixed3CurrencyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(String fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public String getLocalPayCode() {
		return localPayCode;
	}

	public void setLocalPayCode(String localPayCode) {
		this.localPayCode = localPayCode;
	}

	@Override
	public String toString() {
		return "MerchantRateDto [id=" + id + ", memberCode=" + memberCode
				+ ", countryCode=" + countryCode + ", organisation="
				+ organisation + ", transType=" + transType + ", cardType="
				+ cardType + ", transMode=" + transMode + ", createDate="
				+ createDate + ", operator=" + operator + ", status=" + status
				+ ", chargeRate=" + chargeRate + ", fixedCharge=" + fixedCharge
				+ ", feeType=" + feeType + ", currency=" + currency
				+ ", dealCode=" + dealCode + ", localPayCode=" + localPayCode  + "]";
	}
	
	
}
