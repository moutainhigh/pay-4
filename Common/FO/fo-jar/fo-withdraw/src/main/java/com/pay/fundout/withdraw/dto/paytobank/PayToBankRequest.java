/**
 * 
 */
package com.pay.fundout.withdraw.dto.paytobank;

import java.io.Serializable;

/**
 * 付款至银行卡请求
 * @author zliner
 *
 */
public class PayToBankRequest implements Serializable {
	//序号
	private static final long serialVersionUID = -6979753706054488416L;
	//会员号
	private Long memberCode;
	//会员类型
	private Long memberType;
	//账户号
	private Long memberAcct;
	//帐户类型。默认人民币账户 
	private Long memberAccType;
	//付款至银行卡金额,以分为单位
	private Long amount;
	//收款人名称
	private String accountName;
	//银行卡卡号
	private String bankCardNo;
	//银行卡类型
	private Long bankCardType;
	//银行编号
	private String bankCode;
	//开户行支行
	private String bankBranch;
	//开户省份
	private Short bankProvince;
	//开户行所在城市
	private Short bankCity;
	//备注
	private String remarks;
	//币种
	private String moneyType;
	//业务类型
	private Long busiType;
	//发起出款请求来源
	private String fundorigin;
	//出款方式
	private Short withdrawType;
	//出款银行编号
	private String withdrawBankCode;
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Long getMemberType() {
		return memberType;
	}
	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}
	public Long getMemberAcct() {
		return memberAcct;
	}
	public void setMemberAcct(Long memberAcct) {
		this.memberAcct = memberAcct;
	}
	public Long getMemberAccType() {
		return memberAccType;
	}
	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public Long getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(Long bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public Short getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}
	public Short getBankCity() {
		return bankCity;
	}
	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public Long getBusiType() {
		return busiType;
	}
	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}
	public String getFundorigin() {
		return fundorigin;
	}
	public void setFundorigin(String fundorigin) {
		this.fundorigin = fundorigin;
	}
	public Short getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(Short withdrawType) {
		this.withdrawType = withdrawType;
	}
	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}
	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}
	
}
