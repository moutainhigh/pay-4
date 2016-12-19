/**
 *  File: UploadFileDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.batchpaytoaccount;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class UploadFileDTO extends BaseObject {
	private Integer payAccount;
	private byte[] uploadFile;
	private String uploadFileName;
	private String payAccountName;
	private String uuid;
	private String randCode;

	public String getUuid() {
		return uuid;
	}

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPayAccountName() {
		return payAccountName;
	}

	public void setPayAccountName(String payAccountName) {
		this.payAccountName = payAccountName;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Integer getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(Integer payAccount) {
		this.payAccount = payAccount;
	}

	public byte[] getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(byte[] uploadFile) {
		this.uploadFile = uploadFile;
	}

}
