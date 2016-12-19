/** @Description 
 * @project 	fo-withdraw
 * @file 		AutoFundoutResult.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-13		Henry.Zeng			Create 
 */
package com.pay.fundout.autofundout.custom.model;

import com.pay.inf.model.BaseObject;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-12-13
 * @see
 */
public class AutoFundoutResult extends BaseObject {

	private static final long serialVersionUID = 1L;

	private AutoFundoutConfig autoFundoutConfig;

	private AutoQuotaConfig autoQuotaConfig;

	private AutoTimeConfig autoTimeConfig;

	private Long amount;

	private String errorMessage;

	private Integer autoType;
	
	//mail subject
	private String title;
	private String errorDesc;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the autoType
	 */
	public Integer getAutoType() {
		return autoType;
	}

	/**
	 * @param autoType
	 *            the autoType to set
	 */
	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AutoFundoutConfig getAutoFundoutConfig() {
		return autoFundoutConfig;
	}

	public void setAutoFundoutConfig(AutoFundoutConfig autoFundoutConfig) {
		this.autoFundoutConfig = autoFundoutConfig;
	}

	public AutoQuotaConfig getAutoQuotaConfig() {
		return autoQuotaConfig;
	}

	public void setAutoQuotaConfig(AutoQuotaConfig autoQuotaConfig) {
		this.autoQuotaConfig = autoQuotaConfig;
	}

	public AutoTimeConfig getAutoTimeConfig() {
		return autoTimeConfig;
	}

	public void setAutoTimeConfig(AutoTimeConfig autoTimeConfig) {
		this.autoTimeConfig = autoTimeConfig;
	}
}