package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 科目类型
 * 
 */
public enum AcctType {
    
	ASSERT(1,"资产类"), 
	LIABILITY(2,"负债类"), 
	OWNERSEQUIRY(3,"所有者权益"), 
	LOSSANDPROFILT(4,"损益类"), 
	ASSERTLIABILITY(5,"资产负债共同类");
	
	private int value;
	private String desc;

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	AcctType(int value,String desc) {
		this.value = value;
		this.desc = desc;
	}

	public final static Map<AcctType, String> ACCTTYPEMAP;

	static {
		ACCTTYPEMAP = new EnumMap<AcctType, String>(AcctType.class);
		ACCTTYPEMAP.put(AcctType.ASSERT, "资产类");
		ACCTTYPEMAP.put(AcctType.ASSERTLIABILITY, "资产负债共同类");
		ACCTTYPEMAP.put(AcctType.LIABILITY, "负债类");
		ACCTTYPEMAP.put(AcctType.OWNERSEQUIRY, "所有者权益");
		ACCTTYPEMAP.put(AcctType.LOSSANDPROFILT, "损益类");
	}

	/**
	 * 返回ACCTTYPE对应的值 Example getValue(ACCTTYPE.ASSERT);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(AcctType key) {

		return key.getValue();
	}

	/**
	 * 返回ACCTTYPE对应的值 Example getACCTTYPEValue("ASSERT");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getACCTTYPEValue(String key) {
		return AcctType.valueOf(key).getValue();
	}

	/**
	 * 返回ACCTTYPEMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getACCTTYPEMAPList() {
		return ACCTTYPEMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return ACCTTYPE
	 */
	public static AcctType getACCTTYPEMAPKey(int value) {
		AcctType tmpKey = null;
		for (AcctType tmpEnum : AcctType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}

		return tmpKey;
	}

    /**
     * 返回ACCTTYPE对应的描述.
     * @param value int.
     * @return String
     */
    public static String getACCTTYPEDesc(final int value) {
        return AcctType.ACCTTYPEMAP.get(
                AcctType.getACCTTYPEMAPKey(value));
    }
}