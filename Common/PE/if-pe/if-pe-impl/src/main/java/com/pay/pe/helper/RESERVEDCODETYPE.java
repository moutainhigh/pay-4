package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;


public enum RESERVEDCODETYPE {
    
	OPPOSINGACCTCODE(1), OPPOSINGORGCODE(2);
	private int value;

	public int getValue() {
		return value;
	}

	RESERVEDCODETYPE(int value) {
		this.value = value;
	}

	public final static Map<RESERVEDCODETYPE, String> RESERVEDCODETYPEMAP;

	static {
		RESERVEDCODETYPEMAP = new EnumMap<RESERVEDCODETYPE, String>(RESERVEDCODETYPE.class);
		RESERVEDCODETYPEMAP.put(RESERVEDCODETYPE.OPPOSINGACCTCODE, "对方会员号");
		RESERVEDCODETYPEMAP.put(RESERVEDCODETYPE.OPPOSINGORGCODE, "对方机构号");
	}

	/**
	 * 返回RESERVEDCODETYPEMAP对应的值
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(RESERVEDCODETYPE key) {

		return key.getValue();
	}

	/**
     * 返回RESERVEDCODETYPE对应的值 Example getRESERVEDCODETYPEValue("PAYERACCTCODE");
     * 
     * @param key
     * @return int
     */
	public static int getRESERVEDCODETYPEValue(String key) {
		return RESERVEDCODETYPE.valueOf(key).getValue();
	}

	/**
	 * 返回RESERVEDCODETYPEMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getRESERVEDCODETYPEMAPList() {
		return RESERVEDCODETYPEMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return RESERVEDCODETYPEMAP
	 */
	public static RESERVEDCODETYPE getRESERVEDCODETYPEMAPKey(int value) {
		RESERVEDCODETYPE tmpKey = null;
		for (RESERVEDCODETYPE tmpEnum : RESERVEDCODETYPE.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}

		return tmpKey;
	}

    /**
     * 返回RESERVEDCODETYPE对应的描述.
     * @param value int.
     * @return String
     */
    public static String getRESERVEDCODETYPEDesc(final int value) {
        return RESERVEDCODETYPE.RESERVEDCODETYPEMAP.get(
                RESERVEDCODETYPE.getRESERVEDCODETYPEMAPKey(value));
    }
}