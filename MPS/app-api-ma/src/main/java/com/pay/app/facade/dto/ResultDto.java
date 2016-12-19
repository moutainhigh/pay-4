package com.pay.app.facade.dto;

import java.io.Serializable;

public class ResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2708626760996568136L;

	private String status;
	private String errorCode;
	private String errorMsg;
	private String memerCode;
	private Object object;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getMemerCode() {
		return memerCode;
	}
	public void setMemerCode(String memerCode) {
		this.memerCode = memerCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
