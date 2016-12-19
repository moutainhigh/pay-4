/**
 *  File: SearchResultInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-21      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.dto;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class SearchResultInfoDTO extends BaseObject {
	
	private static final long serialVersionUID = 2007012810811082371L;
	
	private String bankNo;
	private String bankName;
	private String clearBankNo;
	private String status;
	private String ccpc;
	private String provinceCode;
	private String cityCode;
	private String mark;
	private String provinceName;
	private String cityName;
	private String bankField;
	
	public String getBankField() {
		return bankField;
	}

	public void setBankField(String bankField) {
		this.bankField = bankField;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getClearBankNo() {
		return clearBankNo;
	}

	public void setClearBankNo(String clearBankNo) {
		this.clearBankNo = clearBankNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCcpc() {
		return ccpc;
	}

	public void setCcpc(String ccpc) {
		this.ccpc = ccpc;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Override
	public String toString() {
		return "SearchResultInfoDTO [bankName=" + bankName + ", bankNo=" + bankNo
				+ ", ccpc=" + ccpc + ", cityCode=" + cityCode
				+ ", clearBankNo=" + clearBankNo + ", mark=" + mark
				+ ", status=" + status + "]";
	}
}
