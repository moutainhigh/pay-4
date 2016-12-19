package com.pay.fi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the KF_PAY_TRADE_DETAIL database table.
 * 
 */
public class KfPayTradeDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String detailNo;

	private String batchNo;

	private String cheques;

	private Date completeDate;

	private Date createDate;

	private String drawee;

	private String failDesc;

	private String operator;

	private String orderId;

	private String outStatus;

	private long partnerId;

	private long payAmount;

	private String rate;

	private String remark;

	private long remitAmount;

	private String remitCurrencyCode;

	private long remitExpense;

	private String type;
	
	private long smallServiceFee;
	
	private long fee;

	private long  orderAmount;
	
	
	public long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public long getSmallServiceFee() {
		return smallServiceFee;
	}

	public void setSmallServiceFee(long smallServiceFee) {
		this.smallServiceFee = smallServiceFee;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public long getRemitExpense() {
		return remitExpense;
	}

	public void setRemitExpense(long remitExpense) {
		this.remitExpense = remitExpense;
	}

	public KfPayTradeDetail() {
	}

	public String getDetailNo() {
		return detailNo;
	}

	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCheques() {
		return cheques;
	}

	public void setCheques(String cheques) {
		this.cheques = cheques;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDrawee() {
		return drawee;
	}

	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	public String getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}

	public long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(long remitAmount) {
		this.remitAmount = remitAmount;
	}

	public String getRemitCurrencyCode() {
		return remitCurrencyCode;
	}

	public void setRemitCurrencyCode(String remitCurrencyCode) {
		this.remitCurrencyCode = remitCurrencyCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "KfPayTradeDetail [detailNo=" + detailNo + ", batchNo=" + batchNo + ", cheques=" + cheques
				+ ", completeDate=" + completeDate + ", createDate=" + createDate + ", drawee=" + drawee + ", failDesc="
				+ failDesc + ", operator=" + operator + ", orderId=" + orderId + ", outStatus=" + outStatus
				+ ", partnerId=" + partnerId + ", payAmount=" + payAmount + ", rate=" + rate + ", remark=" + remark
				+ ", remitAmount=" + remitAmount + ", remitCurrencyCode=" + remitCurrencyCode + ", remitExpense="
				+ remitExpense + ", type=" + type + ", smallServiceFee=" + smallServiceFee + ", fee=" + fee + "]";
	}

}