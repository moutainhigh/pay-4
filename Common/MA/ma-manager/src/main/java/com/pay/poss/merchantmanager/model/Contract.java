/**
 * 
 */
package com.pay.poss.merchantmanager.model;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		Contract.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-8-11		gungun_zhang			Create
 */
 
 
public class Contract {
	
	private String contractCode;
	private String contractTime;
	private String contractName;
	private String merchantName;
	private String contractNo;
	private String contractContact;
	private String contractContactPhone;
	private String contractStatus;
	
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractTime() {
		return contractTime;
	}
	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractContact() {
		return contractContact;
	}
	public void setContractContact(String contractContact) {
		this.contractContact = contractContact;
	}
	public String getContractContactPhone() {
		return contractContactPhone;
	}
	public void setContractContactPhone(String contractContactPhone) {
		this.contractContactPhone = contractContactPhone;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	
	
		
}
