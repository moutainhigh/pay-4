package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 余额方向
 */
public enum CRDRType {
	
    DEBIT(1),
    CREDIT(2) 
    ;
    private int value;

    public int getValue() {
        return value;
    }

    CRDRType(int value) {
        this.value = value;
    }
    
    public final static Map<CRDRType, String> CRDBTYPEMAP;
    
    static{
    	CRDBTYPEMAP = new EnumMap<CRDRType, String>(CRDRType.class);
        CRDBTYPEMAP.put(CRDRType.CREDIT, "贷方");
        CRDBTYPEMAP.put(CRDRType.DEBIT, "借方");
    }
    
    /**
     * 返回CRDBTYPE对应的值 Example getValue(CRDBTYPE.CREDIT)
     * 
     * @param key
     * @return int
     */
    public static int getValue(CRDRType key) {
        return key.getValue();
    }

    /**
     * 返回CRDBTYPE对应的值 Example getCRDBTYPEValue("CREDIT");
     * 
     * @param key
     * @return int
     */
    public static int getCRDBTYPEValue(String key) {
        return CRDRType.valueOf(key).getValue();
    }

    /**
     * 返回CRDBTYPEMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getCRDBTYPEMAPList() {
        return CRDBTYPEMAP.entrySet().iterator();
    }

    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return CRDBTYPE
     */
    public static CRDRType getCRDBTYPEMAPKey(int value) {

        CRDRType tmpKey = null;
        for (CRDRType tmpEnum : CRDRType.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回CRDBTYPE对应的描述.
     * @param value int.
     * @return String
     */
    public static String getCRDBTYPEDesc(final int value) {
        return CRDRType.CRDBTYPEMAP.get(
                CRDRType.getCRDBTYPEMAPKey(value));
    }
}