package com.pay.app.cidverify;
/**
 * 银行卡鉴权参数对象
 * @author lei.jiangl 
 * @version 
 * @data 2010-9-13 上午10:01:33
 */
public class BaseCid2BankParameterModel {
	
	private String no;
	private String name;
	private String acctBank;
	private String acctBankAdd;
	private String acctBankName;
	private String bankCardNo;
	
	public String getAcctBank() {
		return acctBank;
	}
	public void setAcctBank(String acctBank) {
		this.acctBank = acctBank;
	}
	public String getAcctBankAdd() {
		return acctBankAdd;
	}
	public void setAcctBankAdd(String acctBankAdd) {
		this.acctBankAdd = acctBankAdd;
	}
	public String getAcctBankName() {
		return acctBankName;
	}
	public void setAcctBankName(String acctBankName) {
		this.acctBankName = acctBankName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
