package com.pay.poss.personmanager.enums;

public enum IdcStatusEnums {
	UNVALIDATE(0, "否"),
	VALIDATE(1,"是")
	;
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	IdcStatusEnums(int code, String description) {
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
	public static IdcStatusEnums getByCode(int code) {
		for (IdcStatusEnums department : values()) {
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
		for (IdcStatusEnums tmpEnum : IdcStatusEnums.values()) {
			if (tmpEnum.getCode() == value) {				
				tmpKey = tmpEnum.getDescription();
				break;
			}
		}
		return tmpKey;
	}
}
