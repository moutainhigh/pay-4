package com.pay.channel.model;

import java.util.Date;

public class ChannelItemRCurrency {
	/**关联ID*/
	private Long relateId;
	/**支付渠道code*/
	private String channelItemCode;
	/**币种code*/
	private String currencyCode;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date updateDate;
	/**创建者*/
	private String createOperator;
	/**修改者*/
	private String updateOperator;
	/**1代表有效,0代表被删除*/
	private String status;
	public Long getRelateId() {
		return relateId;
	}
	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}
	public String getChannelItemCode() {
		return channelItemCode;
	}
	public void setChannelItemCode(String channelItemCode) {
		this.channelItemCode = channelItemCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public String getUpdateOperator() {
		return updateOperator;
	}
	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {		
		return "ChannelItemRCurrency[relateId=" +relateId + ",channelItemCode=" +   channelItemCode + 
					",currencyCode=" +   currencyCode + ",createDate=" +   createDate + ",updateDate=" +   updateDate + 
					",createOperator=" +   createOperator + ",updateOperator=" +   updateOperator  + ",status=" +   status + "]";
	}
}
