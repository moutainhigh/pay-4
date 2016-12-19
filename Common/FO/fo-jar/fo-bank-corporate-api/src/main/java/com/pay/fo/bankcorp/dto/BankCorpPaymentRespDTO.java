package com.pay.fo.bankcorp.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 银企直连响应对象，银行前置主动请求核心系统的参数对象
 * @author new
 *
 */
public class BankCorpPaymentRespDTO extends RespDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1150673423849482311L;

	/**
	 * 交易订单号
	 */
	private Long tradeOrderId;
	
	/**
	 * 银行处理状态
	 */
	private Integer orderStatus;
	/**
	 * 失败的原因
	 */
	private String failedReason;
	/**
	 * 银行订单号/银行流水号
	 */
	private String bankOrderId;
	/**
	 * 响应日期
	 */
	private Date updateDate;
	
	
	public Long getTradeOrderId() {
		return tradeOrderId;
	}
	public void setTradeOrderId(Long tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getFailedReason() {
		return failedReason;
	}
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	public String getBankOrderId() {
		return bankOrderId;
	}
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
