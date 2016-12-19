/**
 *  <p>File: CmbGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.cmb;

/**
 * <p>生成招行文件Model</p>
 * @author zengli
 * @since 2011-5-17
 * @see 
 */
public class CmbGeneratorModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 业务参考号
	 */
	private String busiNo;
	/**
	 * 收款人编号
	 */
	private String payeeNo;
	/**
	 * 收款人帐号
	 */
	private String payeeAccNo;

	/**
	 * 收款人名称
	 */
	private String payeeAccName;
	/**
	 * 收方开户支行
	 */
	private String payeeBankBranch;
	/**
	 * 收款人所在省
	 */
	private String payeeProvince;
	/**
	 * 收方开户银行
	 */
	private String payeeBank;
	/**
	 * 收款人所在市
	 */
	private String payeeCity;
	/**
	 * 收方邮件地址
	 */
	private String payeeEmail;
	/**
	 * 收方移动电话
	 */
	private String payeeMobile;
	/**
	 * 币种
	 */
	private String currencyType ;
	/**
	 * 付款分行
	 */
	private String payerBank;
	/**
	 * 结算方式
	 */
	private String jsMode;
	/**
	 * 付方帐号
	 */
	private String payerAccNo;
	/**
	 * 期望日
	 */
	private String expectDate;
	/**
	 * 期望时间
	 */
	private String expectTime;
	/**
	 * 用途,交易流水号存放在这里
	 */
	private String use;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 收方联行号
	 */
	private String unionBankCode;
	
	/**
	 * 业务摘要
	 */
	private String busiSummary;
	
	/**
	 * @return the busiNo
	 */
	public String getBusiNo() {
		return busiNo;
	}

	/**
	 * @param busiNo the busiNo to set
	 */
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	/**
	 * @return the payeeNo
	 */
	public String getPayeeNo() {
		return payeeNo;
	}

	/**
	 * @param payeeNo the payeeNo to set
	 */
	public void setPayeeNo(String payeeNo) {
		this.payeeNo = payeeNo;
	}

	/**
	 * @return the payeeAccNo
	 */
	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	/**
	 * @param payeeAccNo the payeeAccNo to set
	 */
	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	/**
	 * @return the payeeAccName
	 */
	public String getPayeeAccName() {
		return payeeAccName;
	}

	/**
	 * @param payeeAccName the payeeAccName to set
	 */
	public void setPayeeAccName(String payeeAccName) {
		this.payeeAccName = payeeAccName;
	}

	/**
	 * @return the payeeBankBranch
	 */
	public String getPayeeBankBranch() {
		return payeeBankBranch;
	}

	/**
	 * @param payeeBankBranch the payeeBankBranch to set
	 */
	public void setPayeeBankBranch(String payeeBankBranch) {
		this.payeeBankBranch = payeeBankBranch;
	}

	/**
	 * @return the payeeProvince
	 */
	public String getPayeeProvince() {
		return payeeProvince;
	}

	/**
	 * @param payeeProvince the payeeProvince to set
	 */
	public void setPayeeProvince(String payeeProvince) {
		this.payeeProvince = payeeProvince;
	}

	/**
	 * @return the payeeBank
	 */
	public String getPayeeBank() {
		return payeeBank;
	}

	/**
	 * @param payeeBank the payeeBank to set
	 */
	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	/**
	 * @return the payeeCity
	 */
	public String getPayeeCity() {
		return payeeCity;
	}

	/**
	 * @param payeeCity the payeeCity to set
	 */
	public void setPayeeCity(String payeeCity) {
		this.payeeCity = payeeCity;
	}

	/**
	 * @return the payeeEmail
	 */
	public String getPayeeEmail() {
		return payeeEmail;
	}

	/**
	 * @param payeeEmail the payeeEmail to set
	 */
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}

	/**
	 * @return the payeeMobile
	 */
	public String getPayeeMobile() {
		return payeeMobile;
	}

	/**
	 * @param payeeMobile the payeeMobile to set
	 */
	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	/**
	 * @return the currencyType
	 */
	public String getCurrencyType() {
		return currencyType;
	}

	/**
	 * @param currencyType the currencyType to set
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * @return the payerBank
	 */
	public String getPayerBank() {
		return payerBank;
	}

	/**
	 * @param payerBank the payerBank to set
	 */
	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	/**
	 * @return the jsMode
	 */
	public String getJsMode() {
		return jsMode;
	}

	/**
	 * @param jsMode the jsMode to set
	 */
	public void setJsMode(String jsMode) {
		this.jsMode = jsMode;
	}

	/**
	 * @return the payerAccNo
	 */
	public String getPayerAccNo() {
		return payerAccNo;
	}

	/**
	 * @param payerAccNo the payerAccNo to set
	 */
	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	/**
	 * @return the expectDate
	 */
	public String getExpectDate() {
		return expectDate;
	}

	/**
	 * @param expectDate the expectDate to set
	 */
	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}

	/**
	 * @return the expectTime
	 */
	public String getExpectTime() {
		return expectTime;
	}

	/**
	 * @param expectTime the expectTime to set
	 */
	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}

	/**
	 * @return the use
	 */
	public String getUse() {
		return use;
	}

	/**
	 * @param use the use to set
	 */
	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the unionBankCode
	 */
	public String getUnionBankCode() {
		return unionBankCode;
	}

	/**
	 * @param unionBankCode the unionBankCode to set
	 */
	public void setUnionBankCode(String unionBankCode) {
		this.unionBankCode = unionBankCode;
	}

	/**
	 * @return the busiSummary
	 */
	public String getBusiSummary() {
		return busiSummary;
	}

	/**
	 * @param busiSummary the busiSummary to set
	 */
	public void setBusiSummary(String busiSummary) {
		this.busiSummary = busiSummary;
	}
}
