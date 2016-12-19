/**
 *  <p>File: BatchFileRecord.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.batchinfo.service.model;

import com.pay.inf.model.BaseObject;

/**
 * <p>黑名单查询实体</p>
 * @author limeng
 * @since 2012-6-12
 * @see 
 */
public class BlackListDTO extends BaseObject {
	
	/*
	 * 用户唯一识别码，用于填写业务账号
	 */
	private String sbm = "";
	
	/*
	 * 黑名单类型，1表示个人黑名单，2表示机构黑名单
	 */
	private String type = "";
	
	/*
	 * 身份证/法人身份证
	 */
	private String identityCode = "";
	
	/*
	 * 姓名/法人代表姓名
	 */
	private String name = "";
	
	/*
	 * 发生地，6位行政区划编码
	 */
	private String occuredArea = "";
	
	/*
	 * 业务类型
	 */
	private String businessType = "";
	
	/*
	 * 手机号码
	 */
	private String mobile = "";
	
	/*
	 * 银行卡号
	 */
	private String bankCode = "";
	
	/*
	 * 邮箱
	 */
	private String email = "";
	
	/*
	 * URL地址
	 */
	private String urlAddress = "";
	
	/*
	 * URL跳转地址
	 */
	private String urlBranchAddress = "";
	
	/*
	 * 机构名称
	 */
	private String orgName = "";
	
	/*
	 * 营业执照编号
	 */
	private String businessCode = "";

	public String getSbm() {
		return sbm;
	}

	public void setSbm(String sbm) {
		this.sbm = sbm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
}