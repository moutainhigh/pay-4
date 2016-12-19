package com.pay.poss.merchantmanager.dto;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantSearchDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-23 gungun_zhang Create
 */

public class MerchantSearchDto {

	/**
	 * 商户号
	 */
	private String merchantCode;

	private String memberCode;

	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户名称_拼音首字母
	 */
	private String merchantSearchKey;

	/**
	 * 商户状态
	 */
	private String merchantStatus;

	/**
	 * 商户审核状态
	 */
	private String merchantCheckStatus;

	/**
	 * 商户主网站
	 */
	private String merchantUrl;
	/**
	 * 审核通过未激活的逾期天数
	 */
	private String dayCount;
	/**
	 * 邮件中所发出的url激活链接是否有效1有效2.无效
	 */
	private String checkCodeStatus;

	private String email;

	private String pageEndRow;
	private String pageStartRow;

	public String getCheckCodeStatus() {
		return checkCodeStatus;
	}

	public void setCheckCodeStatus(String checkCodeStatus) {
		this.checkCodeStatus = checkCodeStatus;
	}

	public String getDayCount() {
		return dayCount;
	}

	public void setDayCount(String dayCount) {
		this.dayCount = dayCount;
	}

	public String getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(String pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public String getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(String pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public String getMerchantSearchKey() {
		return merchantSearchKey;
	}

	public void setMerchantSearchKey(String merchantSearchKey) {
		this.merchantSearchKey = merchantSearchKey;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public String getMerchantCheckStatus() {
		return merchantCheckStatus;
	}

	public void setMerchantCheckStatus(String merchantCheckStatus) {
		this.merchantCheckStatus = merchantCheckStatus;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
