package com.pay.poss.merchantmanager.formbean;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantSearchFormBean.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-23 gungun_zhang Create
 */

public class MerchantSearchFormBean {
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

	private String pageNo;
	private String pageSize;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName.trim();
	}

	public String getMerchantSearchKey() {
		return merchantSearchKey;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode.trim();
	}

	public void setMerchantSearchKey(String merchantSearchKey) {
		this.merchantSearchKey = merchantSearchKey.trim();
	}

	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus.trim();
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
		this.merchantUrl = merchantUrl.trim();
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

}
