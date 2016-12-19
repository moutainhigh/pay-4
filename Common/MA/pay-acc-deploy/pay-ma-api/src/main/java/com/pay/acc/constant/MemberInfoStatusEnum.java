package com.pay.acc.constant;

public enum MemberInfoStatusEnum {

	/** 0:创建 */
	CREATE(0, "创建"),

	/** 1:正常 */
	NORMAL(1, "正常"),

	/** 2:冻结 */
	FREEZE(2, "冻结"),

	/**
	 * 未激活
	 */
	NUACTIVE(3, "未激活"), /**
	 * 
	 * 删除
	 */
	DELETED(4, "删除"), /**
	 * 
	 * 临时
	 */
	TEMP(5, "临时");

	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MemberInfoStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static MemberInfoStatusEnum getByCode(int code) {
		for (MemberInfoStatusEnum memberInfoStatus : values()) {
			if (memberInfoStatus.getCode() == code) {
				return memberInfoStatus;
			}
		}
		return null;
	}

}
