package com.pay.poss.memberrelation.model;

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
public class MemberRelation {
	
	private Long 		memberRelationId;		//
	private Long 		fatherMemberCode;		//父会员membercode		
	private Long 		fatherOperatorId;		//交易商公司名称
	private String 		sonZhName;				//交易商公司名称
	private String 		sonEnName;				//交易商公司简称
	private Long 		sonMemberCode;			//子会员membercode
	private Long 		sonOperatorId;			//子会员操作员ID
	private Long 		sonMerchantCode;		//子会员商户号
	private Long 		sonUsteelId;			//子会员你的钢网ID
	private String 		sonUsteelName;			//子会员你的钢网名称
	private String 		relationType;			//关系类型
	private Integer 	status;					//状态 1为正常
	private String		value1;					//
	private String 		value2;					//
	private Date 		createDate;				//
	private Date 		updateData;				//
	private String 		loginName;
	private String 		mobile;
	private String 		name;
	
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getSonZhName() {
		return sonZhName;
	}
	public void setSonZhName(String sonZhName) {
		this.sonZhName = sonZhName;
	}
	public String getSonEnName() {
		return sonEnName;
	}
	public void setSonEnName(String sonEnName) {
		this.sonEnName = sonEnName;
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
	public Long getSonUsteelId() {
		return sonUsteelId;
	}
	public void setSonUsteelId(Long sonUsteelId) {
		this.sonUsteelId = sonUsteelId;
	}
	public String getSonUsteelName() {
		return sonUsteelName;
	}
	public void setSonUsteelName(String sonUsteelName) {
		this.sonUsteelName = sonUsteelName;
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
	public Date getUpdateData() {
		return updateData;
	}
	public void setUpdateData(Date updateData) {
		this.updateData = updateData;
	}

}
