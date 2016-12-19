package com.pay.poss.personmanager.enums;

public enum BankAcctStatusEnums {
//	验证状态标识    0、未验证;1、已验证;2、验证中（未打款）  ;3.验证中 ; 4 鉴权验证中
	UNVALIDATE(0, "未验证"),
	VALIDATE(1,"已验证"),
	NOPAY(2,"验证中（未打款）"),
	VAKUDATING(3,"验证中"),
	IDENTIFY(4,"验证中")
	;
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	BankAcctStatusEnums(int code, String description) {
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
	 * 通过枚举code获得枚举
	 * 
	 * @param code
	 * @return description
	 */
	public static BankAcctStatusEnums getByCode(int code) {
		for (BankAcctStatusEnums department : values()) {
			if (department.getCode() == code) {
				return department;
			}
		}
		return null;
	}
	
	/**通过CODE得到值
	 * @param value
	 * @return
	 */
	public static String getName(int value) {
		String  tmpKey = null;
		for (BankAcctStatusEnums tmpEnum : BankAcctStatusEnums.values()) {
			if (tmpEnum.getCode() == value) {				
				tmpKey = tmpEnum.getDescription();
				break;
			}
		}
		return tmpKey;
	}
}
