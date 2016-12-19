package com.pay.acc.member.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-12-27 下午05:27:55
 */
public class BlackWhiteList implements Serializable {

	private static final long serialVersionUID = 1L;
	// ID
	private Long id;
	// 会员号
	private String memberCode;
	//业务类型ID
	private Long businessTypeId;
	//名单类型（白名单|黑名单）
	private int listType;
	//状态(1有效0无效)
	private int status;
	//创建时间
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Long getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public int getListType() {
		return listType;
	}
	public void setListType(int listType) {
		this.listType = listType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
