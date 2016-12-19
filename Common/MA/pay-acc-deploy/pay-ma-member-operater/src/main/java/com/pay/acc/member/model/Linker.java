/**
 *  File: Linker.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.acc.member.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 */
public class Linker  implements Serializable {

	private Long id;
	private String memberCode;
	private Long linkerId;
	private String linkerName;
	private String description;
	private Integer groupId;
	private Timestamp joinDate;
	private Integer status;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLinkerId() {
		return linkerId;
	}

	public void setLinkerId(Long linkerId) {
		this.linkerId = linkerId;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Timestamp getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	@Override
	public String toString() {
		return "Linker [description=" + description + ", groupId=" + groupId + ", id=" + id + ", joinDate=" + joinDate + ", linkerId="
				+ linkerId + ", linkerName=" + linkerName + ", memberCode=" + memberCode + ", remark=" + remark + ", status=" + status
				+ "]";
	}
	
	
}
