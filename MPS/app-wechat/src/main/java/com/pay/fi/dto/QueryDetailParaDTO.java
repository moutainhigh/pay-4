package com.pay.fi.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class QueryDetailParaDTO implements Serializable {

	private static final long serialVersionUID = -1123007642612959168L;

	/** 开始时间 **/
	private Date startTime;

	/** 结束时间 **/
	private Date endTime;
	
	/**
	 * 支付开始时间
	 */
	private Date payStartTime;
	
	/**
	 * 支付结束时间
	 */
	private Date payEndTime;

	/** 支付状态 **/
	private String payStatus[];

	/** 支付渠道 **/
	private String payChannel;

	/** 商家订单号 **/
	private String orderSeq;
	
	/** 用户memberCode **/
	private String memberCode;

	/** 交易流水号 **/
	private String serialNo;
	
	/** 通知状态 **/
	private String notifyStatus;
	
	/** 开始金额范围 **/
	private Double sOrderAmount;
	
	/** 结束金额范围 **/
	private Double eOrderAmount;
	
	/** 订单状态 **/
	private String orderStatus;
	
	/** 协议流水号 **/
	private String protocolNo;
	
	/**	PNR码 **/
	private String pnrCode;
	
	public String getPnrCode() {
		return pnrCode;
	}

	public void setPnrCode(String pnrCode) {
		this.pnrCode = pnrCode;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public String[] getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String[] payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Double getsOrderAmount() {
		return sOrderAmount;
	}

	public void setsOrderAmount(Double sOrderAmount) {
		this.sOrderAmount = sOrderAmount;
	}

	public Double geteOrderAmount() {
		return eOrderAmount;
	}

	public void seteOrderAmount(Double eOrderAmount) {
		this.eOrderAmount = eOrderAmount;
	}
	
	public Date getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	@Override
	public String toString() {
		return "QueryDetailParaDTO [eOrderAmount=" + eOrderAmount
				+ ", endTime=" + endTime + ", memberCode=" + memberCode
				+ ", notifyStatus=" + notifyStatus + ", orderSeq=" + orderSeq
				+ ", orderStatus=" + orderStatus + ", payChannel=" + payChannel
				+ ", payEndTime=" + payEndTime + ", payStartTime="
				+ payStartTime + ", payStatus=" + Arrays.toString(payStatus)
				+ ", pnrCode=" + pnrCode + ", protocolNo=" + protocolNo
				+ ", sOrderAmount=" + sOrderAmount + ", serialNo=" + serialNo
				+ ", startTime=" + startTime + "]";
	}

}
