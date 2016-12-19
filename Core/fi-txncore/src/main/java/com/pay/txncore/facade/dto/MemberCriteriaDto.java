/**
 *  File: MemberQueryCriteria.java
 *  Description:
 *  Copyright 2006-2011 HNA Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-7-15   terry_ma     Create
 *
 */
package com.pay.txncore.facade.dto;

import java.util.Date;

/**
 * 会员相关信息
 */
public class MemberCriteriaDto implements java.io.Serializable {

	private Long memberCode;
	private Integer serviceLevelCode;
	private Long operatorId;

	/**
	 * 1是个人,2是企业
	 */
	private Integer memberType;
	private String name;
	private String greeting;
	private Integer status;
	private String securityQuestion;
	private String securityAnswer;
	private Date creationDate;
	private Date updateDate;
	private String parnterUserid;

	private String regType;

	private String loginName;

	private String salutatory;

	/**
	 * 简称
	 */
	private String shortName;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the serviceLevelCode
	 */
	public Integer getServiceLevelCode() {
		return serviceLevelCode;
	}

	/**
	 * @param serviceLevelCode
	 *            the serviceLevelCode to set
	 */
	public void setServiceLevelCode(Integer serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	/**
	 * @return the memberType
	 */
	public Integer getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the greeting
	 */
	public String getGreeting() {
		return greeting;
	}

	/**
	 * @param greeting
	 *            the greeting to set
	 */
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion
	 *            the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public String getSalutatory() {
		return salutatory;
	}

	public void setSalutatory(String salutatory) {
		this.salutatory = salutatory;
	}

	/**
	 * @param securityAnswer
	 *            the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the parnterUserid
	 */
	public String getParnterUserid() {
		return parnterUserid;
	}

	/**
	 * @param parnterUserid
	 *            the parnterUserid to set
	 */
	public void setParnterUserid(String parnterUserid) {
		this.parnterUserid = parnterUserid;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		return "MemberCriteriaDto [memberCode=" + memberCode
				+ ", serviceLevelCode=" + serviceLevelCode + ", memberType="
				+ memberType + ", name=" + name + ", greeting=" + greeting
				+ ", status=" + status + ", securityQuestion="
				+ securityQuestion + ", securityAnswer=" + securityAnswer
				+ ", creationDate=" + creationDate + ", updateDate="
				+ updateDate + ", parnterUserid=" + parnterUserid
				+ ", regType=" + regType + ", loginName=" + loginName
				+ ", salutatory=" + salutatory + "]";
	}

}
