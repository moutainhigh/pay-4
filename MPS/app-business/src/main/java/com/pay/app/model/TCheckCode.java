/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.model;

import java.util.Date;

/**
 * 会员激活信息<br>
 * 对应ACC库
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午02:02:21
 * 
 */
public class TCheckCode {
	
	private long id;
	private String checkCode;
	private int  status;
	private Date createTime;
	private long memberCode;
	private String origin;
	private String email;
	private String phone;
	private Date updateTime;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
