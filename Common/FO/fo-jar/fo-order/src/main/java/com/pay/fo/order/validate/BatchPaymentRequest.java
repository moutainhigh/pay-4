/**
 *  File: Batch2bankRequest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate;

import java.util.List;

import jxl.Sheet;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * 
 */
public class BatchPaymentRequest extends PaymentRequest {

	/**
	 * 批次文件sheet
	 */
	private HSSFSheet sheet;
	/**
	 * 批次文件sheet
	 */
	private Sheet jxlSheet;

	/**
	 * 批次文件csv
	 */
	private List<String[]> csvList;

	private int fileType; //1.xls 2.csv

	/**
	 * 业务批次号
	 */
	private String businessNo;

	private Integer isPayerPayFee;

	private BatchPaymentResponse batchPaymentResponse;
	
	//----------------------added  PengJiangbo
	/**
	 * 收款方账户类型
	 */
	private Integer payerAcctType;
	/**
	 * 收款方账户类型
	 */
	private Integer payeeAcctType;
	/**
	 * 付款方币种代码
	 */
	private String payerCurrencyCode;
	/**
	 * 收款方币种代码
	 */
	private String payeeCurrencyCode;
	//----------------------added  PengJiangbo end
	

	public BatchPaymentRequest() {
		this.batchPaymentResponse = new BatchPaymentResponse();
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public BatchPaymentResponse getBatchPaymentResponse() {
		return batchPaymentResponse;
	}

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	public Sheet getJxlSheet() {
		return jxlSheet;
	}

	public void setJxlSheet(Sheet jxlSheet) {
		this.jxlSheet = jxlSheet;
	}

	public List<String[]> getCsvList() {
		return csvList;
	}

	public void setCsvList(List<String[]> csvList) {
		this.csvList = csvList;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	@Override
	public String toString() {
		return "BatchPaymentRequest [sheet=" + sheet + ", jxlSheet=" + jxlSheet
				+ ", csvList=" + csvList + ", fileType=" + fileType
				+ ", businessNo=" + businessNo + ", isPayerPayFee="
				+ isPayerPayFee + ", batchPaymentResponse="
				+ batchPaymentResponse + ", payerAcctType=" + payerAcctType
				+ ", payeeAcctType=" + payeeAcctType + ", payerCurrencyCode="
				+ payerCurrencyCode + ", payeeCurrencyCode="
				+ payeeCurrencyCode + "]";
	}

}
