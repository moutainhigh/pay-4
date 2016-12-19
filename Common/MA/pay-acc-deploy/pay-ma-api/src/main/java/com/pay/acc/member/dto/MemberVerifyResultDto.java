package com.pay.acc.member.dto;

import java.io.Serializable;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-5 下午10:05:03
 */
public class MemberVerifyResultDto implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否实名认证
	 */
	private boolean isVerify;
	/**
	 * 会员认证等级
	 * 0 普通
	 * 1 银行卡鉴权 
	 * 2 公安网  
	 * 3 银行卡鉴权+上传影印件 
	 * 4 公安网+上传影印件
	 */
	private int memberLevel;
	
	public boolean isVerify() {
		return isVerify;
	}
	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
	
}
