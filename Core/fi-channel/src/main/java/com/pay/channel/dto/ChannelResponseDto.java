/**
 * 
 */
package com.pay.channel.dto;

import java.util.Map;

/**
 * @author chaoyue
 *
 */
public class ChannelResponseDto {

	// 渠道号
	private Long channelOrderNo;
	// 渠道返回
	private String channelReturnNo;

	// 支付金额
	private Long payAmount;

	// 机构号
	private String orgCode;

	private String orgMerchantCode;

	private String responseCode;

	private String responseDesc;
	
	private String authorisation;
	//参考号
	private String referenceNo;

	//是否完全扣款撤销 1代表是2代表不是
	private String completeStatus;
	private Map<String,String> dataMap;
	
	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public String getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getChannelReturnNo() {
		return channelReturnNo;
	}

	public void setChannelReturnNo(String channelReturnNo) {
		this.channelReturnNo = channelReturnNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}

	@Override
	public String toString() {
		return "ChannelResponseDto [channelOrderNo=" + channelOrderNo
				+ ", channelReturnNo=" + channelReturnNo + ", payAmount="
				+ payAmount + ", orgCode=" + orgCode + ", orgMerchantCode="
				+ orgMerchantCode + ", responseCode=" + responseCode
				+ ", responseDesc=" + responseDesc + ", authorisation="
				+ authorisation + "]";
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
}
