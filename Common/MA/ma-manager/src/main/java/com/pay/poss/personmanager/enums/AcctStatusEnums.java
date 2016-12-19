package com.pay.poss.personmanager.enums;

public enum AcctStatusEnums {
	INVALIDATION(0, "失效"),
	ACTIVATE(1,"正常"),
	REEEZE(2,"冻结")
	;
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	AcctStatusEnums(int code, String description) {
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
	public static AcctStatusEnums getByCode(int code) {
		for (AcctStatusEnums department : values()) {
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
		for (AcctStatusEnums tmpEnum : AcctStatusEnums.values()) {
			if (tmpEnum.getCode() == value) {				
				tmpKey = tmpEnum.getDescription();
				break;
			}
		}
		return tmpKey;
	}
}
