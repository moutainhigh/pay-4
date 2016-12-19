package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付内容
 * 
 */
public enum AcctContent {
    CURRENCY(1), CHINAGO(2), INTEGRAL(3);
    private int value;

    public int getValue() {
        return value;
    }

    AcctContent(int value) {
        this.value = value;
    }
    
    public final static Map<AcctContent, String> ACCTCONTENTMAP;
    
    static{
    	  ACCTCONTENTMAP = new EnumMap<AcctContent, String>(AcctContent.class);
          ACCTCONTENTMAP.put(AcctContent.CURRENCY, "货币");
          ACCTCONTENTMAP.put(AcctContent.CHINAGO, "神州行");
          ACCTCONTENTMAP.put(AcctContent.INTEGRAL, "积分");
    }
    
    /**
     * 返回ACCTCONTENT对应的值 Example getValue(ACCTCONTENT.CURRENCY);
     * 
     * @param key
     * @return int
     */
    public static int getValue(AcctContent key) {
        return key.getValue();
    }

    /**
     * 返回ACCTCONTENT对应的值 Example getACCTCONTENTValue("CURRENCY");
     * 
     * @param key
     * @return int
     */
    public static int getACCTCONTENTValue(String key) {
        return AcctContent.valueOf(key).getValue();
    }

    /**
     * 返回ACCTCONTENTMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getACCTCONTENTMAPList() {
        return ACCTCONTENTMAP.entrySet().iterator();
    }

    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return ACCTCONTENT
     */
    public static AcctContent getACCTCONTENTMAPKey(int value) {
        AcctContent tmpKey = null;
        for (AcctContent tmpEnum : AcctContent.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }

    /**
     * 返回ACCTCONTENT对应的描述.
     * @param value int.
     * @return String
     */
    public static String getACCTCONTENTDesc(final int value) {
        return AcctContent.ACCTCONTENTMAP.get(
                AcctContent.getACCTCONTENTMAPKey(value));
    }
}