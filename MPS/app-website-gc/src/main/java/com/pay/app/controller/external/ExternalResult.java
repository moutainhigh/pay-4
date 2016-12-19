package com.pay.app.controller.external;

public class ExternalResult {
	private boolean returnBool=false;  //正常返回true 失败返回false
	private String resultMsg; //返回的信息
	private Object resultObj;
	public Object getResultObj() {
		return resultObj;
	}
	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}
	
	public boolean isReturnBool() {
		return returnBool;
	}
	public void setReturnBool(boolean returnBool) {
		this.returnBool = returnBool;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
