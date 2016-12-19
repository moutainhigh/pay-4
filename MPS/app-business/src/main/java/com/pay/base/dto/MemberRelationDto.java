/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

/**
 * 会员父子关系表 2011-06-24
 * 
 * @author 戴德荣
 */
public class MemberRelationDto implements MutableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long memberRelationId; // NUMBER(11) not null,
	private Long fatherMemberCode; // NUMBER(11) not null,
	private Long fatherOperatorId; // NUMBER(11),
	private String sonZhName;// VARCHAR2(64) 交易商公司名称
	private String sonEnName; // VARCHAR2(30) 交易商公司名称
	private Long sonMemberCode; // NUMBER(11) not null,
	private Long sonOperatorId; // NUMBER(11),
	private Long sonMerchantCode; // NUMBER(15) not null,
	private Long sonUsteelId; // NUMBER(11)子会员你的钢网ID
	private String sonUsteelName; // VARCHAR2(30)子会员你的钢网名称
	private String relationType; // VARCHAR2(30),
	private Integer status; // NUMBER(1) default 1 not null,
	private String value1; // VARCHAR2(256),
	private String value2; // VARCHAR2(256),
	private Date createDate; // DATE default sysdate not null,
	private Date updateDate; // DATE default sysdate not null

	public Long getMemberRelationId() {
		return memberRelationId;
	}

	public void setMemberRelationId(Long memberRelationId) {
		this.memberRelationId = memberRelationId;
	}

	public Long getFatherMemberCode() {
		return fatherMemberCode;
	}

	public void setFatherMemberCode(Long fatherMemberCode) {
		this.fatherMemberCode = fatherMemberCode;
	}

	public Long getFatherOperatorId() {
		return fatherOperatorId;
	}

	public void setFatherOperatorId(Long fatherOperatorId) {
		this.fatherOperatorId = fatherOperatorId;
	}

	public Long getSonMemberCode() {
		return sonMemberCode;
	}

	public void setSonMemberCode(Long sonMemberCode) {
		this.sonMemberCode = sonMemberCode;
	}

	public Long getSonOperatorId() {
		return sonOperatorId;
	}

	public void setSonOperatorId(Long sonOperatorId) {
		this.sonOperatorId = sonOperatorId;
	}

	public Long getSonMerchantCode() {
		return sonMerchantCode;
	}

	public void setSonMerchantCode(Long sonMerchantCode) {
		this.sonMerchantCode = sonMerchantCode;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the sonZhName
	 */
	public String getSonZhName() {
		return sonZhName;
	}

	/**
	 * @param sonZhName
	 *            the sonZhName to set
	 */
	public void setSonZhName(String sonZhName) {
		this.sonZhName = sonZhName;
	}

	/**
	 * @return the sonEnName
	 */
	public String getSonEnName() {
		return sonEnName;
	}

	/**
	 * @param sonEnName
	 *            the sonEnName to set
	 */
	public void setSonEnName(String sonEnName) {
		this.sonEnName = sonEnName;
	}

	/**
	 * @return the sonUsteelId
	 */
	public Long getSonUsteelId() {
		return sonUsteelId;
	}

	/**
	 * @param sonUsteelId
	 *            the sonUsteelId to set
	 */
	public void setSonUsteelId(Long sonUsteelId) {
		this.sonUsteelId = sonUsteelId;
	}

	/**
	 * @return the sonUsteelName
	 */
	public String getSonUsteelName() {
		return sonUsteelName;
	}

	/**
	 * @param sonUsteelName
	 *            the sonUsteelName to set
	 */
	public void setSonUsteelName(String sonUsteelName) {
		this.sonUsteelName = sonUsteelName;
	}

}
