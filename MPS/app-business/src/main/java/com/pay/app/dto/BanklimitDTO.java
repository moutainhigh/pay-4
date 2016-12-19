/**
 *  File: BanklimitDTO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dto;

import com.pay.inf.dto.MutableDto;

/**
 * 
 */
public class BanklimitDTO implements MutableDto{

	private Long id;
	private Integer bankType;
	private Integer cardType;
	private String bankCode;
	private Long singleLimit;
	private Long dayLimit;
	private String description;
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Long getSingleLimit() {
		return singleLimit;
	}
	public void setSingleLimit(Long singleLimit) {
		this.singleLimit = singleLimit;
	}
	public Long getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(Long dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}