package com.pay.txncore.dto;

import java.util.Date;


/**
 * 渠道币种映射关系
 * @author peiyu
 *
 */
public class ChannelCurrencyDTO {
    //主键
	private long id;

	private long partnerId;
	//渠道编号
	private String orgCode;
	//产品编号
	private String prdtCode;
	//支付类型
	private String payType;
	//交易币种
	private String currencyCode;
	//卡本币
	private String cardCurrencyCode;
	//送渠道币种
	private String channelCurrencyCode;
	//操作人
	private String operator;
	//创建日期
	private Date createDate;
	//更新日期
	private Date updateDate;

	public long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getCardCurrencyCode() {
		return cardCurrencyCode;
	}
	public void setCardCurrencyCode(String cardCurrencyCode) {
		this.cardCurrencyCode = cardCurrencyCode;
	}
	public String getChannelCurrencyCode() {
		return channelCurrencyCode;
	}
	public void setChannelCurrencyCode(String channelCurrencyCode) {
		this.channelCurrencyCode = channelCurrencyCode;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Override
	public String toString() {
		return "ChannelCurrencyDTO [id=" + id + ", orgCode=" + orgCode
				+ ", prdtCode=" + prdtCode + ", payType=" + payType
				+ ", currencyCode=" + currencyCode + ", cardCurrencyCode="
				+ cardCurrencyCode + ", channelCurrencyCode="
				+ channelCurrencyCode + ", operator=" + operator
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}
}
