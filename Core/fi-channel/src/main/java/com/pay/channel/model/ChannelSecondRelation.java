/**
 * 
 */
package com.pay.channel.model;

import java.util.Date;

/**
 * @author chaoyue
 *
 */
public class ChannelSecondRelation {

	private Long id;

	private Long memberCode;

	private String orgCode;

	private String orgMerchantCode;

	private String operator;

	private Date createDate;

	private String transType;

	private String currencyCode;

	private String mcc;
	
	private String paymentChannelItemId;
	
	private String merchantBillName;
	
	private String cardOrg;

	private Long channelConfigId;

	public Long getChannelConfigId() {
		return channelConfigId;
	}

	public void setChannelConfigId(Long channelConfigId) {
		this.channelConfigId = channelConfigId;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getPaymentChannelItemId() {
		return paymentChannelItemId;
	}

	public void setPaymentChannelItemId(String paymentChannelItemId) {
		this.paymentChannelItemId = paymentChannelItemId;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	@Override
	public String toString() {
		return "ChannelSecondRelation [id=" + id + ", memberCode=" + memberCode
				+ ", orgCode=" + orgCode + ", orgMerchantCode="
				+ orgMerchantCode + ", operator=" + operator + ", createDate="
				+ createDate + ", transType=" + transType + ", currencyCode="
				+ currencyCode + ", mcc=" + mcc + ", paymentChannelItemId="
				+ paymentChannelItemId + ", merchantBillName="
				+ merchantBillName + "]";
	}
}