/**
 * 
 */
package com.pay.txncore.dto.refund;

import java.math.BigDecimal;

/**
 * 退款订单明细DTO
 * @author huhb
 *
 */
public class RefundOrderDetailDTO {

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
	private String orderAmount;
	
	/**
	 * 申请退款金额
	 */
	private String applyAmount;
	
	private int status;
	
	private String refundObjType;
	
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

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
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

	@Override
	public String toString() {
		return "RefundOrderDetailDTO [applyAmount=" + applyAmount
				+ ", createTime=" + createTime + ", orderAmount=" + orderAmount
				+ ", orderID=" + orderID + ", refundOrderID=" + refundOrderID
				+ ", status=" + status + "]";
	}
	
}
