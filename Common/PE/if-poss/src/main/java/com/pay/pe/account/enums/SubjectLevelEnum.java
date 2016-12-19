package com.pay.pe.account.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SubjectLevelEnum {
	ONE_LEVER(1, "一级科目"),
	TWO_LEVER(2, "二级科目"), 
	THREE_LEVER(3, "三级科目"),
	FOUR_LEVER(4, "四级科目"), 
	FIVE_LEVER(5, "五级科目")

	;
	private int code;
	private String message;

	//反向查找
	private static final Map<Integer, SubjectLevelEnum> lookup

	= new HashMap<Integer, SubjectLevelEnum>();

	static {

		for (SubjectLevelEnum s : EnumSet.allOf(SubjectLevelEnum.class)) {

			lookup.put(s.getCode(), s);

		}

	}

	public static SubjectLevelEnum get(int code) {
		return lookup.get(code);

	}

	private SubjectLevelEnum(int code, String message) {
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
	
	public static void main(String[] args){
		int level=Integer.parseInt("5");
		System.out.println(SubjectLevelEnum.get(level).getMessage());
	}

}
