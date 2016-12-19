/**
 * 
 */
package com.pay.acc.service.member.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberBaseInfoBO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 会员帐号
	 */
	private Long memberCode;
	/**
	 * 服务级别代码
	 */
	private Integer serviceLevelCode;
	
	/**
	 * 1个人会员，2企业会员
	 */
	private Integer memberType;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 问候语
	 */
	private String greeting;
	/**
	 * 0为创建，1正常状态（激活成功），2冻结状态，3.未激活，4删除状态
	 */
	private Integer status;
	/**
	 * 安全问题
	 */
	private String securityQuestion;
	/**
	 * 安全问题答案
	 */
	private String securityAnswer;
	/**
	 * 创建时间
	 */
	private Date creationDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * sso userid
	 */
	private String parnterUserid;
	/**
	 * 登陆类型
	 */
	private String regType;
	
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 简称
	 */
	private String shortName;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}



}
