/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * MemberQuickBankAcct.java
 */
package com.pay.acc.member.dao.model;

import java.io.Serializable;

import java.util.Date;

/**
 * [文件名称]<br>
 * MemberQuickBankAcct.java<br>
 * <br>
 * [文件描述]<br>
 * 会员快捷银行卡绑定表 pojo bean<br>
 * <br>
 * [修改记录]<br>
 * 2014-11-15 12:28:31 创建 陶俊代码生成器<br>
 * 
 * @author 陶俊代码生成器
 * @version 1.00
 */
public class MemberQuickBankAcct implements Serializable {
	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * null
	 */
	private Long id;
	/**
	 * null
	 */
	private String bankName;
	/**
	 * null
	 */
	private String bankId;
	/**
	 * null
	 */
	private Long memberCode;
	/**
	 * null
	 */
	private Date createDate;
	/**
	 * null
	 */
	private String name;
	/**
	 * null
	 */
	private String bankCardNo;
	/**
	 * 1-借记卡，2-贷记卡，3-存折
	 */
	private String bankCardType;
	/**
	 * null
	 */
	private String bankMobile;
	/**
	 * 0-创建，1-绑定申请，2-已绑定，3-解绑申请，4-已解除
	 */
	private Boolean bindingFlag;
	/**
	 * null
	 */
	private Date bindingTime;
	/**
	 * null
	 */
	private Date unbindingTime;
	/**
	 * null
	 */
	private Date applyBindingTime;
	/**
	 * null
	 */
	private Date applyUnbindingTime;
	/**
	 * null
	 */
	private String rsvdtext1;
	/**
	 * null
	 */
	private String rsvdtext2;
	/**
	 * null
	 */
	private String rsvdtext3;

	/**
	 * 设置 :null
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取 :null
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置 :null
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 获取 :null
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 设置 :null
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * 获取 :null
	 */
	public String getBankId() {
		return this.bankId;
	}

	/**
	 * 设置 :null
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * 获取 :null
	 */
	public Long getMemberCode() {
		return this.memberCode;
	}

	/**
	 * 设置 :null
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取 :null
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置 :null
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 :null
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 :null
	 */
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	/**
	 * 获取 :null
	 */
	public String getBankCardNo() {
		return this.bankCardNo;
	}

	/**
	 * 设置 :1-借记卡，2-贷记卡，3-存折
	 */
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	/**
	 * 获取 :1-借记卡，2-贷记卡，3-存折
	 */
	public String getBankCardType() {
		return this.bankCardType;
	}

	/**
	 * 设置 :null
	 */
	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	/**
	 * 获取 :null
	 */
	public String getBankMobile() {
		return this.bankMobile;
	}

	/**
	 * 设置 :0-创建，1-绑定申请，2-已绑定，3-解绑申请，4-已解除
	 */
	public void setBindingFlag(Boolean bindingFlag) {
		this.bindingFlag = bindingFlag;
	}

	/**
	 * 获取 :0-创建，1-绑定申请，2-已绑定，3-解绑申请，4-已解除
	 */
	public Boolean isBindingFlag() {
		return this.bindingFlag;
	}

	public Date getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}

	public Boolean getBindingFlag() {
		return bindingFlag;
	}

	/**
	 * 设置 :null
	 */
	public void setUnbindingTime(Date unbindingTime) {
		this.unbindingTime = unbindingTime;
	}

	/**
	 * 获取 :null
	 */
	public Date getUnbindingTime() {
		return this.unbindingTime;
	}

	/**
	 * 设置 :null
	 */
	public void setApplyBindingTime(Date applyBindingTime) {
		this.applyBindingTime = applyBindingTime;
	}

	/**
	 * 获取 :null
	 */
	public Date getApplyBindingTime() {
		return this.applyBindingTime;
	}

	/**
	 * 设置 :null
	 */
	public void setApplyUnbindingTime(Date applyUnbindingTime) {
		this.applyUnbindingTime = applyUnbindingTime;
	}

	/**
	 * 获取 :null
	 */
	public Date getApplyUnbindingTime() {
		return this.applyUnbindingTime;
	}

	/**
	 * 设置 :null
	 */
	public void setRsvdtext1(String rsvdtext1) {
		this.rsvdtext1 = rsvdtext1;
	}

	/**
	 * 获取 :null
	 */
	public String getRsvdtext1() {
		return this.rsvdtext1;
	}

	/**
	 * 设置 :null
	 */
	public void setRsvdtext2(String rsvdtext2) {
		this.rsvdtext2 = rsvdtext2;
	}

	/**
	 * 获取 :null
	 */
	public String getRsvdtext2() {
		return this.rsvdtext2;
	}

	/**
	 * 设置 :null
	 */
	public void setRsvdtext3(String rsvdtext3) {
		this.rsvdtext3 = rsvdtext3;
	}

	/**
	 * 获取 :null
	 */
	public String getRsvdtext3() {
		return this.rsvdtext3;
	}

}
