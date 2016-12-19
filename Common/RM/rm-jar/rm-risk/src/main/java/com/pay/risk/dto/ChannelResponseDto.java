/**
 * 
 */
package com.pay.risk.dto;

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

	private String responseCode;

	private String responseDesc;
	/** 渠道返回码 **/
	private String channelRespCode;

	private String merchantBillName;

	private String transRate;

	private String originalRate;
	
	 
	
	// add by DL for 动态负载 2016年5月23日22:12:45
	private String payCurrencyCode;

	public String getPayCurrencyCode() {
		return payCurrencyCode;
	}

	public void setPayCurrencyCode(String payCurrencyCode) {
		this.payCurrencyCode = payCurrencyCode;
	}

	// add end
 

	public String getTransRate() {
		return transRate;
	}

	public void setTransRate(String transRate) {
		this.transRate = transRate;
	}

	public String getOriginalRate() {
		return originalRate;
	}

	public void setOriginalRate(String originalRate) {
		this.originalRate = originalRate;
	}

	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getChannelRespCode() {
		return channelRespCode;
	}

	public void setChannelRespCode(String channelRespCode) {
		this.channelRespCode = channelRespCode;
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

}
