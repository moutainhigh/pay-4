/**
 *  <p>File: OrderInfoDetail.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.dto;

public class OrderInfoDetail extends OrderInfo {

	/**
	 * 交易开始时间
	 */
	private String startDate;
	/**
	 * 交易结束时间
	 */
	private String endDate;
	/**
	 * 账户类型
	 */
	private Integer accountType;
	/**
	 * 收款银行
	 */
	private String payeeBankName;
	/**
	 * 收款银行开户行名称
	 */
	private String openingBankName;
	/**
	 * 开户行所在城市
	 */
	private String provinceName;
	
	private String cityName;
	/**
	 * 收款人姓名
	 */
	private String payeeName;
	/**
	 * 收款人账号
	 */
	private String payeeBankNo;
	/**
	 * 交易备注
	 */
	private String remark;
	/**
	 * 付款方账号
	 */
	private String payerAcctCode ;
	/**
	 * 付款方账户名
	 * @return
	 */
	private String payerAcctName ;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(final Integer accountType) {
		this.accountType = accountType;
	}
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(final String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public String getOpeningBankName() {
		return openingBankName;
	}
	public void setOpeningBankName(final String openingBankName) {
		this.openingBankName = openingBankName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(final String provinceName) {
		this.provinceName = provinceName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(final String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeBankNo() {
		return payeeBankNo;
	}
	public void setPayeeBankNo(final String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(final String remark) {
		this.remark = remark;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}
	public String getPayerAcctCode() {
		return payerAcctCode;
	}
	public void setPayerAcctCode(final String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}
	public String getPayerAcctName() {
		return payerAcctName;
	}
	public void setPayerAcctName(final String payerAcctName) {
		this.payerAcctName = payerAcctName;
	}
	
}
