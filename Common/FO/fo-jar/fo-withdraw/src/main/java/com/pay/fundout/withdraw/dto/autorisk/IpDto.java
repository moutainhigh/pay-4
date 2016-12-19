package com.pay.fundout.withdraw.dto.autorisk;

public class IpDto {

	/*
	 * 会员号
	 */
	private Long memberCode;
	
	/*
	 * 会员名
	 */
	private String memberName;
	
	/*
	 * 会员类型
	 */
	private Integer memberType;
	
	/*
	 * 关联IP
	 */
	private String ip;
	
	/*
	 * IP城市
	 */
	private String city;
	
	/*
	 * 主账户最近登录日期
	 */
	private String latestLoginTime;
	
	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatestLoginTime() {
		return latestLoginTime;
	}

	public void setLatestLoginTime(String latestLoginTime) {
		this.latestLoginTime = latestLoginTime;
	}
	
}
