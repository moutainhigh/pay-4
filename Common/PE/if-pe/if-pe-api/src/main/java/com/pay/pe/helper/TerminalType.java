package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 终端类型
 */
public enum TerminalType {
	
	WEB(1), WAP(2), API(3), POS(4);
	private int value;

	public int getValue() {
		return value;
	}

	TerminalType(int value) {
		this.value = value;
	}

	public final static Map<TerminalType, String> TERMINALTYPEMAP;

	static {
		TERMINALTYPEMAP = new EnumMap<TerminalType, String>(TerminalType.class);
		TERMINALTYPEMAP.put(TerminalType.WEB, "WEB");
		TERMINALTYPEMAP.put(TerminalType.WAP, "WAP");
		TERMINALTYPEMAP.put(TerminalType.API, "API");
		TERMINALTYPEMAP.put(TerminalType.POS, "POS");
	}

	/**
	 * 返回TERMINALTYPE对应的值 Example getValue(TERMINALTYPE.API);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(TerminalType key) {
		return key.getValue();
	}

	/**
	 * 返回TERMINALTYPE对应的值 Example getTERMINALTYPEValue("API");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getTERMINALTYPEValue(String key) {
		return TerminalType.valueOf(key).getValue();
	}

	/**
	 * 返回TERMINALTYPEMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getTERMINALTYPEMAPList() {
		return TERMINALTYPEMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return TERMINALTYPE
	 */
	public static TerminalType getTERMINALTYPEMAPKey(int value) {
		TerminalType tmpKey = null;
		for (TerminalType tmpEnum : TerminalType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}
		return tmpKey;
	}

	/**
	 * 返回TERMINALTYPE对应的描述.
	 * 
	 * @param value
	 *            int.
	 * @return String
	 */
	public static String getTERMINALTYPEDesc(final int value) {
		return TerminalType.TERMINALTYPEMAP.get(TerminalType
				.getTERMINALTYPEMAPKey(value));
	}
}