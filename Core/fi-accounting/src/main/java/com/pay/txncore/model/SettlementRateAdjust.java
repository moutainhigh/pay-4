package com.pay.txncore.model;

import java.util.Date;


/**
 * 清算汇率调整
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class SettlementRateAdjust {
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
	

	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 卡组织
	 */
	private String cardOrg;
	
	/**
	 * 最低交易金额
	 */
	private Double leastTransAmount;
	
	/**
	 * 起始时间点
	 */
	private Integer startPoint;
	
	/**
	 * 截止时间点
	 */
	private Integer endPoint;
	
	/**
	 * 最低交易金额币种
	 */
	private String ltaCurrencyCode;

	public Double getLeastTransAmount() {
		return leastTransAmount;
	}

	public void setLeastTransAmount(Double leastTransAmount) {
		this.leastTransAmount = leastTransAmount;
	}

	public String getLtaCurrencyCode() {
		return ltaCurrencyCode;
	}

	public void setLtaCurrencyCode(String ltaCurrencyCode) {
		this.ltaCurrencyCode = ltaCurrencyCode;
	}

	public Integer getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}

	public Integer getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Integer endPoint) {
		this.endPoint = endPoint;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
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

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
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