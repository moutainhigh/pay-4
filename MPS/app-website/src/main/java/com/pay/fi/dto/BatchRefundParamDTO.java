/**
 * 
 */
package com.pay.fi.dto;

/**
 * @author PengJiangbo
 *
 */
public class BatchRefundParamDTO {
	
	//商户订单号
	private String orderId ;
	//交易金额
	private String orderAmount ;
	//交易币种
	private String currencyCode ;
	//退款金额
	private String refundAmount ;
	
	//失败原因
	private String remark ;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderAmount
	 */
	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the refundAmount
	 */
	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
