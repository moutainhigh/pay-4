package com.pay.poss.dto;

import java.util.Date;


/**
 * 清算汇率调整
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class CurrencyRateAdjust {
	/**
	 *ID
	 */
	private Long id;
	
	/**
	 * 商户会员号
	 */
	private String memberCode;

	/**
	 * 币种代码
	 */
	private String currency;

	/**
	 * 兑换的币种
	 */
	private String targetCurrency;

	/**
	 * 调整比率
	 */
	private String adjustRate;
	
	/**
	 * 逆向调整幅度
	 */
    private String reverseAdjustRate;
    
    private String reverseAdjustTag;
	
	
	/**
	 * 调整方向
	 * 0：下调
	 * 1：上调
	 */
	private String adjustTag;

	/**
	 * 有效起始日期
	 */
	private String effectDate;

	/**
	 * 有效截止日期
	 */
	private String expireDate;

	/**
	 * 创建日期
	 */
	private String createDate;

	/**
	 * 更新日期
	 */
	private String updateDate;

	/**
	 * 0：已过期 1：正常 2：已作废 3：待审核 4：审核未通过
	 */
	private String status;
	
	private String cardOrg;
	
	private String startPoint;
	
	private String endPoint;
	
	private String leastTransAmount;
	
	private String ltaCurrencyCode;

	/**
	 * 操作人
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

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getAdjustRate() {
		return adjustRate;
	}

	public void setAdjustRate(String adjustRate) {
		this.adjustRate = adjustRate;
	}

	public String getAdjustTag() {
		return adjustTag;
	}

	public void setAdjustTag(String adjustTag) {
		this.adjustTag = adjustTag;
	}
	
	public String getReverseAdjustRate() {
		return reverseAdjustRate;
	}

	public void setReverseAdjustRate(String reverseAdjustRate) {
		this.reverseAdjustRate = reverseAdjustRate;
	}

	public String getReverseAdjustTag() {
		return reverseAdjustTag;
	}

	public void setReverseAdjustTag(String reverseAdjustTag) {
		this.reverseAdjustTag = reverseAdjustTag;
	}

	@Override
	public String toString() {
		return "SettlementRateAdjust [id=" + id + ", memberCode=" + memberCode
				+ ", currency=" + currency
				+ ", targetCurrency=" + targetCurrency + ", adjustRate="
				+ adjustRate + ", adjustTag=" + adjustTag + ", effectDate="
				+ effectDate + ", expireDate=" + expireDate + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", status="
				+ status + ", operator=" + operator + "]";
	}
}