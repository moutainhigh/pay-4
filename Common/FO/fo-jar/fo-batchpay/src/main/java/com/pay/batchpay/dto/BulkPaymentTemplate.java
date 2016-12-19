/**
 * 
 */
package com.pay.batchpay.dto;

import java.math.BigDecimal;

/**
 * 批量付款模板
 * @author PengJiangbo
 *
 */
public class BulkPaymentTemplate {
	//主键
	private long id ;
	//收款人姓名
	private String toAccountName ;
	//银行名称
	private String bankName ;
	//开户行名称（支行）
	private String branchBankName ;
	//swift/iban_code
	private String siCode;
	//银行地址
	private String bankAddress ;
	//收款人银行账户
	private String bankAccount ;
	//账户类型
	private String accountType ;
	//付款金额
	private BigDecimal payAmount ;
	//批量付款批次号，关联T_BULKPayment_order
	private String bulkpayNo ;
	//账户类型编码1：对公2：对私
	private int accountTypeCode ;
	//付款币种
	private String currency ;
	//付款币种代码
	private String currencyCode ;
	
	//付款结果
	private int payResult ;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToAccountName() {
		return toAccountName;
	}
	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getSiCode() {
		return siCode;
	}
	public void setSiCode(String siCode) {
		this.siCode = siCode;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getBulkpayNo() {
		return bulkpayNo;
	}
	public void setBulkpayNo(String bulkpayNo) {
		this.bulkpayNo = bulkpayNo;
	}
	public int getAccountTypeCode() {
		return accountTypeCode;
	}
	public void setAccountTypeCode(int accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public int getPayResult() {
		return payResult;
	}
	public void setPayResult(int payResult) {
		this.payResult = payResult;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + accountTypeCode;
		result = prime * result
				+ ((bankAccount == null) ? 0 : bankAccount.hashCode());
		result = prime * result
				+ ((bankAddress == null) ? 0 : bankAddress.hashCode());
		result = prime * result
				+ ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result
				+ ((branchBankName == null) ? 0 : branchBankName.hashCode());
		result = prime * result
				+ ((bulkpayNo == null) ? 0 : bulkpayNo.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((payAmount == null) ? 0 : payAmount.hashCode());
		result = prime * result + ((siCode == null) ? 0 : siCode.hashCode());
		result = prime * result
				+ ((toAccountName == null) ? 0 : toAccountName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BulkPaymentTemplate other = (BulkPaymentTemplate) obj;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (accountTypeCode != other.accountTypeCode)
			return false;
		if (bankAccount == null) {
			if (other.bankAccount != null)
				return false;
		} else if (!bankAccount.equals(other.bankAccount))
			return false;
		if (bankAddress == null) {
			if (other.bankAddress != null)
				return false;
		} else if (!bankAddress.equals(other.bankAddress))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (branchBankName == null) {
			if (other.branchBankName != null)
				return false;
		} else if (!branchBankName.equals(other.branchBankName))
			return false;
		if (bulkpayNo == null) {
			if (other.bulkpayNo != null)
				return false;
		} else if (!bulkpayNo.equals(other.bulkpayNo))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (id != other.id)
			return false;
		if (payAmount == null) {
			if (other.payAmount != null)
				return false;
		} else if (!payAmount.equals(other.payAmount))
			return false;
		if (siCode == null) {
			if (other.siCode != null)
				return false;
		} else if (!siCode.equals(other.siCode))
			return false;
		if (toAccountName == null) {
			if (other.toAccountName != null)
				return false;
		} else if (!toAccountName.equals(other.toAccountName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BulkPaymentTemplate [id=" + id + ", toAccountName="
				+ toAccountName + ", bankName=" + bankName
				+ ", branchBankName=" + branchBankName + ", siCode=" + siCode
				+ ", bankAddress=" + bankAddress + ", bankAccount="
				+ bankAccount + ", accountType=" + accountType + ", payAmount="
				+ payAmount + ", bulkpayNo=" + bulkpayNo + ", accountTypeCode="
				+ accountTypeCode + ", currency=" + currency
				+ ", currencyCode=" + currencyCode + ", payResult=" + payResult
				+ "]";
	}
	
}
