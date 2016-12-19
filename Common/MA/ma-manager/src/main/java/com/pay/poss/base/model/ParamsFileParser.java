 /** @Description 
 * @project 	poss-reconcile
 * @file 		ParamsFileParser.java 
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-30		Henry.Zeng			Create 
*/
package com.pay.poss.base.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-7-30
 * @see 
 */
public class ParamsFileParser extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String bankCode;
	
	private Long parserKy ; 
	
	private String providerCode ; 
	
	private String parserClass;

	private String storePath;

	private String version;
	
	private Date createTime;
	
	private String status;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getParserKy() {
		return parserKy;
	}

	public void setParserKy(Long parserKy) {
		this.parserKy = parserKy;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getParserClass() {
		return parserClass;
	}

	public void setParserClass(String parserClass) {
		this.parserClass = parserClass;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
