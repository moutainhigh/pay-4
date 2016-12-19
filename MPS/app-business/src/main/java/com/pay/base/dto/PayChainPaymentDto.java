/**
 * 
 */
package com.pay.base.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 支付链付款人信息
 * @author DDR
 * Date 2011-9-23
 */
public class PayChainPaymentDto {
	
	private Date 	payDate; //支付时间
	private String gatewayNo; //网关订单号
	private String paymentName; //支付人名称
	private String payAmount = "0.00";  //支付金额 
	private String remark;   //备注
	private String orderNo; ////本地订单号
	/**
	 * @return the payDate
	 */
	public Date getPayDate() {
		return payDate;
	}
	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	/**
	 * @return the gatewayNo
	 */
	public String getGatewayNo() {
		return gatewayNo;
	}
	/**
	 * @param gatewayNo the gatewayNo to set
	 */
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	/**
	 * @return the paymentName
	 */
	public String getPaymentName() {
		return paymentName;
	}
	/**
	 * @param paymentName the paymentName to set
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	/**
	 * @return the payAmount
	 */
	public String getPayAmount() {
		return payAmount;
	}
	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(String payAmount) {
		this.payAmount = new DecimalFormat("0.00")
		.format(new BigDecimal(payAmount));
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
}
