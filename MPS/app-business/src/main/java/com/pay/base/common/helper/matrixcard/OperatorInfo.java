package com.pay.base.common.helper.matrixcard;

import java.io.Serializable;

/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class OperatorInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4786396090188787326L;
	
	private Long memberCode;
	private Long u_id;
	private String ip;
	private String sessionId;
	private String operator;
	
	//会员权限级别
	private int securityLevel;
	
	public Long getMemberCode() {
		return memberCode;
	}
	
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	public Long getU_id() {
		return u_id;
	}
	
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
	}
}
