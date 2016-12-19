package com.pay.poss.platformmembers.dto;

import java.io.Serializable;
import java.util.Date;
/**   
* @Title: RelationDataDto.java 
* @Package com.pay.poss.memberrelation.dto 
* @Description: 关联名单数据传输对象
* @author cf
* @date 2011-9-22 下午12:50:03 
* @version V1.0   
*/

public class PlatformMembersDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7158642451824060563L;

	private Long 		id;		//
	private Long 		fatherMemberCode;		//父会员membercode		
	private Long 		fatherOperatorId;		//交易商公司名称
	private Long 		sonMemberCode;			//子会员membercode
	private Long 		sonOperatorId;			//子会员操作员ID
	private Integer 	status;					//状态 1为正常
	private String 		createDate;				//
	private String 		updateDate;				//
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
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
