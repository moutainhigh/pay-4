/**
 *  File: ElecBillsDto.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-6   Sany        Create
 *
 */
package com.pay.fo.elecbills.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 回单属性Dto
 */
public class ElecBillsDto {
	
	
	/** 批次号 **/
	private String batchNo;
	
	/** 交易号 **/
	private String tradeNo;
	
	/** 收款方名称 **/
	private String payeeName;
	
	/** 付款方名称 **/
	private String payerName;
	
	/** 收款方账号 **/
	private String payeeLoginName;
	
	/** 付款方账号 **/
	private String payerLoginName;
	
	
	/** 收款方银行账号 **/
	private String payeeBankNo;
	
	/** 收款方开户行 **/
	private String bankBranch;
	
	/** 协议代付方户名 **/
	private String agreementPayName;
	
	/** 协议代付方开户行 **/
	private String agreementPayBankName;
	
	/** 协议代付方账号 **/
	private String agreementPayBankNo;
	
	/** 金额 **/
	private String amount;
	
	/** 金额大写 **/
	@SuppressWarnings("unused")
	private String amountWords;
	
	/** 手续费 **/
	private String fee;
	
	/** 手续费大写 **/
	@SuppressWarnings("unused")
	private String feeWords;
	
	/** 出账金额 **/
	private String realAmount;
	
	/** 出账金额大写 **/
	@SuppressWarnings("unused")
	private String realAmountWords;
	
	/** 交易类型 **/
	private String tradeType;
	
	/** 交易时间 **/
	private String createTime;
	
	/** 交易备注 **/
	private String remark;
	
	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	private String getFormat(String amount) {
		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		DecimalFormat f = new DecimalFormat("#,##0.00");  
		f.setRoundingMode(RoundingMode.HALF_UP);
		return f.format(amountBig);
	}
	
	/**
	 * RMB大写
	 * @param amount
	 * @return
	 */
	private String getAmountWords(String amount) {
		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		return Trans2RMB.processNum(amountBig.toString());
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeLoginName() {
		return payeeLoginName;
	}

	public void setPayeeLoginName(String payeeLoginName) {
		this.payeeLoginName = payeeLoginName;
	}

	public String getPayerLoginName() {
		return payerLoginName;
	}

	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	public String getPayeeBankNo() {
		return payeeBankNo;
	}

	public void setPayeeBankNo(String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAgreementPayName() {
		return agreementPayName;
	}

	public void setAgreementPayName(String agreementPayName) {
		this.agreementPayName = agreementPayName;
	}

	public String getAgreementPayBankName() {
		return agreementPayBankName;
	}

	public void setAgreementPayBankName(String agreementPayBankName) {
		this.agreementPayBankName = agreementPayBankName;
	}

	public String getAgreementPayBankNo() {
		return agreementPayBankNo;
	}

	public void setAgreementPayBankNo(String agreementPayBankNo) {
		this.agreementPayBankNo = agreementPayBankNo;
	}

	public String getAmount() {
		return getFormat(amount);
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountWords() {
		return getAmountWords(amount);
	}

	public void setAmountWords(String amountWords) {
		this.amountWords = amountWords;
	}

	public String getFee() {
		return getFormat(fee);
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFeeWords() {
		return getAmountWords(fee);
	}

	public void setFeeWords(String feeWords) {
		this.feeWords = feeWords;
	}

	public String getRealAmount() {
		return getFormat(realAmount);
	}

	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}

	public String getRealAmountWords() {
		return getAmountWords(realAmount);
	}

	public void setRealAmountWords(String realAmountWords) {
		this.realAmountWords = realAmountWords;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
}
