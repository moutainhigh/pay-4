package com.pay.pricingstrategy.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/** 计算方式
 * 
 */
public enum RANGBY {
	ONCEDEALINGMONEY(1), MONTHSUNDEALINGMONEY(2);
	private int value;

	public int getValue() {
		return value;
	}

	RANGBY(int value) {
		this.value = value;
	}

	public final static Map<RANGBY, String> RANGBYMAP;

	static {
		RANGBYMAP = new EnumMap<RANGBY, String>(RANGBY.class);
		RANGBYMAP.put(RANGBY.ONCEDEALINGMONEY, "单笔交易额");
		RANGBYMAP.put(RANGBY.MONTHSUNDEALINGMONEY, "月累计交易额");
	}

	/**
	 * 返回RANGBY对应的值 Example getValue(RANGBY.ONCEDEALINGMONEY);
	 * 
	 * @param key
	 * @return int 
	 */
	public static int getValue(RANGBY key) {

		return key.getValue();
	}

	/**
	 * 返回RANGBY对应的值 Example getRANGBYValue("ONCEDEALINGMONEY");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getRANGBYValue(String key) {
		return RANGBY.valueOf(key).getValue();
	}

	/**
	 * 返回RANGBY Entry list
	 * 
	 * @return Iterator
	 */

	public static Iterator getRANGBYMAPList() {
		return RANGBYMAP.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return RANGBY
	 */
	public static RANGBY getRANGBYMAPKey(int value) {
		RANGBY tmpKey = null;
		for (RANGBY tmpEnum : RANGBY.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}

		return tmpKey;
	}
    
    /**
     * 返回RANGBY对应的描述.
     * @param value int.
     * @return String
     */
    public static String getRANGBYDesc(final int value) {
        return RANGBY.RANGBYMAP.get(
                RANGBY.getRANGBYMAPKey(value));
    }
}