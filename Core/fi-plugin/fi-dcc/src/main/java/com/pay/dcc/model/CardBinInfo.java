package com.pay.dcc.model;

/**
 * 卡bin
 * 
 * @author peiyu.yang
 *
 */
public class CardBinInfo {
	/**
	 * 卡Bin
	 */
	private String cardBin;
	/**
	 * 卡组织
	 */
	private String cardOrg;

	/**
	 * 发卡行
	 */
	private String issuer;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 卡种类
	 */
	private String cardClass;

	/**
	 * 发卡国家
	 */
	private String issuerCountry;

	/**
	 * 发卡国家二字代码
	 */
	private String countryCode2;

	/**
	 * 发卡国家二字代码
	 */
	private String countryCode3;

	/**
	 * 币种编号
	 */
	private String currencyNumber;

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardClass() {
		return cardClass;
	}

	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}

	public String getIssuerCountry() {
		return issuerCountry;
	}

	public void setIssuerCountry(String issuerCountry) {
		this.issuerCountry = issuerCountry;
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	public String getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

	@Override
	public String toString() {
		return "CardBinInfo [cardBin=" + cardBin + ", cardOrg=" + cardOrg
				+ ", issuer=" + issuer + ", cardType=" + cardType
				+ ", cardClass=" + cardClass + ", issuerCountry="
				+ issuerCountry + ", countryCode2=" + countryCode2
				+ ", countryCode3=" + countryCode3 + ", currencyNumber="
				+ currencyNumber + "]";
	}
}
