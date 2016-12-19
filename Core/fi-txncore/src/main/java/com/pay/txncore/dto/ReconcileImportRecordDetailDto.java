package com.pay.txncore.dto;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class ReconcileImportRecordDetailDto extends BaseObject {
	/***
	 * 对账明细ID
	 */
	private String id;
	/***
	 * 渠道订单号
	 */
	private Long channelOrderNo;
	/***
	 * 失败原因
	 */
	private String remark;
	/**
	 * 对账 状态
	 */
	private Integer status;
	/***
	 * 批次号
	 */
	
	private String operator;
	
	private Date createDate;
	
	private String batchNoDetail;
	
	private String merchantNo;
	
	private Date transactionDate;
	
	private Date postingDate;
	
	private String type;
	
	private String credoraxStatus;
	
	private String transCurrency;
	
	private String transAmount;
	
	private String acctCurrency;
	
	private String acctAmountGross;
	
	private String acctTotalCharges;
	
	private String captureMethod;
	
	private String merchTranRef;
	
	private String authCode;
	
	private String merchantName;
	
	private String transActionCountry;
	
	private String areaOfEvent;
	
	private String fpi;
	
	private String feeErcentage;
	
	private String base;
	
	private String interChangeCurrency;
	
	private String interChangeAmount;
	
	private String debitOrCreditCard;
	
	private String merchantCity;
	
	private String acquirerRef;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCredoraxStatus() {
		return credoraxStatus;
	}

	public void setCredoraxStatus(String credoraxStatus) {
		this.credoraxStatus = credoraxStatus;
	}

	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getAcctCurrency() {
		return acctCurrency;
	}

	public void setAcctCurrency(String acctCurrency) {
		this.acctCurrency = acctCurrency;
	}

	public String getAcctAmountGross() {
		return acctAmountGross;
	}

	public void setAcctAmountGross(String acctAmountGross) {
		this.acctAmountGross = acctAmountGross;
	}

	public String getAcctTotalCharges() {
		return acctTotalCharges;
	}

	public void setAcctTotalCharges(String acctTotalCharges) {
		this.acctTotalCharges = acctTotalCharges;
	}

	public String getCaptureMethod() {
		return captureMethod;
	}

	public void setCaptureMethod(String captureMethod) {
		this.captureMethod = captureMethod;
	}

	public String getMerchTranRef() {
		return merchTranRef;
	}

	public void setMerchTranRef(String merchTranRef) {
		this.merchTranRef = merchTranRef;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTransActionCountry() {
		return transActionCountry;
	}

	public void setTransActionCountry(String transActionCountry) {
		this.transActionCountry = transActionCountry;
	}

	public String getAreaOfEvent() {
		return areaOfEvent;
	}

	public void setAreaOfEvent(String areaOfEvent) {
		this.areaOfEvent = areaOfEvent;
	}

	public String getBatchNoDetail() {
		return batchNoDetail;
	}

	public void setBatchNoDetail(String batchNoDetail) {
		this.batchNoDetail = batchNoDetail;
	}

	public String getFpi() {
		return fpi;
	}

	public void setFpi(String fpi) {
		this.fpi = fpi;
	}

	public String getFeeErcentage() {
		return feeErcentage;
	}

	public void setFeeErcentage(String feeErcentage) {
		this.feeErcentage = feeErcentage;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getInterChangeCurrency() {
		return interChangeCurrency;
	}

	public void setInterChangeCurrency(String interChangeCurrency) {
		this.interChangeCurrency = interChangeCurrency;
	}

	public String getInterChangeAmount() {
		return interChangeAmount;
	}

	public void setInterChangeAmount(String interChangeAmount) {
		this.interChangeAmount = interChangeAmount;
	}

	public String getDebitOrCreditCard() {
		return debitOrCreditCard;
	}

	public void setDebitOrCreditCard(String debitOrCreditCard) {
		this.debitOrCreditCard = debitOrCreditCard;
	}

	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public String getAcquirerRef() {
		return acquirerRef;
	}

	public void setAcquirerRef(String acquirerRef) {
		this.acquirerRef = acquirerRef;
	}
	
}
