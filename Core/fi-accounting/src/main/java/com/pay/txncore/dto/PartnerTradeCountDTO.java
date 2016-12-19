package com.pay.txncore.dto;

import java.util.Date;

/**
 * 商户交易统计
 * 
 * @author peiyu.yang
 *
 */
public class PartnerTradeCountDTO {
	/**
	 * 会员号
	 */
	private String partnerId;
	/**
	 * 交易币种
	 */
	private String currencyCode;
	/**
	 * 交易金额
	 */
	private Long tradeAmount;
	/**
	 * 交易数量
	 */
	private Long tradeCount;
	/**
	 * 开始日期
	 */
	private Date startDate;
	/**
	 * 截止日期
	 */
	private Date endDate;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Long getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
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

	@Override
	public String toString() {
		return "PartnerTradeCountDTO [partnerId=" + partnerId
				+ ", currencyCode=" + currencyCode + ", tradeAmount="
				+ tradeAmount + ", tradeCount=" + tradeCount + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
}
