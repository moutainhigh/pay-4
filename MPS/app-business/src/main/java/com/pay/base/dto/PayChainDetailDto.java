package com.pay.base.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * 支付链详情
 * 
 * @author DDR 
 * Date 2011-09-21
 */
public class PayChainDetailDto {

	private String payChainUrl;//支付链接
	private List<PayChainPaymentDto> payChainPaymentDtos; //付款人信息
	private PayChainPayInfo payChainPayInfo;
	private Integer recordsCount = 0; // 支付链条数
	private String recordsAmountSum = "0.00"; // 支付总金额
	private Integer recordsPaySum = 0; // 支付总笔数
	/**
	 * @return the payChainUrl
	 */
	public String getPayChainUrl() {
		return payChainUrl;
	}
	/**
	 * @param payChainUrl the payChainUrl to set
	 */
	public void setPayChainUrl(String payChainUrl) {
		this.payChainUrl = payChainUrl;
	}
	/**
	 * @return the payChainPaymentDto
	 */
	public List<PayChainPaymentDto> getPayChainPaymentDtos() {
		return payChainPaymentDtos == null ? Collections.EMPTY_LIST
				: payChainPaymentDtos;
	}
	/**
	 * @param payChainPaymentDto the payChainPaymentDto to set
	 */
	public void setPayChainPaymentDtos(List<PayChainPaymentDto> payChainPaymentDtos) {
		this.payChainPaymentDtos = payChainPaymentDtos;
	}
	/**
	 * @return the payChainPayInfo
	 */
	public PayChainPayInfo getPayChainPayInfo() {
		return payChainPayInfo;
	}
	/**
	 * @param payChainPayInfo the payChainPayInfo to set
	 */
	public void setPayChainPayInfo(PayChainPayInfo payChainPayInfo) {
		this.payChainPayInfo = payChainPayInfo;
	}
	/**
	 * @return the recordsCount
	 */
	public Integer getRecordsCount() {
		return recordsCount;
	}
	/**
	 * @param recordsCount the recordsCount to set
	 */
	public void setRecordsCount(Integer recordsCount) {
		this.recordsCount = recordsCount;
	}
	/**
	 * @return the recordsAmountSum
	 */
	public String getRecordsAmountSum() {
		return 	this.recordsAmountSum = new DecimalFormat("0.00")
		.format(new BigDecimal(recordsAmountSum));
	}
	/**
	 * @param recordsAmountSum the recordsAmountSum to set
	 */
	public void setRecordsAmountSum(String recordsAmountSum) {
		this.recordsAmountSum = recordsAmountSum;
	}
	/**
	 * @return the recordsPaySum
	 */
	public Integer getRecordsPaySum() {
		return recordsPaySum;
	}
	/**
	 * @param recordsPaySum the recordsPaySum to set
	 */
	public void setRecordsPaySum(Integer recordsPaySum) {
		this.recordsPaySum = recordsPaySum;
	}
	
	
	
}
