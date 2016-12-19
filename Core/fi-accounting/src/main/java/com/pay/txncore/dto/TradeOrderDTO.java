/**
 * 
 */
package com.pay.txncore.dto;

import java.util.Date;

/**
 * @author huhb
 *
 */
public class TradeOrderDTO {

	private Long tradeBaseNo;
	private Long tradeOrderNo;
	private Integer status;
	private Long partnerId;
	private Integer goodsCount;
	private java.util.Date updateDate;
	private Long refundAmount;
	private Long orderAmount;
	private String partnerDisplayName;
	private Integer lockFlg;
	private Integer settlementFlg;
	private java.util.Date createDate;
	private String goodsName;
	private String orderId;
	private Date completeDate;
	private String tradeType;
	private String rate;

	private String currencyCode;

	private String beginTime;
	private String endTime;
	private String respCode;
	private String respMsg;
	private String paymentWay;
	
	private String statusList;
	
	private String signLoginIdS;
	
	private String merchantBillName;
	
	//浮动之前的汇率
	private String originalRate;
	
	//汇率浮动的值
	private String floatValue;
	
	private String payLinkNo;
	
	public String getPayLinkNo() {
		return payLinkNo;
	}

	public void setPayLinkNo(String payLinkNo) {
		this.payLinkNo = payLinkNo;
	}

	public String getOriginalRate() {
		return originalRate;
	}

	public void setOriginalRate(String originalRate) {
		this.originalRate = originalRate;
	}

	public String getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(String floatValue) {
		this.floatValue = floatValue;
	}

	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getSignLoginIdS() {
		return signLoginIdS;
	}

	public void setSignLoginIdS(String signLoginIdS) {
		this.signLoginIdS = signLoginIdS;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Long getTradeBaseNo() {
		return tradeBaseNo;
	}

	public void setTradeBaseNo(Long tradeBaseNo) {
		this.tradeBaseNo = tradeBaseNo;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPartnerDisplayName() {
		return partnerDisplayName;
	}

	public void setPartnerDisplayName(String partnerDisplayName) {
		this.partnerDisplayName = partnerDisplayName;
	}

	public Integer getLockFlg() {
		return lockFlg;
	}

	public void setLockFlg(Integer lockFlg) {
		this.lockFlg = lockFlg;
	}

	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	@Override
	public String toString() {
		return "TradeOrderDTO [tradeBaseNo=" + tradeBaseNo + ", tradeOrderNo="
				+ tradeOrderNo + ", status=" + status + ", partnerId="
				+ partnerId + ", goodsCount=" + goodsCount + ", updateDate="
				+ updateDate + ", refundAmount=" + refundAmount
				+ ", orderAmount=" + orderAmount + ", partnerDisplayName="
				+ partnerDisplayName + ", lockFlg=" + lockFlg
				+ ", settlementFlg=" + settlementFlg + ", createDate="
				+ createDate + ", goodsName=" + goodsName + ", orderId="
				+ orderId + ", completeDate=" + completeDate + ", tradeType="
				+ tradeType + ", rate=" + rate + ", currencyCode="
				+ currencyCode + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", respCode=" + respCode + ", respMsg=" + respMsg
				+ ", paymentWay=" + paymentWay + ", statusList=" + statusList
				+ "]";
	}
	
}
