package com.pay.rm.orderfilter.dto;

import java.util.Date;


/**
 * 订单过滤对象
 * @author peiyu.yang
 * @since 2016年5月19日10:51:17
 */
public class OrderFilterRuleDTO {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 会员号
	 */
	private String memberCode;
	/**
	 * 起始日期
	 */
	private Date startDate;
	/**
	 * 截止日期
	 */
	private Date endDate;
	/**
	 * IP所在国家
	 */
	private String ipCountryCode;
	/**
	 * 卡本币
	 */
	private String cardCurrencyCode;
	/**
	 * 卡类型
	 * 例如：礼品卡啊，什么的
	 */
	private String cardType;
	/**
	 * 发卡国家
	 */
	private String cardCountryCode;
	/**
	 * 起始金额
	 */
	private Double startAmount;
	
	/**
	 * 订单金额
	 */
	private Double orderAmount;
	
	/**
	 * 订单时间
	 */
	private Date orderDate;
	/**
	 * 截止金额
	 */
	private Double endAmount;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 操作人
	 */
	private String operator;
	
	private String startTime;
	
	private String endTime;
	
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIpCountryCode() {
		return ipCountryCode;
	}
	public void setIpCountryCode(String ipCountryCode) {
		this.ipCountryCode = ipCountryCode;
	}
	public String getCardCurrencyCode() {
		return cardCurrencyCode;
	}
	public void setCardCurrencyCode(String cardCurrencyCode) {
		this.cardCurrencyCode = cardCurrencyCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardCountryCode() {
		return cardCountryCode;
	}
	public void setCardCountryCode(String cardCountryCode) {
		this.cardCountryCode = cardCountryCode;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
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
	@Override
	public String toString() {
		return "OrderFilterDTO [id=" + id + ", memberCode=" + memberCode
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", ipCountryCode=" + ipCountryCode + ", cardCurrencyCode="
				+ cardCurrencyCode + ", cardType=" + cardType
				+ ", cardCountryCode=" + cardCountryCode + ", startAmount="
				+ startAmount + ", endAmount=" + endAmount + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", operator="
				+ operator + "]";
	}
}
