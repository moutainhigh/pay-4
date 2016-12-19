package com.pay.poss.paychainmanager.dto;



/**
 * 用于显示支付链统计列表的dto
 * 
 * @author DDR Date 2011-09-21
 */
public class PayChainStatDto {

	private String recordsAmountSum = "0.00"; // 支付总金额
	private Integer recordsPaySum = 0; // 支付笔数
	
	public String getRecordsAmountSum() {
		return recordsAmountSum;
	}
	public void setRecordsAmountSum(String recordsAmountSum) {
		this.recordsAmountSum = recordsAmountSum;
	}
	public Integer getRecordsPaySum() {
		return recordsPaySum;
	}
	public void setRecordsPaySum(Integer recordsPaySum) {
		this.recordsPaySum = recordsPaySum;
	}




}
