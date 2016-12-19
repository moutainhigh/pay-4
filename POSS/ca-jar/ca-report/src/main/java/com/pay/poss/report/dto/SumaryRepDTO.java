package com.pay.poss.report.dto;

import java.io.Serializable;

public class SumaryRepDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String createDate;
	
	/**
	 * 支付交易
	 */
	private Long amount;
	
	/**
	 * 支付笔数
	 */
	private Long count;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	

}
