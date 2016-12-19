package com.pay.txncore.dto.refund;

import java.util.Date;

/**
 * 退款订单请求，从fi-ordercenter移植过来的 
 * @author davis.guo at 2016-08-17
 * 
 */
public class RefundOrderDTO {

	/**
	 * 支付平台产生的退款订单号
	 */
	private Long refundOrderNo;

	/**
	 * 退款目的地
	 */
	private String refundObjType;

	/**
	 * 商户请求序列
	 */
	private String requestSerialId;

	/**
	 * 网关订单编号
	 */
	private Long tradeOrderNo;

	/**
	 * 退款订单状态 默认为-1，作为不修改依据
	 */
	private String status;

	/**
	 * 调用充退回调时产生
	 */
	private Long depositBackNo;

	/**
	 * 前台通知地址
	 */
	private String pageUrl;

	/**
	 * 商户ID
	 */
	private String partnerId;

	/**
	 * 支付订单号
	 */
	private Long paymentOrderNo;

	/**
	 * 退款类型[全额，部分，比例]
	 */
	private String refundType;

	/**
	 * 实际退款金额
	 */
	private Long refundAmount;

	/**
	 * 按比例退款时记录比例值
	 */
	private Long refundValue;

	/**
	 * 手续费
	 */
	// private Long fee;

	/**
	 * 收款方手续费
	 */
	private Long payeeFee;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;

	/**
	 * 后台通知URL
	 */
	private String bgUrl;

	/**
	 * 商户订单ID
	 */
	private String partnerRefundOrderId;

	/**
	 * 商户退款时间
	 */
	private java.util.Date partnerRefundTime;

	/**
	 * 处理过程错误码，仅出现在创建完退款订单后续操作发生异常
	 */
	private String errorCode;

	/**
	 * 商户请求时产生
	 */
	private String remark;

	private java.util.Date createDate;

	private java.util.Date updateDate;

	private Date complateDate;

	private String beginTime;
	private String endTime;
	
	private String serialNo;
	private String orderId;
	//完成时间
	private String compEndTime;
	//完成时间
	private String compStartTime;
	
	private String  rate;
	
	private String currencyCode;
	
	private String orgCode;
	
	private Integer reconciliationFlg;		//note by sch 2016-06-01 
											//这里用Interger,是因为FI-Accounting里用的是Integer
	
	private String refundOrgCode;		//orgCode,这是退款订单里的字段  add by sch 2016-07-22
	private String channelReturnNo;		//渠道返回的参考号，ctv和migs 需要记录这些字段,用于比对查找退款订单号
	
	public String getRefundOrgCode(){
		return refundOrgCode;
	}
	public void setRefundOrgCode(String refundOrgCode){
		this.refundOrgCode = refundOrgCode;
	}
	
	public String getChannelReturnNo(){
		return channelReturnNo;
	}
	public void setChannelReturnNo(String channelReturnNo){
		this.channelReturnNo = channelReturnNo;
	}
	
	public Integer getReconciliationFlg(){
		return reconciliationFlg;
	}
	public void setReconciliationFlg(Integer reconciliationFlg){
		this.reconciliationFlg = reconciliationFlg;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	private Long channelOrderNo ;
	
	
	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCompEndTime() {
		return compEndTime;
	}

	public void setCompEndTime(String compEndTime) {
		this.compEndTime = compEndTime;
	}

	public String getCompStartTime() {
		return compStartTime;
	}

	public void setCompStartTime(String compStartTime) {
		this.compStartTime = compStartTime;
	}

	private String refundSource;
	
	public String getRefundSource() {
		return refundSource;
	}

	public void setRefundSource(String refundSource) {
		this.refundSource = refundSource;
	}

	//退款转换后金额
	private Long transferAmount;
	
    
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// public Long getFee() {
	// return fee;
	// }
	//
	// public void setFee(Long fee) {
	// this.fee = fee;
	// }

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundObjType() {
		return refundObjType;
	}

	public void setRefundObjType(String refundObjType) {
		this.refundObjType = refundObjType;
	}

	public String getRequestSerialId() {
		return requestSerialId;
	}

	public void setRequestSerialId(String requestSerialId) {
		this.requestSerialId = requestSerialId;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDepositBackNo() {
		return depositBackNo;
	}

	public void setDepositBackNo(Long depositBackNo) {
		this.depositBackNo = depositBackNo;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Long getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(Long refundValue) {
		this.refundValue = refundValue;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public java.util.Date getPartnerRefundTime() {
		return partnerRefundTime;
	}

	public void setPartnerRefundTime(java.util.Date partnerRefundTime) {
		this.partnerRefundTime = partnerRefundTime;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getPartnerRefundOrderId() {
		return partnerRefundOrderId;
	}

	public void setPartnerRefundOrderId(String partnerRefundOrderId) {
		this.partnerRefundOrderId = partnerRefundOrderId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public Date getComplateDate() {
		return complateDate;
	}

	public void setComplateDate(Date complateDate) {
		this.complateDate = complateDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Long transferAmount) {
		this.transferAmount = transferAmount;
	}

	@Override
	public String toString() {
		return "RefundOrderDTO [refundOrderNo=" + refundOrderNo
				+ ", refundObjType=" + refundObjType + ", requestSerialId="
				+ requestSerialId + ", tradeOrderNo=" + tradeOrderNo
				+ ", status=" + status + ", depositBackNo=" + depositBackNo
				+ ", pageUrl=" + pageUrl + ", partnerId=" + partnerId
				+ ", paymentOrderNo=" + paymentOrderNo + ", refundType="
				+ refundType + ", refundAmount=" + refundAmount
				+ ", refundValue=" + refundValue + ", payeeFee=" + payeeFee
				+ ", payerFee=" + payerFee + ", bgUrl=" + bgUrl
				+ ", partnerRefundOrderId=" + partnerRefundOrderId
				+ ", partnerRefundTime=" + partnerRefundTime + ", errorCode="
				+ errorCode + ", remark=" + remark + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", complateDate="
				+ complateDate + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", serialNo=" + serialNo + ", orderId=" + orderId
				+ ", refundSource=" + refundSource + ", transferAmount="
				+ transferAmount + "]";
	}

}