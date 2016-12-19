/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午05:57:15 2010-11-11
 */
package com.pay.base.model;

import com.pay.app.model.Model;

/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午05:57:15 & 2010-11-11
 */

public class MemberAcctTemplate implements Model {

	private Long id;
	private String subjectCode;
	private Integer acctType;
	private Integer memberType;

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrimaryKey(Long id) {
		setId(id);
	}

}
