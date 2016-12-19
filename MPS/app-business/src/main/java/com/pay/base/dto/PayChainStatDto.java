package com.pay.base.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * 用于显示支付链统计列表的dto
 * 
 * @author DDR Date 2011-09-21
 */
public class PayChainStatDto {

	private List<PayChainStatRecordDto> payChainStatRecordDtos;
	private Integer recordsCount = 0; // 支付链条数
	private String recordsAmountSum = "0.00"; // 支付总金额
	private Integer recordsPaySum = 0; // 支付笔数

	/**
	 * 如果没有则返回空的list,因此不会有null的异常
	 * @return the payChainStatRecordDtos
	 */
	public List<PayChainStatRecordDto> getPayChainStatRecordDtos() {
		return payChainStatRecordDtos == null ? Collections.EMPTY_LIST
				: payChainStatRecordDtos;
	}

	/**
	 * @param payChainStatRecordDtos
	 *            the payChainStatRecordDtos to set
	 */
	public void setPayChainStatRecordDtos(
			List<PayChainStatRecordDto> payChainStatRecordDtos) {
		this.payChainStatRecordDtos = payChainStatRecordDtos;
	}

	/**
	 * @return the recordsCount
	 */
	public int getRecordsCount() {
		return recordsCount;
	}

	/**
	 * @param recordsCount
	 *            the recordsCount to set
	 */
	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	/**
	 * @return the recordsAmountSum
	 */
	public String getRecordsAmountSum() {
		return recordsAmountSum;
	}

	/**
	 * @param recordsAmountSum
	 *            the recordsAmountSum to set
	 */
	public void setRecordsAmountSum(String recordsAmountSum) {
		this.recordsAmountSum = new DecimalFormat("0.00")
				.format(new BigDecimal(recordsAmountSum));
	}

	/**
	 * @return the recordsPaySum
	 */
	public int getRecordsPaySum() {
		return recordsPaySum;
	}

	/**
	 * @param recordsPaySum
	 *            the recordsPaySum to set
	 */
	public void setRecordsPaySum(int recordsPaySum) {
		this.recordsPaySum = recordsPaySum;
	}

}
