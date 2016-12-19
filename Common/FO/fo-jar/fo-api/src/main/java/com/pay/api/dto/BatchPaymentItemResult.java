/**
 *  File: PaymentItemResult.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 22, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto;

import java.util.Date;

/**
 * 
 */
public class BatchPaymentItemResult {

	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 订单流水号
	 */
	private Long paySeqNo;

	private Long amount;
	/**
	 * 处理结果
	 */
	private Integer status;
	/**
	 * 错误信息代码
	 */
	private String errorCode;

	// 成功时间
	private String successTime;
	

	/**
	 * 明细流水号
	 */
	private Long detailSeq;

	/**
	 * 请求流水号
	 */
	private Long requestSeq;

	/**
	 * 商户订单号
	 */
	private String foreignOrderId;

	/**
	 * 收款方名称
	 */
	private String payeeName;

	/**
	 * 收款方银行账号
	 */
	private String payeeBankAcctCode;

	/**
	 * 收款方账户类型（交易类型）
	 */
	private String tradeType;

	/**
	 * 收款行银行代码
	 */
	private String payeeBankCode;

	/**
	 * 收款行银行名称
	 */
	private String payeeBankName;

	/**
	 * 收款方开户行名称
	 */
	private String payeeOpeningBankName;

	/**
	 * 收款行所在省份代码
	 */
	private String payeeBankProvince;

	/**
	 * 收款行所在省份名称
	 */
	private String payeeBankProvinceName;

	/**
	 * 收款行所在城市代码
	 */
	private String payeeBankCity;

	/**
	 * 收款行所在城市名称
	 */
	private String payeeBankCityName;

	/**
	 * 请求付款金额
	 */
	private String requestAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 验证状态
	 */
	private Integer validStatus;

	/**
	 * 验证错误信息
	 */
	private String errorMsg;

	/**
	 * 付款金额
	 */
	private Long paymentAmount;

	/**
	 * 是否是付款方付手续费
	 */
	private Integer isPayerPayFee;

	/**
	 * 手续费
	 */
	private Long fee = 0L;

	/**
	 * 实际付款金额
	 */
	private Long realpayAmount = 0L;

	/**
	 * 实际出款金额
	 */
	private Long realoutAmount = 0L;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 订单状态(描述)
	 */
	private String orderStatusDesc;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;
	
	/**
	 * 开户行联行号
	 */
	private String bankNumber;
	
	//渠道号
	private String channelCode;
	
	private String payeeLoginName;
	private String payeeAcctcode;
	private Integer payeeAcctType;
	private Integer payeeMemberType;
	private Long payeeMemberCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getpaySeqNo() {
		return paySeqNo;
	}

	public void setpaySeqNo(Long paySeqNo) {
		this.paySeqNo = paySeqNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public Long getDetailSeq() {
		return detailSeq;
	}

	public void setDetailSeq(Long detailSeq) {
		this.detailSeq = detailSeq;
	}

	public Long getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(Long requestSeq) {
		this.requestSeq = requestSeq;
	}

	public String getForeignOrderId() {
		return foreignOrderId;
	}

	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}

	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}

	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	public String getPayeeBankCity() {
		return payeeBankCity;
	}

	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}

	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getPayeeLoginName() {
		return payeeLoginName;
	}

	public void setPayeeLoginName(String payeeLoginName) {
		this.payeeLoginName = payeeLoginName;
	}

	public String getPayeeAcctcode() {
		return payeeAcctcode;
	}

	public void setPayeeAcctcode(String payeeAcctcode) {
		this.payeeAcctcode = payeeAcctcode;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public Integer getPayeeMemberType() {
		return payeeMemberType;
	}

	public void setPayeeMemberType(Integer payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	public Long getPayeeMemberCode() {
		return payeeMemberCode;
	}

	public void setPayeeMemberCode(Long payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

}
