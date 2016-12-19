/**
 * 
 */
package com.pay.app.dto;

/**
 * 系统公告参数
 * @author PengJiangbo
 *
 */
public class AnnouncementParamDTO extends BaseDTO {
	
	//商户会员号
	private Long memberCode ;

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
}
