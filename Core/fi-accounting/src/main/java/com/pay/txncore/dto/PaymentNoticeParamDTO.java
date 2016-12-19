/**
 * 
 */
package com.pay.txncore.dto;

/**
 * 用于生成网关签名字符串已经加签msg的paramDTO
 * 所有字段均为字符串类型
 * 如果为空的都统一将null转换成""
 * @author huhb
 *
 */
public class PaymentNoticeParamDTO {

	//商户订单ID
	private String orderID;
	
	//处理结果码
	private String resultCode;
	
	//订单状态
	private String stateCode;
	
	//商户订单金额
	private String orderAmount;
	
	//支付金额
	private String payAmount;
	
	//收单时间 格式：20110413090801
	private String acquiringTime;
	
	//处理完成时间格式：20110413090801
	private String completeTime;
	
	//支付订单号
	private String orderNo;
	
	//商户ID
	private String partnerID;
	
	//扩展字段
	private String remark;
	
	//编码方式
	private String charset;
	
	//签名类型
	private String signType;
	
	//后台通知地址
	private String noticeUrl;
	
	//通知内容
	private String noticeContent;
	
	//服务版本号
	private String serviceVersion;
	
	//充值订单流水号
	private String depositOrderNo;
	
	private String platformID;
	
	private String payee;
	
	private String payer;
	
	private String payeeMarked;
	
	private String payerMarked;

	public String getPayeeMarked() {
		return payeeMarked;
	}

	public void setPayeeMarked(String payeeMarked) {
		this.payeeMarked = payeeMarked;
	}

	public String getPayerMarked() {
		return payerMarked;
	}

	public void setPayerMarked(String payerMarked) {
		this.payerMarked = payerMarked;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public String getPlatformID() {
		return platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	
}
