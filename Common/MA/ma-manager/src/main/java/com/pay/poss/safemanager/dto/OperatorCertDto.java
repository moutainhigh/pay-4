/**
 * 
 */
package com.pay.poss.safemanager.dto;

import java.util.List;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-22		DaiDeRong			Create
 */
public class OperatorCertDto {
	
	private Long memberCertId;
	private Long memberCode;
	private String memberName;
	private String loginName;
	private String operatorName;
	private int isAdmin = 0;
	private String bindMobile;
	///详细情况
	private String certCode;
	private Long operatorId;
	private String merchantCode;
	private Integer certStatus;
	
	private List<OperatorCertUseDto> operatorCertUseDtos;;
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
	/**
	 * @return the operatorCertUseDtos
	 */
	public List<OperatorCertUseDto> getOperatorCertUseDtos() {
		return operatorCertUseDtos;
	}
	/**
	 * @param operatorCertUseDtos the operatorCertUseDtos to set
	 */
	public void setOperatorCertUseDtos(List<OperatorCertUseDto> operatorCertUseDtos) {
		this.operatorCertUseDtos = operatorCertUseDtos;
	}
	/**
	 * @return the certStatus
	 */
	public Integer getCertStatus() {
		return certStatus;
	}
	/**
	 * @param certStatus the certStatus to set
	 */
	public void setCertStatus(Integer certStatus) {
		this.certStatus = certStatus;
	}
	/**
	 * @return the memberCertId
	 */
	public Long getMemberCertId() {
		return memberCertId;
	}
	/**
	 * @param memberCertId the memberCertId to set
	 */
	public void setMemberCertId(Long memberCertId) {
		this.memberCertId = memberCertId;
	}
	
	
	
	
	
	

}
