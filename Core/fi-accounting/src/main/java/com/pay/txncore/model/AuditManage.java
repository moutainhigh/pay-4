package com.pay.txncore.model;

import java.io.Serializable;
import java.util.Date;

public class AuditManage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7916971423937922501L;
	
	/**
	 * 交易流水号
	 */
	private String tradeOrderNo;
	
	/**
	 * 业务类型
	 */
	private String type;
	
	/**
	 * 申请时间
	 */
	private String crateDate;
	
	/**
	 * 审核状态
	 */
	private String status;

	/**
	 * 商户ID
	 * @return
	 */
    private String parterId;
    
    /**
	 * 商户订单号[必填 String(32)]
	 */
	private String orderID;
	
	/**
	 * 交易金额 [必填 String(18)] 
	 */
	private double orderAmount;
	
	/**
	 * 买家
	 */
	private String payerMark;
	
	/**
	 * 交易内容
	 * @return
	 */
	private String goodsName;
	
	/**
	 * 备注字段卖家
	 */
	private String payerAcc;
	
	/**
	 * 备注字段买家
	 */
	private String payeeAcc;
	
	/**
	 * 平台商ID
	 * @return
	 */
	private String platformId;
	
	/**
	 * 结束时间
	 * @return
	 */
	private String updateDate;
	
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPayerAcc() {
		return payerAcc;
	}

	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}

	public String getPayeeAcc() {
		return payeeAcc;
	}

	public void setPayeeAcc(String payeeAcc) {
		this.payeeAcc = payeeAcc;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getCrateDate() {
		return crateDate;
	}

	public void setCrateDate(String crateDate) {
		this.crateDate = crateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParterId() {
		return parterId;
	}

	public void setParterId(String parterId) {
		this.parterId = parterId;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayerMark() {
		return payerMark;
	}

	public void setPayerMark(String payerMark) {
		this.payerMark = payerMark;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
