/**
 * 
 */
package com.pay.txncore.dto;

/**
 * @author chaoyue
 *
 */
public class ChannelPaymentResult {

	// 渠道处理结果
	private String errorCode;
	// 渠道处理结果描述
	private String errorMsg;

	// 支付渠道
	private String orgCode;

	private Long channelOrderNo;

	// 渠道返回交易号
	private String channelReturnNo;
	// 成功支付金额
	private Long payAmount;

	private String authorisation;

	//参考号 对账使用
	private String  referenceNo;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
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

	public String getChannelReturnNo() {
		return channelReturnNo;
	}

	public void setChannelReturnNo(String channelReturnNo) {
		this.channelReturnNo = channelReturnNo;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
}
