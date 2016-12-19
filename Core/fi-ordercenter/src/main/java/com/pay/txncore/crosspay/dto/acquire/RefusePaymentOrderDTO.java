package com.pay.txncore.crosspay.dto.acquire;

import java.io.Serializable;
import java.util.Date;

/**
 * website拒付记录查询
 */
public class RefusePaymentOrderDTO implements Serializable {
	private static final long serialVersionUID = 7384624079437718569L;

	/**
	 * 拒付订单号
	 */
	private Long id;

	/**
	 * 交易号
	 */
	private Long tradeOrderNo;

	/**
	 * 运单号
	 */
	private String trackingNo;
	/**
	 * 所属网站
	 */
	private String tradeUrl;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 拒付申请时间
	 */
	private Date applyDate;
	/**
	 * 审核时间
	 */
	private Date checkDate;
	/**
	 * 交易时间
	 */
	private Date tradeDate;

	/**
	 * 拒付费用
	 */
	private Double arbFee;
	/**
	 * 拒付状态
	 */
	private String refuseStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getTradeUrl() {
		return tradeUrl;
	}

	public void setTradeUrl(String tradeUrl) {
		this.tradeUrl = tradeUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Double getArbFee() {
		return arbFee;
	}

	public void setArbFee(Double arbFee) {
		this.arbFee = arbFee;
	}

	public String getRefuseStatus() {
		return refuseStatus;
	}

	public void setRefuseStatus(String refuseStatus) {
		this.refuseStatus = refuseStatus;
	}

}