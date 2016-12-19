/**
 *  File: AdvertisementDTO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dto;

import java.sql.Timestamp;

import com.pay.inf.dto.MutableDto;

/**
 * 
 */
public class AdvertisementDTO implements MutableDto {

	private Long id;
	private Integer available;
	private Integer advtype;
	private Integer sort;
	private String title;
	private Integer targets;
	private String parameters;
	private String code;
	private Timestamp startTime;
	private Timestamp endTime;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public Integer getAdvtype() {
		return advtype;
	}
	public void setAdvtype(Integer advtype) {
		this.advtype = advtype;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTargets() {
		return targets;
	}
	public void setTargets(Integer targets) {
		this.targets = targets;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
}