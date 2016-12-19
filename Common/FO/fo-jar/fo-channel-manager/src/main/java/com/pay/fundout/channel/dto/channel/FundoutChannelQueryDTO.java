/**
 *  File: FundoutChannelQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-2     darv      Changes
 *  
 *
 */
package com.pay.fundout.channel.dto.channel;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class FundoutChannelQueryDTO extends BaseObject {
	private String businessCode;
	private Long channelId;
	private Long status;
	private String mark;
	private String modeCode;
	private String operator;
	private String bankId;
	private String channelName;
	private String businessName;
	private String modeName;
	private String bankName;
	private java.util.Date createDate;
	private BankChannelConfig bankChannelConfig;
	public String getCode() {
		return code;
	}

	public BankChannelConfig getBankChannelConfig() {
		return bankChannelConfig;
	}

	public void setBankChannelConfig(BankChannelConfig bankChannelConfig) {
		this.bankChannelConfig = bankChannelConfig;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private java.util.Date updateDate;
	private String code;

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}


	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}



	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getStatusStr(){
		if(this.status==1){
			return "开启";
		} else {
			return "关闭";
		}
	}
}
