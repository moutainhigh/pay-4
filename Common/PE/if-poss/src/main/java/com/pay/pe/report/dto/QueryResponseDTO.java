package com.pay.pe.report.dto;

import java.io.Serializable;

public class QueryResponseDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 交易号
	 */
	private String sequenceId;
	
	/**
	 * 批次号
	 */
	private String batchNo;
	
	/**
	 * 付款人名称
	 */
	private String payerName;
	
	/**
	 * 收款银行名称
	 */
	private String bankName;
	
	/**
	 * 银行账号
	 */
	private String bankAcct;
	
	/**
	 * 收款人名称
	 */
	private String payeeName;
	
	/**
	 * 交易金额
	 */
	private String orderAmount;
	
	/**
	 * 交易时间
	 */
	private String orderDate;
	
	
	/**
	 * 原始订单号
	 */
	private String orderSeqId;
	
	/**
	 * 费用
	 */
	private String fee;
	
	/**
	 * 银行网关名称
	 * @return
	 */
	private String gatewayName;
	
	/**
	 * 商户号
	 */
	private String partnerId;
	
	/**
	 * 商户名称
	 */
	private String partnerName;
	
	/**
	 * 银行订单号
	 */
	private String serialNo;
	
	/**
	 * 订单开始时间
	 */
	private String createDate;
	
	/**
	 * 结束时间
	 */
	private String updateDate;
	
	/**
	 * 订单状态描叙
	 */
	private String statusDes;
	
	/**
	 * 商家订单号
	 */
	private String orderId;
	
	/**
	 * 退款金额
	 */
	private String refundAmount;
	
	/**
	 * 借贷标记
	 */
	private String debitFlag;
	
	public String getDebitFlag() {
		return debitFlag;
	}

	public void setDebitFlag(String debitFlag) {
		this.debitFlag = debitFlag;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderSeqId() {
		return orderSeqId;
	}

	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatusDes() {
		return statusDes;
	}

	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
}
