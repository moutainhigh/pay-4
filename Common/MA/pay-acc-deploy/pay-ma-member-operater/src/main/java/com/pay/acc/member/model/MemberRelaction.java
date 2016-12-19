package com.pay.acc.member.model;

public class MemberRelaction {
	private Long fatherMemberCode;//交易中心
	private Long sonMemberCode;//交易商
	private Long usteelId;//钢网ID
	private String usteelName;//钢网名称
	

	public Long getFatherMemberCode() {
		return fatherMemberCode;
	}
	public void setFatherMemberCode(Long fatherMemberCode) {
		this.fatherMemberCode = fatherMemberCode;
	}
	public Long getSonMemberCode() {
		return sonMemberCode;
	}
	public void setSonMemberCode(Long sonMemberCode) {
		this.sonMemberCode = sonMemberCode;
	}
	
	public Long getUsteelId() {
		return usteelId;
	}
	public void setUsteelId(Long usteelId) {
		this.usteelId = usteelId;
	}
	public String getUsteelName() {
		return usteelName;
	}
	public void setUsteelName(String usteelName) {
		this.usteelName = usteelName;
	}

}
