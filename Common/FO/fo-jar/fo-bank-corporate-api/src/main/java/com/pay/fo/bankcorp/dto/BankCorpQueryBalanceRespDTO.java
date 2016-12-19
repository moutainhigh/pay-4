package com.pay.fo.bankcorp.dto;

import java.io.Serializable;

public class BankCorpQueryBalanceRespDTO extends RespDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5713402530973690064L;

	/**
	 * 出款银行编号
	 */
	private String fundoutBankCode;

	/***
	 * 付款方银行账号
	 */
	private String payerBankAcc;

	/**
	 * 付款方银行账号名称
	 */
	private String payerBankAccName;

	/**
	 * 银行账号余额
	 */
	private Long bankAccBalance;

	// 出款渠道编号
	private String channelCode;

	public String getFundoutBankCode() {
		return fundoutBankCode;
	}

	public void setFundoutBankCode(String fundoutBankCode) {
		this.fundoutBankCode = fundoutBankCode;
	}

	public String getPayerBankAcc() {
		return payerBankAcc;
	}

	public void setPayerBankAcc(String payerBankAcc) {
		this.payerBankAcc = payerBankAcc;
	}

	public String getPayerBankAccName() {
		return payerBankAccName;
	}

	public void setPayerBankAccName(String payerBankAccName) {
		this.payerBankAccName = payerBankAccName;
	}

	public Long getBankAccBalance() {
		return bankAccBalance;
	}

	public void setBankAccBalance(Long bankAccBalance) {
		this.bankAccBalance = bankAccBalance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}
