/**
 * 
 */
package com.pay.poss.enterprisemanager.enums;

/**
 * @Description
 * @project ma-manager
 * @file OperatorEnum.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0
 */
public enum OperatorEnum {

	CREATE(0, "创建"), 
	NORMAL(1, "正常"), 
	CLOSED(2, "关闭"),
	DELETED(3, "删除"), 
	UNKNOWN(-1, "未知");

	private int code;
	private String desc;

	/**
	 * @param code
	 * @param desc
	 */
	private OperatorEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	public static OperatorEnum get(int code){
		for(OperatorEnum e : values()){
			if(e.getCode() == code){
				return e;
			}
		}
		return UNKNOWN;
	}

}
