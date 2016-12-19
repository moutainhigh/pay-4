/**
 * 
 */
package com.pay.poss.safemanager.dto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorBindDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-22		DaiDeRong			Create
 */
public class OperatorBindDto {
	
	private Long memberCode;
	private String memberName;
	private String loginName;
	private String operatorName;
	private int isAdmin = 0;
	private String bindMobile;
	
	///详细情况
	private String operatorFullName;
	private String certCode;
	private String department;
	private String operatorEmail;
	private String createDate;
	private int  	status;
	private Long operatorId;
	private String operatorPhone;
	private String merchantCode;
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the isAdmin
	 */
	public int getIsAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * @return the bindMobile
	 */
	public String getBindMobile() {
		return bindMobile;
	}
	/**
	 * @param bindMobile the bindMobile to set
	 */
	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
	/**
	 * @return the operatorFullName
	 */
	public String getOperatorFullName() {
		return operatorFullName;
	}
	/**
	 * @param operatorFullName the operatorFullName to set
	 */
	public void setOperatorFullName(String operatorFullName) {
		this.operatorFullName = operatorFullName;
	}
	/**
	 * @return the certCode
	 */
	public String getCertCode() {
		return certCode;
	}
	/**
	 * @param certCode the certCode to set
	 */
	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the operatorEmail
	 */
	public String getOperatorEmail() {
		return operatorEmail;
	}
	/**
	 * @param operatorEmail the operatorEmail to set
	 */
	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}
	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	/**
	 * @return the operatorPhone
	 */
	public String getOperatorPhone() {
		return operatorPhone;
	}
	/**
	 * @param operatorPhone the operatorPhone to set
	 */
	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}
	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	
	public String getStatusMsg(){
		//0创建，1正常，2关闭，3删除
		
	switch (status) {
		case 0:
			return "创建";
		case 1:
			return "正常";
		case 2:
			return "关闭";
		case 3:
			return 	"删除";
		default:
			break;
		}
		return "";
	}
	
	
	
	
	
	

}
