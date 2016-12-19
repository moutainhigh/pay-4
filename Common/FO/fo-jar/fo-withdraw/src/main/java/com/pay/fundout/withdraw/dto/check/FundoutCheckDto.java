package com.pay.fundout.withdraw.dto.check;

import java.util.Date;

public class FundoutCheckDto {

	private String id;
	
	private String batchNum;
	
	private String status;  //状态2 为复核失败  1为复核成功 
 	
	private String bankAccountCode;
	
	private String amount;
	
	private String bankAccount;
	
	private String bankName;
	
	private String bankCode;
	
	private String extrafields;
	
	private String successCount;
	
	public String getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getExtrafields() {
		return extrafields;
	}

	public void setExtrafields(String extrafields) {
		this.extrafields = extrafields;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
