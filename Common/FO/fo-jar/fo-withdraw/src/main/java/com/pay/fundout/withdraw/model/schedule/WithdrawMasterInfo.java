package com.pay.fundout.withdraw.model.schedule;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

//提现批次 概要信息
public class WithdrawMasterInfo extends BaseObject {
	private static final long serialVersionUID = 1385051870244358461L;
	
	private String bankCode; // 提现银行
	private String withdrawBankCode;//出款银行
	private String batchNum; // 批次号
	private BigDecimal totalAmount; // 总金额
	private int totalCount;// 总笔数
	private String bankName;
	private BigDecimal showTotalAmount;
	
	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getShowTotalAmount() {
		return showTotalAmount;
	}

	public void setShowTotalAmount(BigDecimal showTotalAmount) {
		this.showTotalAmount = showTotalAmount;
	}

}
