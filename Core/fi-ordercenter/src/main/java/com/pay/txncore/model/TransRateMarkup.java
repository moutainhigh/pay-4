package com.pay.txncore.model;

import java.util.Date;


/**
 * 交易汇率markup
 * @author peiyu.yang
 * @since 2016年5月9日17:40:37
 */
public class TransRateMarkup {
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
	 * 交换汇率
	 */
	private String markup;

	/**
	 * 起始时间点
	 */
	private Integer startPoint;
	
	/**
	 * 截止时间点
	 */
	private Integer endPoint;
	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 0：已过期 1：正常 
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
	 * 最低交易金额
	 */
	private Double startAmount;
	
	/**
	 * 最低交易金额
	 */
	private Double endAmount;
	
	/**
	 * 最低交易金额币种
	 */
	private String ltaCurrencyCode;
	
	/**
	 * 卡所属国
	 */
	private String cardCountry;
	
	/**
	 * 卡本币
	 */
	private String cardCurrencyCode;
	
	private String startTime;
	
	private String endTime;
	
	/**
	 * 优先级
	 */
	private Integer priority;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getLtaCurrencyCode() {
		return ltaCurrencyCode;
	}
	public void setLtaCurrencyCode(String ltaCurrencyCode) {
		this.ltaCurrencyCode = ltaCurrencyCode;
	}
	public String getCardOrg() {
		return cardOrg;
	}
	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}
	public Double getLeastTransAmount() {
		return leastTransAmount;
	}
	public void setLeastTransAmount(Double leastTransAmount) {
		this.leastTransAmount = leastTransAmount;
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
	public String getMarkup() {
		return markup;
	}
	public void setMarkup(String markup) {
		this.markup = markup;
	}
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}
	public String getCardCurrencyCode() {
		return cardCurrencyCode;
	}
	public void setCardCurrencyCode(String cardCurrencyCode) {
		this.cardCurrencyCode = cardCurrencyCode;
	}
	public Double getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(Double startAmount) {
		this.startAmount = startAmount;
	}

	public Double getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}

	@Override
	public String toString() {
		return "TransRateMarkup [id=" + id + ", memberCode=" + memberCode
				+ ", currency=" + currency + ", targetCurrency="
				+ targetCurrency + ", markup=" + markup + ", startPoint="
				+ startPoint + ", endPoint=" + endPoint + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", status="
				+ status + ", operator=" + operator + ", cardOrg=" + cardOrg
				+ ", leastTransAmount=" + leastTransAmount
				+ ", ltaCurrencyCode=" + ltaCurrencyCode + ", cardCountry="
				+ cardCountry + ", cardCurrencyCode=" + cardCurrencyCode
				+ ", priority=" + priority + "]";
	}
}