/** @Description 
 * @project 	fo-bankfile
 * @file 		FileSummerInfo.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		Rick_lv			Create 
 */
package com.pay.fundout.bankfile.generator.model;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**
 * <p>
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-9
 * @see
 */
public class FileSummaryModel extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3031459707749413120L;
	private String date;//日期
	private String date2;//日期
	
	private String bankCode; // 提现银行
	private String withdrawBankCode;// 出款银行
	private String batchNum; // 批次号
	private BigDecimal totalAmount; // 总金额
	private int totalCount;// 总笔数
	private String bankName;
	private BigDecimal showTotalAmount;
	private Long orderSeq;
	private Long acctType;
	private String currencyCode;
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getAcctType() {
		return acctType;
	}
	public void setAcctType(Long acctType) {
		this.acctType = acctType;
	}
	public Long getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}
	private String cnpyBatchNum;
	
	private String totalCountString;// 格式化的总笔数
	
	private Integer areaCode;// 只针对中行，1表示上海卡，6表示非上海卡
	
	private String merchantCode;//商户号

	private String  payerName;//收款方开户行名称
	
	private String payeeOpeningBankName;//收款银行开户行
	
	private String  payeeBankAcctCode;//收款方银行账号
	
	private String showAmount;
	private String auditType;    //审核方式

	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getShowAmount() {
		return showAmount;
	}
	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}
	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}
	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}
	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}
	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}
	
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	/**
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}
	/**
	 * @param date2 the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getAreaCode() {
		return areaCode;
	}
	
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
	public String getTotalCountString() {
		return totalCountString;
	}
	public void setTotalCountString(String totalCountString) {
		this.totalCountString = totalCountString;
	}
	/**
	 * @return the cnpyBatchNum
	 */
	public String getCnpyBatchNum() {
		return cnpyBatchNum;
	}
	/**
	 * @param cnpyBatchNum the cnpyBatchNum to set
	 */
	public void setCnpyBatchNum(String cnpyBatchNum) {
		this.cnpyBatchNum = cnpyBatchNum;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}
	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public BigDecimal getTotalAmount() {
		if(null == this.totalAmount){
			return new BigDecimal(0);
		}
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
