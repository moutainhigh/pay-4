/**
 * 
 */
package com.pay.base.fi.model;

import java.sql.Timestamp;

/**
 * 
 * @author PengJiangbo
 *ORDERID
MEMBER_CODE
CREATE_TIME
CP_TYPE
 */
public class ChargeBackMemberRelation {

	private Long orderId ;
	
	private Long memberCode ;
	
	private Timestamp createTime ;
	
	private String cpType ;

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the cpType
	 */
	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}
	
	
	
}
