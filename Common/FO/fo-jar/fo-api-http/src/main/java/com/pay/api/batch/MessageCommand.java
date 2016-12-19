/**
 *  File: MessageCommand.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Nov 25, 2011   ch-ma     Create
 *
 */
package com.pay.api.batch;

/**
 * 
 */
public class MessageCommand {

	private String websvrCode;

	private String productCode;

	private String merchantCode;
	
	private String bizNo;

	private String version;

	private String requestIp;
	
	private String clientIp;

	public String getWebsvrCode() {
		return websvrCode;
	}

	public void setWebsvrCode(String websvrCode) {
		this.websvrCode = websvrCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

}
