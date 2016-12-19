package com.pay.fundout.channel.model.channel;

import com.pay.inf.model.BaseObject;

public class ChannelObject extends BaseObject {

	/*
	 * 渠道编号
	 */
	private String channelCode;
	
	/*
	 * 渠道ID
	 */
	private Long channelId;
	
	/*
	 * 出款银行ID
	 */
	private String bankId;
	
	/*
	 * 出款方式,1表示手工,0表示银企直连
	 */
	private String modeCode;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}
	
}
