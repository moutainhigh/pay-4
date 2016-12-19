/**
 * 
 */
package com.pay.acc.member.memberenum;

/**
 * @author Administrator
 * 
 */
public enum MemberStatusEnum {
	CREATE(0, "创建"), NORMAL(1, "正常"), FROZEEN(2, "冻结"), NO_ACTIVE(3, "未激活"), DELETE(4, "删除"),PROVISIONAL(5,"临时");
	private int code;
	private String message;

	private MemberStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public static MemberStatusEnum getByCode(int code){
		for(MemberStatusEnum mse:values()){
			if(mse.getCode()==code){
				return mse;
			}
		}
		return null;
	}
	
}
