package com.pay.fundout.withdraw.dto.autorisk;

import java.util.Date;

public class MemBerDto {

	/*
	 * 会员号
	 */
	private Long memberCode;
	
	/*
	 * 会员名称
	 */
	private String memberName;
	
	/*
	 * 建立时间
	 */
	private Date createDate;
	
	/*
	 * 风险等级，针对商户
	 */
	private String riskLeveCode;
	
	/*
	 * 行业代码，页面上的MCC，针对商户
	 */
	private String inIndustry;
	
	/*
	 *	可用余额
	 */
	private double balance;
	
	/*
	 * 企业网站，针对商户
	 */
	private String website;
	
	/*
	 * 邮箱
	 */
	private String email;
	
	/*
	 * 地址
	 */
	private String address;
	
	/*
	 * 联系电话
	 */
	private String mobile;
	
	/*
	 * 签约人，只针对企业用户
	 */
	private String signName;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	public void setRiskLeveCode(String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	public String getInIndustry() {
		return inIndustry;
	}

	public void setInIndustry(String inIndustry) {
		this.inIndustry = inIndustry;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
	
}
