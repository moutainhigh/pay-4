package com.pay.poss.report.dto;

import java.io.Serializable;

public class SumaryResuleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String createDate;
	
	/**
	 * 支付交易
	 */
	private Long payAmount;
	
	/**
	 * 支付笔数
	 */
	private Long payCount;
	
	/**
	 * 充值交易
	 */
	private Long depositAmount;
	/**
	 * 充值笔数
	 */
	private Long depositCount;
	
	/**
	 * 付款到帐户
	 */
	private Long pay2AccAmount;
	
	private Long pay2AccCount;
	
	/**
	 * 付款到银行
	 */
	private Long pay2BankAmount;
	
	private Long pay2BankCount;
	
	/**
	 * 提现
	 */
	private Long withDrowAmount;
	
	private Long withDrowCount;
	
	/**
	 * 充退
	 */
	private Long refundAmount;
	
	private Long refundCount;
	
	
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public Long getPayCount() {
		return payCount;
	}
	public void setPayCount(Long payCount) {
		this.payCount = payCount;
	}
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Long getDepositCount() {
		return depositCount;
	}
	public void setDepositCount(Long depositCount) {
		this.depositCount = depositCount;
	}
	public Long getPay2AccAmount() {
		return pay2AccAmount;
	}
	public void setPay2AccAmount(Long pay2AccAmount) {
		this.pay2AccAmount = pay2AccAmount;
	}
	public Long getPay2AccCount() {
		return pay2AccCount;
	}
	public void setPay2AccCount(Long pay2AccCount) {
		this.pay2AccCount = pay2AccCount;
	}
	public Long getPay2BankAmount() {
		return pay2BankAmount;
	}
	public void setPay2BankAmount(Long pay2BankAmount) {
		this.pay2BankAmount = pay2BankAmount;
	}
	public Long getPay2BankCount() {
		return pay2BankCount;
	}
	public void setPay2BankCount(Long pay2BankCount) {
		this.pay2BankCount = pay2BankCount;
	}
	public Long getWithDrowAmount() {
		return withDrowAmount;
	}
	public void setWithDrowAmount(Long withDrowAmount) {
		this.withDrowAmount = withDrowAmount;
	}
	public Long getWithDrowCount() {
		return withDrowCount;
	}
	public void setWithDrowCount(Long withDrowCount) {
		this.withDrowCount = withDrowCount;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Long getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(Long refundCount) {
		this.refundCount = refundCount;
	}
	

}
