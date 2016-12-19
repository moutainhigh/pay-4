package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付组织类型
 */
public enum OrgType {
	
	MEMBER(1), BANK(2), INNER(3);
	
	private int value;

	public int getValue() {
		return value;
	}

	OrgType(int value) {
		this.value = value;
	}

	public final static Map<OrgType, String> ORGTYPEMAP;

	static {

		ORGTYPEMAP = new EnumMap<OrgType, String>(OrgType.class);
		ORGTYPEMAP.put(OrgType.MEMBER, "会员");
		;
		ORGTYPEMAP.put(OrgType.BANK, "银行");
		ORGTYPEMAP.put(OrgType.INNER, "内部");
	}

	/**
	 * 返回ORGTYPE对应的值 Example getValue(ORGTYPE.BANK);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(OrgType key) {
		return key.getValue();
	}

	/**
	 * 返回ORGTYPE对应的值 Example getORGTYPEValue("BANK");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getORGTYPEValue(String key) {
		return OrgType.valueOf(key).getValue();
	}

	/**
	 * 返回ORGTYPEMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getORGTYPEMAPList() {
		return ORGTYPEMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return ORGTYPE
	 */
	public static OrgType getORGTYPEMAPKey(int value) {
		OrgType tmpKey = null;
		for (OrgType tmpEnum : OrgType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}
		return tmpKey;
	}

	/**
	 * 返回ORGTYPE对应的描述.
	 * 
	 * @param value
	 *            int.
	 * @return String
	 */
	public static String getORGTYPEDesc(final int value) {
		return OrgType.ORGTYPEMAP.get(OrgType.getORGTYPEMAPKey(value));
	}
};