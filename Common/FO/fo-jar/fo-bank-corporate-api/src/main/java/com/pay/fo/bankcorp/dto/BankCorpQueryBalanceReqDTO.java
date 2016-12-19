package com.pay.fo.bankcorp.dto;

import java.io.Serializable;

public class BankCorpQueryBalanceReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8529062017236413501L;
	
	/**
	 * 渠道编号
	 */
	private String channelCode;
	
	/**
	 * 出款银行名称
	 */
	private String fundoutBankName;
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
	 * 交易代码
	 */
	private String transCode;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

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

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFundoutBankName() {
		return fundoutBankName;
	}

	public void setFundoutBankName(String fundoutBankName) {
		this.fundoutBankName = fundoutBankName;
	}
	
	
	

}
