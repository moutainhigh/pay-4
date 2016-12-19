package com.pay.poss.refund.model;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

//充退批次信息    概要信息
public class RefundBatchInfoDTO extends BaseObject {

	private static final long serialVersionUID = 3820201738454632580L;
	private String bankCode; // 银行编码
	private String batchNum; // 批次号
	private BigDecimal totalAmount; // 总金额
	private int totalCount;// 总笔数

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
