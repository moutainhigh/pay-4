package com.pay.fi.dto;

public class OrderQueryResultDetailDTO {
	
	private String orderID=""; 		    //3.4.3商户订单号 	orderID
	private String orderAmount="" ; 	//商户订单金额	orderAmount
	private String payAmount=""; 		//实际支付金额	payAmount
	private String acquiringTime=""; 	//收单时间	acquiringTime
	private String completeTime=""; 	//支付完成时间	completeTime
	private String orderNo="";			//支付流水号;	orderNo
	private String stateCode=""; 		//状态码	stateCode
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getAcquiringTime() {
		return acquiringTime;
	}
	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	@Override
	public String toString() {
		return "orderID="+orderID+
				"&orderAmount="+orderAmount+
				"&payAmount="+payAmount+
				"&acquiringTime="+acquiringTime+
				"&completeTime="+completeTime+
				"&orderNo="+orderNo+
				"&stateCode="+stateCode;
	}
	
}
