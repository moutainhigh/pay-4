package com.pay.base.dto;
import java.io.Serializable;

import com.pay.base.common.enums.ErrorCodeEnum;

/**
 *  File: ResultDto.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-10-2     
 *  Author   zengjin     
 *  Changes   接口返回结果Dto
 *  Comment
 */
public class ResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2708626760996568136L;

	/**
	 *  返回的结果 (bool值)
	 */
	private boolean resultStatus;
	/**
	 *  错误编号
	 */
	private String errorCode;
	/**
	 *  错误信息
	 */
	private String errorMsg;
	/**
	 *  选用(memberCode)
	 */
	private String memberCode;

	/**
	 *  选用(返回对象)
	 */
	private Object object;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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
	public boolean isResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(boolean resultStatus) {
		this.resultStatus = resultStatus;
	}

	
	/**
	 *  有错误时调用
	 */   
    public void setErrorNum(ErrorCodeEnum errorEnum){
    	this.setResultStatus(false);
       this.setErrorCode(errorEnum.getErrorCode());
       this.setErrorMsg(errorEnum.getMessage());
    }
}

