/**
 *  File: AdvertisementDTO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

/**
 * 
 */
public class AdvertisementDTO implements MutableDto {

	private Long id;
    private Integer available;
    /**
     * 广告类型
     */
    private Integer advtype;
    private Integer sort;
    private String title;
    /**
     * 位置
     */
    private Integer targets;
    private String parameters;
    private String code;
    private Date starttime;
    private Date endtime;
    private String remark;
    private String imgpath;
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
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	
}
