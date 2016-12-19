 /** @Description 
 * @project 	poss-refund
 * @file 		RechargeRecordDto.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-27		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 封装单条充值记录
 * @author sunsea_li
 *
 */
public class RechargeRecordDto  extends BaseObject{
	private static final long serialVersionUID = 425660318654649529L;
    private Long rechargeOrderSeq;//充值流水(支付系统中的一个业务主键)
    private String rechargeBankSeq;//充值银行流水(银行返回给支付的一个流水号)
    private BigDecimal rechargeAmount;//充值金额
    private String rechargeChannel;//充值渠道
    private String rechargeBank;//充值银行
    private Date rechargeTime;//充值时间
    private Integer rechargeStatus;//充值状态
    
    private BigDecimal applyMax;//可充退金额
    
    private String rechargeBankOrder;//银行和支付都认识的一个银行订单号
    
    private Long rechargeAmountLong;//long型充值金额
    private Long applyMaxLong;//long型可充退金额
    private Integer refundType;
    
    private String rechargeBankName;
    
    private String depositTypeName;// 充值渠道。见fi.payment_channel.name。
    
    private boolean flag;//是否可退：可充退金额小于充值金额的时候不可退
    
    private String merchantOrderId;
    private String tradeOrderNo;
    private Long paymentAmount;
    private Integer paymentStatus;
    private Date paymentDate;
    
	/**
	 * @return the merchantOrderId
	 */
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	/**
	 * @param merchantOrderId the merchantOrderId to set
	 */
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	/**
	 * @return the tradeOrderNo
	 */
	public String getTradeOrderNo() {
		return tradeOrderNo;
	}
	/**
	 * @param tradeOrderNo the tradeOrderNo to set
	 */
	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	/**
	 * @return the paymentAmount
	 */
	public Long getPaymentAmount() {
		return paymentAmount;
	}
	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	/**
	 * @return the paymentStatus
	 */
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getDepositTypeName() {
		return depositTypeName;
	}
	public void setDepositTypeName(String depositTypeName) {
		this.depositTypeName = depositTypeName;
	}
	/**
	 * @return the rechargeBankName
	 */
	public String getRechargeBankName() {
		return rechargeBankName;
	}
	/**
	 * @param rechargeBankName the rechargeBankName to set
	 */
	public void setRechargeBankName(String rechargeBankName) {
		this.rechargeBankName = rechargeBankName;
	}
	public Integer getRefundType() {
		return refundType;
	}
	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	public Long getRechargeAmountLong() {
		this.rechargeAmountLong = rechargeAmount.longValue();
		return rechargeAmountLong;
	}
	public void setRechargeAmountLong(Long rechargeAmountLong) {
		this.rechargeAmountLong = rechargeAmountLong;
	}
	public Long getApplyMaxLong() {
		this.applyMaxLong = applyMax.longValue();
		return applyMaxLong;
	}
	public void setApplyMaxLong(Long applyMaxLong) {
		this.applyMaxLong = applyMaxLong;
	}
	public String getRechargeBankSeq() {
		return rechargeBankSeq;
	}
	public void setRechargeBankSeq(String rechargeBankSeq) {
		this.rechargeBankSeq = rechargeBankSeq;
	}
	public String getRechargeBankOrder() {
		return rechargeBankOrder;
	}
	public void setRechargeBankOrder(String rechargeBankOrder) {
		this.rechargeBankOrder = rechargeBankOrder;
	}
	public BigDecimal getApplyMax() {
		return applyMax;
	}
	public void setApplyMax(BigDecimal applyMax) {
		this.applyMax = applyMax;
	}
	public Long getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}
	public void setRechargeOrderSeq(Long rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}
	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getRechargeChannel() {
		return rechargeChannel;
	}
	public void setRechargeChannel(String rechargeChannel) {
		this.rechargeChannel = rechargeChannel;
	}
	public String getRechargeBank() {
		return rechargeBank;
	}
	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public Integer getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
}