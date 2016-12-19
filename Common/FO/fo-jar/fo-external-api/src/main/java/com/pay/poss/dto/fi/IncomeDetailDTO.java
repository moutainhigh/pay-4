package com.pay.poss.dto.fi;

import java.io.Serializable;

/**
 * @author Sandy
 * @Date 2011-4-11
 * @Description 交易管理-收款明细查询结果DTO
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class IncomeDetailDTO implements Serializable {

	private static final long serialVersionUID = -1123007642612959168L;

	/** 交易时间 **/
	private String createTime;

	/** 流水号 **/
	private String serialNo;

	/** 订单号 **/
	private String orderSeq;
	
	/** 支付渠道 **/
	private String payChannel;

	/** 支付状态 **/
	private String payStatus;

	/** 通知状态 **/
	private String notifyStatus;

	/** 订单金额 **/
	private double orderAmount;
	
	/** 操作状态,用来给该条记录执行补单,关闭等动作 **/
	private String opStatus;
	
	private String partnerId;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
