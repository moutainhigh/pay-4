/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.acc.service.cert.dto;

/**
 * @author fjl
 * @date 2011-11-18
 */
public class MemberCertLogDto {

	private Long memberCode;// 会员号
	private Long operatorId;// 操作员ID
	private String userDn;// 用户DN
	private String refNo;// 证书参考号
	private String authCode;// 证书授权码

	private Integer step;// 1 申请 2两码 3制证 4注销 5备份 6导入
	private Long serialNo;// 流水号
	private Integer status;// 状态 1 成功 0失败

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getUserDn() {
		return userDn;
	}

	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Integer getStep() {
		return step;
	}

	
	/**
	 * @see com.pay.acc.cert.enums.StepEnum
	 * @param step
	 */
	public void setStep(Integer step) {
		this.step = step;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getStatus() {
		return status;
	}

	/**
	 * @see com.pay.acc.cert.model.StatusEnum
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
