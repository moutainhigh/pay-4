/**
 *  File: BankInfoDto.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   terry     Create
 *
 */
package com.pay.lucene.dto;

/**
 * 
 */
public class BankInfoDto {

	private String bankNo;
	private String bankName;
	private String bankKaihu;
	private String bankField;
	private String provinceName;
	private String cityName;

	private String proviceField;
	private String cityField;
	private String addressField;
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
	public String getProviceField() {
		return proviceField;
	}
	public void setProviceField(String proviceField) {
		this.proviceField = proviceField;
	}
	public String getCityField() {
		return cityField;
	}
	public void setCityField(String cityField) {
		this.cityField = cityField;
	}
	public String getAddressField() {
		return addressField;
	}
	public void setAddressField(String addressField) {
		this.addressField = addressField;
	}
	public String getBankKaihu() {
		return bankKaihu;
	}
	public void setBankKaihu(String bankKaihu) {
		this.bankKaihu = bankKaihu;
	}
	
}
