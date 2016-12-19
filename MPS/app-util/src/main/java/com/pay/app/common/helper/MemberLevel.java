package com.pay.app.common.helper;

/**
 * 
 * 会员实名认证级别
 * 
 * @date 2010-11-9
 * 
 * @author jerry_jin
 *
 */
public enum MemberLevel {
	
	NORMAL(0,"普通",false),
	BANKCARD_VERIFY(1,"银行卡权鉴",true),
	POLICE_VERIFY(2,"公安网认证",true),
	BANKCARD_UPLOADFILE_VERIFY(3,"银行卡鉴权(上传影印件)",true),
	POLICE_UPOADFILE_VERIFY(4,"公安网认证(上传影印件)",true);
	
	private int value;
	
	private String displayName;
	
	private boolean isVerified;

	public int getValue() {
		return value;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public boolean isVerified() {
		return isVerified;
	}

	private MemberLevel(int value,String displayName,boolean isVerified){
		this.value = value;
		this.displayName = displayName;
		this.isVerified  = isVerified;
	}
}
