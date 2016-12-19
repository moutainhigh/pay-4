package com.pay.gateway.controller.command;


/**
 * @Title: AcctChargeCommand.java
 * @Package com.pay.gateway.controller.deposit
 * @Description: 充值页面参数
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-4-9 上午11:35:56
 * @version V1.0
 */
public class DepositCommand {
	private String bankInfo;
	private String dealAmount;
	private String memberCode;
	private String name;
	private String bankImgUrl;
	private String email;
	private String transactionNo;
	private String payAccount;
	private String payerCorpName;
	private String payerCustNo;
	private String payerCustOperNo;
	private String payFlag;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankImgUrl() {
		return bankImgUrl;
	}

	public void setBankImgUrl(String bankImgUrl) {
		this.bankImgUrl = bankImgUrl;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayerCorpName() {
		return payerCorpName;
	}

	public void setPayerCorpName(String payerCorpName) {
		this.payerCorpName = payerCorpName;
	}

	public String getPayerCustNo() {
		return payerCustNo;
	}

	public void setPayerCustNo(String payerCustNo) {
		this.payerCustNo = payerCustNo;
	}

	public String getPayerCustOperNo() {
		return payerCustOperNo;
	}

	public void setPayerCustOperNo(String payerCustOperNo) {
		this.payerCustOperNo = payerCustOperNo;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	@Override
	public String toString() {
		return "DepositCommand [bankImgUrl=" + bankImgUrl + ", bankInfo="
				+ bankInfo + ", dealAmount=" + dealAmount + ", email=" + email
				+ ", memberCode=" + memberCode + ", name=" + name
				+ ", payAccount=" + payAccount + ", payerCorpName="
				+ payerCorpName + ", payerCustNo=" + payerCustNo
				+ ", payerCustOperNo=" + payerCustOperNo + ", transactionNo="
				+ transactionNo + ", payFlag=" + payFlag + "]";
	}

	

}
