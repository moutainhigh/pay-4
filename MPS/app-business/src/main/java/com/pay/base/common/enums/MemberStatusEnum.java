/**
 * 
 */
package com.pay.base.common.enums;

/**
 * @author Administrator
 * 
 */
public enum MemberStatusEnum {
    UNCREATE(-1, "未创建"),CREATE(0, "创建"), NORMAL(1, "正常"), FROZEEN(2, "冻结"), NO_ACTIVE(3, "未激活"), DELETE(4, "删除"), TEMPORARY(5, "临时"),UNKNOW(9, "未知");
	private Integer code;
	private String message;

	private MemberStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static MemberStatusEnum getByCode(int code) {
		for (MemberStatusEnum status : values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}

}
