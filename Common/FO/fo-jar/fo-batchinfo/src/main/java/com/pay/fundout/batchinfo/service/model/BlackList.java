/**
 *  <p>File: BatchFileRecord.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.batchinfo.service.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p>黑名单实体</p>
 * @author limeng
 * @since 2012-6-12
 * @see 
 */
public class BlackList extends BaseObject {
	
	/*
	 * 黑名单ID
	 */
	private String id;
	
	/*
	 * 黑名单类型，1表示个人黑名单，2表示机构黑名单
	 */
	private String type;
	
	/*
	 * 成员单位编码
	 */
	private String memberUnitCode;
	
	/*
	 * 身份证/法人身份证
	 */
	private String identityCode;
	
	/*
	 * 姓名/法人代表姓名
	 */
	private String name;
	
	/*
	 * 发生地区
	 */
	private String occuredArea;
	
	/*
	 * 录黑途径
	 */
	private String way;
	
	/*
	 * 黑名单事件
	 */
	private String event;
	
	/*
	 * 黑名单事件备注
	 */
	private String remark;
	
	/*
	 * 手机号码
	 */
	private String mobile;
	
	/*
	 * 固定电话号码
	 */
	private String telephone;
	
	/*
	 * 银行卡号
	 */
	private String bankCode;
	
	/*
	 * 开户行
	 */
	private String bankName;
	
	/*
	 * IP地址
	 */
	private String ip;
	
	/*
	 * MAC地址
	 */
	private String mac;
	
	/*
	 * 邮箱
	 */
	private String email;
	
	/*
	 * 地址
	 */
	private String address;
	
	/*
	 * ICP编号
	 */
	private String icpCode;
	
	/*
	 * ICP备案人
	 */
	private String icpMember;
	
	/*
	 * URL地址
	 */
	private String urlAddress;
	
	/*
	 * URL跳转地址
	 */
	private String urlBranchAddress;
	
	/*
	 * 支付人姓名
	 */
	private String payerName;
	
	/*
	 * 标记时间
	 */
	private Date markTime;
	
	/*
	 * 标记时间
	 */
	private String markTimeStr;
	
	/*
	 * 数据状态，1：黑名单，2：无效黑名单
	 */
	private String status;
	
	/*
	 * 操作人
	 */
	private String operator;
	
	/*
	 * 机构名称
	 */
	private String orgName;
	
	/*
	 * 营业执照编号
	 */
	private String businessCode;
	
	/*
	 * 组织机构代码
	 */
	private String orgCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemberUnitCode() {
		return memberUnitCode;
	}

	public void setMemberUnitCode(String memberUnitCode) {
		this.memberUnitCode = memberUnitCode;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccuredArea() {
		return occuredArea;
	}

	public void setOccuredArea(String occuredArea) {
		this.occuredArea = occuredArea;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIcpCode() {
		return icpCode;
	}

	public void setIcpCode(String icpCode) {
		this.icpCode = icpCode;
	}

	public String getIcpMember() {
		return icpMember;
	}

	public void setIcpMember(String icpMember) {
		this.icpMember = icpMember;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public String getUrlBranchAddress() {
		return urlBranchAddress;
	}

	public void setUrlBranchAddress(String urlBranchAddress) {
		this.urlBranchAddress = urlBranchAddress;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public Date getMarkTime() {
		return markTime;
	}

	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMarkTimeStr() {
		return markTimeStr;
	}

	public void setMarkTimeStr(String markTimeStr) {
		this.markTimeStr = markTimeStr;
	}
	
}