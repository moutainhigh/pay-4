/**
 * 
 */
package com.pay.txncore.dto;

/**
 * @author huhb
 *
 */
public class PaymentNoticeDTO {
	
	//商户订单ID
	private String orderId;
	
	//处理结果码
	private String resultCode;
	
	//订单状态
	private int stateCode;
	
	//商户订单金额
	private Long orderAmount;
	
	//支付金额
	private Long payAmount;
	
	//结算币种实际支付金额
	private Long settleAmount;
	
	//收单时间 格式：20110413090801
	private String acquireTime;
	
	//处理完成时间格式：20110413090801
	private String completeTime;
	
	//支付订单号
	private Long orderNo;
	
	//商户ID
	private Long partnerId;
	
	//扩展字段
	private String remark;
	
	//编码方式
	private int charset;
	
	//签名类型
	private int signType;
	
	//后台通知地址
	private String noticeUrl;
	
	//通知内容
	private String noticeContent;
	
	//服务版本号
	private String serviceVersion;
	
	//充值订单流水号
	private Long depositOrderNo;
	
	private String platformID;
	
	private Long payee;
	
	private Long payer;
	
	public Long getPayee() {
		return payee;
	}

	public void setPayee(Long payee) {
		this.payee = payee;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public String getPlatformID() {
		return platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public Long getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(Long depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(String acquireTime) {
		this.acquireTime = acquireTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCharset() {
		return charset;
	}

	public void setCharset(int charset) {
		this.charset = charset;
	}

	public int getSignType() {
		return signType;
	}

	public void setSignType(int signType) {
		this.signType = signType;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public Long getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(Long settleAmount) {
		this.settleAmount = settleAmount;
	}

	@Override
	public String toString() {
		return "PaymentNoticeDTO [acquireTime=" + acquireTime + ", charset="
				+ charset + ", completeTime=" + completeTime
				+ ", depositOrderNo=" + depositOrderNo + ", noticeContent="
				+ noticeContent + ", noticeUrl=" + noticeUrl + ", orderAmount="
				+ orderAmount + ", orderId=" + orderId + ", orderNo=" + orderNo
				+ ", partnerId=" + partnerId + ", payAmount=" + payAmount
				+ ", remark=" + remark + ", resultCode=" + resultCode
				+ ", signType=" + signType + ", stateCode=" + stateCode 
				+ ", settleAmount="+ settleAmount +"]";
	}

}
