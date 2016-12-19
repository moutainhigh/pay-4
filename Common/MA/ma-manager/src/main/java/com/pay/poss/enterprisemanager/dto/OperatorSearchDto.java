package com.pay.poss.enterprisemanager.dto;

/**
 * @Description
 * @project ma-manager
 * @file OperatorSearchDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0
 * @Date 2011-07-25
 */
public class OperatorSearchDto {
	/**
	 * 会员id
	 */
	private String memberCode;
	/**
	 * 商户号
	 */
	private String merchantCode;

	/**
	 * 登录名
	 */
	private String loginName;

	private int pageEndRow;
	private int pageStartRow;

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode
	 *            the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the pageEndRow
	 */
	public int getPageEndRow() {
		return pageEndRow;
	}

	/**
	 * @param pageEndRow
	 *            the pageEndRow to set
	 */
	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	/**
	 * @return the pageStartRow
	 */
	public int getPageStartRow() {
		return pageStartRow;
	}

	/**
	 * @param pageStartRow
	 *            the pageStartRow to set
	 */
	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

}
