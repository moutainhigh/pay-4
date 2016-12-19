package com.pay.app.hesssion.dto;

public class CidVerifyResultDto {

	public static final int SUCCESS = 1;
	public static final int FAILED = 2;
	public static final int EXCEPTION = -1;

	public CidVerifyResultDto(boolean  statusBool) {
		if(statusBool){
			status = 1;
			message = "成功";
		}
		else{
			status = 2;
			message = "认证失败，与公安网不一致";
		}
	}
	public CidVerifyResultDto(int status,String msg) {
		this.status = status;
		this.message = msg;
	}

	private Integer status;
	private String message;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
