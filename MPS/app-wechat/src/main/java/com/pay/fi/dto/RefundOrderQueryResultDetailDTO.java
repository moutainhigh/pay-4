package com.pay.fi.dto;

public class RefundOrderQueryResultDetailDTO {

	private String refundOrderID;	//3.4.4 商户退款订单号	refundOrderID
	private String orderID;			//商户原始订单号	orderID
	private String resultCode;		//处理结果码	resultCode
	private String refundAmount;	//商户退款金额	refundAmount
	private String refundTime;		//商户退款订单时间	refundTime
	private String completeTime;	//处理完成时间	completeTime
	private String refundNo;		//退款流水号	refundNo
	private String stateCode;		//状态码	stateCode
	private int status;
	private String refundDest;
	private String orderAmount;
	private String reason;
	private String errorMsg;
	private String applyAmount;
	private Long itemNo;
	private Long depositBackNo;
	private String payeeFee;
	
	
	public Long getItemNo() {
		return itemNo;
	}
	public void setItemNo(Long itemNo) {
		this.itemNo = itemNo;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRefundDest() {
		return refundDest;
	}
	public void setRefundDest(String refundDest) {
		this.refundDest = refundDest;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRefundOrderID() {
		return refundOrderID;
	}
	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getPayeeFee() {
		return payeeFee;
	}
	public void setPayeeFee(String payeeFee) {
		this.payeeFee = payeeFee;
	}
	public Long getDepositBackNo() {
		return depositBackNo;
	}
	public void setDepositBackNo(Long depositBackNo) {
		this.depositBackNo = depositBackNo;
	}
	@Override
	public String toString() {
		return "RefundOrderQueryResultDetailDTO [applyAmount=" + applyAmount
				+ ", completeTime=" + completeTime + ", depositBackNo="
				+ depositBackNo + ", errorMsg=" + errorMsg + ", itemNo="
				+ itemNo + ", orderAmount=" + orderAmount + ", orderID="
				+ orderID + ", payeeFee=" + payeeFee + ", reason=" + reason
				+ ", refundAmount=" + refundAmount + ", refundDest="
				+ refundDest + ", refundNo=" + refundNo + ", refundOrderID="
				+ refundOrderID + ", refundTime=" + refundTime
				+ ", resultCode=" + resultCode + ", stateCode=" + stateCode
				+ ", status=" + status + "]";
	}
}
