package com.pay.poss.dto.fi;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandy
 * @Date 2011-4-11
 * @Description 交易管理-收款明细查询入参DTO
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class IncomeDetailParaDTO implements Serializable {

	private static final long serialVersionUID = -1123007642612959168L;

	/** 开始时间 **/
	private Date startTime;

	/** 结束时间 **/
	private Date endTime;

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
	
}
