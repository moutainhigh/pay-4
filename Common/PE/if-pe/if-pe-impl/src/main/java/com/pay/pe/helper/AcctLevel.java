package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 科目级别
 * 
 */
public enum AcctLevel {
	GENERAL(1), LEDGER(2), SUBLEDGER(3);
	private int value;

	public int getValue() {
		return value;
	}

	AcctLevel(int value) {
		this.value = value;
	}

	public final static Map<AcctLevel, String> ACCTLEVELMAP;

	static {
		ACCTLEVELMAP = new EnumMap<AcctLevel, String>(AcctLevel.class);
		ACCTLEVELMAP.put(AcctLevel.GENERAL, "总帐");
		ACCTLEVELMAP.put(AcctLevel.LEDGER, "明细帐");
		ACCTLEVELMAP.put(AcctLevel.SUBLEDGER, "明细帐子目");
	}

	/**
	 * 返回ACCTLEVEL对应的值 Example getValue(ACCTLEVEL.GENERAL);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(AcctLevel key) {

		return key.getValue();
	}

	/**
	 * 返回ACCTLEVEL对应的值 Example getACCTLEVELValue("GENERAL");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getACCTLEVELValue(String key) {
		return AcctLevel.valueOf(key).getValue();
	}

	/**
	 * 返回ACCTLEVELMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getACCTLEVELMAPList() {
		return ACCTLEVELMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return ACCTLEVEL
	 */
	public static AcctLevel getACCTLEVELMAPKey(int value) {
		AcctLevel tmpKey = null;
		for (AcctLevel tmpEnum : AcctLevel.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}

		return tmpKey;
	}

    /**
     * 返回ACCTLEVEL对应的描述.
     * @param value int.
     * @return String
     */
    public static String getACCTLEVELDesc(final int value) {
        return AcctLevel.ACCTLEVELMAP.get(
                AcctLevel.getACCTLEVELMAPKey(value));
    }
}
