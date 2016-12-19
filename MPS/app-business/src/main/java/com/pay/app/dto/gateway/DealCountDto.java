/**
 *  File: DealCountDto.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-16   terry_ma     Create
 *
 */
package com.pay.app.dto.gateway;

/**
 * 交易统计
 */
public class DealCountDto {

	/**
	 * 交易总金额
	 */
	private Long totalAmount;

	/**
	 * 交易总记录
	 */
	private Integer totalCount;

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
