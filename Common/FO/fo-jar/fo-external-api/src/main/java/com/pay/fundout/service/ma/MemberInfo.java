/**
 * 
 */
package com.pay.fundout.service.ma;

/**
 * @author NEW
 *
 */
public class MemberInfo {
	/**
	 * 会员登录名称
	 */
	private String loginName;

	/**
	 * 会员代码
	 */
	private Long memberCode;
	
	/**
	 * 会员名称
	 */
	private String memberName;
	
	/**
	 * 会员状态
	 */
	private Integer status;
	
	/**
	 * 会员类型
	 * 1：个人会员
	 * 2：企业会员
	 */
	private Integer memberType;
	
	/**
	 * 会员等级
	 */
	private Integer levelCode;

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the memberType
	 */
	public Integer getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the levelCode
	 */
	public Integer getLevelCode() {
		return levelCode;
	}

	/**
	 * @param levelCode the levelCode to set
	 */
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	@Override
	public String toString() {
		return "MemberInfo [loginName=" + loginName + ", memberCode="
				+ memberCode + ", memberName=" + memberName + ", status="
				+ status + ", memberType=" + memberType + ", levelCode="
				+ levelCode + "]";
	}
	
}
