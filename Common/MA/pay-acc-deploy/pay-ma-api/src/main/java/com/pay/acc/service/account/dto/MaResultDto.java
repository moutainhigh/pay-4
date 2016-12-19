package com.pay.acc.service.account.dto;
import java.io.Serializable;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;


/**
 * @author jim_chen
 * @version 
 * 2010-11-19 
 * MA接口返回结果Dto
 * 
 */
public class MaResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2708626760996568136L;

	/**
	 *  返回的结果
	 */
	private int resultStatus;

	/**
	 *  错误编号
	 */
	private String errorCode;
	/**
	 *  错误信息
	 */
	private String errorMsg;

	/**
	 *  返回的结果
	 */
	private boolean resultBool;

	
	/**
	 *  选用(返回对象)
	 */
	private Object object;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.resultBool=true;
		this.object = object;
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
		this.resultBool=false;
	}
	public int getResultStatus() {
    	return resultStatus;
    }
	public void setResultStatus(int resultStatus) {
    	this.resultStatus = resultStatus;
    }
	public boolean isResultBool() {
		return resultBool;
	}
	public void setResultBool(boolean resultBool) {
		this.resultBool = resultBool;
	}

	@SuppressWarnings("unused")
	public void setErrorEnum(ErrorExceptionEnum ee){
		this.setErrorMsg(ee.getMessage());
		this.setErrorCode(ee.getErrorCode());
		this.setResultBool(false);
	}
}

