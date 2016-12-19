package com.pay.poss.platformmembers.model;

import java.util.Date;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		MemberRelation.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date			Author			cf
 * 2011-9-21	gungun_zhang    Create
 */
public class PlatformMembers {
	
	private Long 		id;		//
	private Long 		fatherMemberCode;		//父会员membercode		
	private Long 		fatherOperatorId;		//交易商公司名称
	private Long 		sonMemberCode;			//子会员membercode
	private Long 		sonOperatorId;			//子会员操作员ID
	private Integer 	status;					//状态 1为正常
	private Date 		createDate;				//
	private Date 		updateDate;				//
	
	private String 		fatherZhName;
	private String 		sonZhName;
	private String 		fatherOperator;
	private String 		sonOperator;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getFatherZhName() {
		return fatherZhName;
	}
	public void setFatherZhName(String fatherZhName) {
		this.fatherZhName = fatherZhName;
	}
	public String getSonZhName() {
		return sonZhName;
	}
	public void setSonZhName(String sonZhName) {
		this.sonZhName = sonZhName;
	}
	public String getFatherOperator() {
		return fatherOperator;
	}
	public void setFatherOperator(String fatherOperator) {
		this.fatherOperator = fatherOperator;
	}
	public String getSonOperator() {
		return sonOperator;
	}
	public void setSonOperator(String sonOperator) {
		this.sonOperator = sonOperator;
	}
}
