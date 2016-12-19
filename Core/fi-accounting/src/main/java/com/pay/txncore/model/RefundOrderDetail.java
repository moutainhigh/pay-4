/**
 * 
 */
package com.pay.txncore.model;

import java.math.BigDecimal;

/**
 * @author huhb
 *
 */
public class RefundOrderDetail implements java.io.Serializable {
	
	private String createTime;
	
	/**
	 * 网关退款订单号
	 */
	private String refundOrderID;
	
	/**
	 * 订单号（trade_order表的order_id）
	 */
	private String orderID;
	
	/**
	 * 原始交易订单金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 申请退款金额
	 */
	private BigDecimal applyAmount;
	
	private int status;
	
	/**
	 * 退款类型
	 */
	private String refundObjType;
	
	/**
	 * 充退订单编号
	 */
	private Long depositBackNo;
	

	public Long getDepositBackNo() {
		return depositBackNo;
	}

	public void setDepositBackNo(Long depositBackNo) {
		this.depositBackNo = depositBackNo;
	}

	public String getRefundObjType() {
		return refundObjType;
	}

	public void setRefundObjType(String refundObjType) {
		this.refundObjType = refundObjType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RefundOrderDetail [applyAmount=" + applyAmount
				+ ", createTime=" + createTime + ", orderAmount=" + orderAmount
				+ ", orderID=" + orderID + ", refundOrderID=" + refundOrderID
				+ ", status=" + status + "]";
	}
}
