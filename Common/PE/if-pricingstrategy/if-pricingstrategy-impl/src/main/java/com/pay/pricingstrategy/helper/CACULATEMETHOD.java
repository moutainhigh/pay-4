package com.pay.pricingstrategy.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 计算方法
 */
public  enum CACULATEMETHOD {
	SINGLETRANSACTION(1), FIXED(2), SPECIALTIME(3), ACCUMULATED(4);
	private int value;

    public int getValue() {
        return value;
    }

	CACULATEMETHOD(int value) {
		this.value = value;
	}
	
	public final static Map<CACULATEMETHOD, String> CACULATEMETHODMAP;
	
	static{
	CACULATEMETHODMAP = new EnumMap<CACULATEMETHOD, String>(
			CACULATEMETHOD.class);
	CACULATEMETHODMAP.put(CACULATEMETHOD.SINGLETRANSACTION, "单笔交易");
	CACULATEMETHODMAP.put(CACULATEMETHOD.FIXED, "每月固定");
	CACULATEMETHODMAP.put(CACULATEMETHOD.SPECIALTIME, "特定时间");
	CACULATEMETHODMAP.put(CACULATEMETHOD.ACCUMULATED, "每月累计");
	}
	
	/**
	 * 返回CACULATEMETHOD对应的值 Example getValue(CACULATEMETHOD.FIXED);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(CACULATEMETHOD key) {
		return key.getValue();
	}

	/**
	 * 返回CACULATEMETHOD对应的值 Example getCACULATEMETHODValue("FIXED");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getCACULATEMETHODValue(String key) {
		return CACULATEMETHOD.valueOf(key).getValue();
	}
	
	/**
	 * 返回CACULATEMETHODMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getCACULATEMETHODMAPList() {
		return CACULATEMETHODMAP.entrySet().iterator();
	}

	
	/**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return CACULATEMETHOD
     */
    public static CACULATEMETHOD getCACULATEMETHODMAPKey(int value) {
    	CACULATEMETHOD tmpKey = null;
        for (CACULATEMETHOD tmpEnum : CACULATEMETHOD.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }

        return tmpKey;
    }
    /**
     * 返回CACULATEMETHOD对应的描述.
     * @param value int.
     * @return String
     */
    public static String getCACULATEMETHODDesc(final int value) {
        return CACULATEMETHOD.CACULATEMETHODMAP.get(
                CACULATEMETHOD.getCACULATEMETHODMAPKey(value));
    }
};