package com.pay.acc.service.member.dto;

import java.io.Serializable;

public class ApplyCertRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5961615820912932150L;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 证件类型
	 */
	private String idCardTypeNum;
	/**
	 * 证件号码
	 */
	private String idCardNo;
	/**
	 * 使用地点
	 */
	private String usePlace;
	
	
	/**
	 * Mac地址
	 */
	private String machineIdentifier;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCardTypeNum() {
		return idCardTypeNum;
	}
	public void setIdCardTypeNum(String idCardTypeNum) {
		this.idCardTypeNum = idCardTypeNum;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getUsePlace() {
		return usePlace;
	}
	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}
	public String getMachineIdentifier() {
		return machineIdentifier;
	}
	public void setMachineIdentifier(String machineIdentifier) {
		this.machineIdentifier = machineIdentifier;
	}
	
	
	
}
