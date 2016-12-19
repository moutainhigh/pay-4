
package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;


public enum PayMethod {
    /**
     * 直接到帐.
     */
    DIRECTACCOUNT(1),
    /**
     * 加密到帐.
     */
    PASSWDACCOUNT(2),
    /**
     * 到帐.
     */
    ESCROW(3);
    private int value;

    public int getValue() {
        return value;
    }

    PayMethod(int value) {
        this.value = value;
    }
    
    public final static Map<PayMethod, String> PAYMETHODMAP;
    
    static{
	    PAYMETHODMAP = new EnumMap<PayMethod, String>(PayMethod.class);
	    PAYMETHODMAP.put(PayMethod.DIRECTACCOUNT, "直接到账");
	    PAYMETHODMAP.put(PayMethod.PASSWDACCOUNT, "加密到帐");
	    PAYMETHODMAP.put(PayMethod.ESCROW, "到帐");
    }
    
    /**
     * 返回PAYMETHOD对应的值 Example getValue(PAYMETHOD.BANKCARD);
     * 
     * @param key
     * @return int
     */
    public static int getValue(PayMethod key) {
        return key.getValue();
    }


    /**
     * 返回PAYMETHOD对应的值 Example getPAYMETHODValue("BANKCARD");
     * 
     * @param key
     * @return int
     */
    public static int getPAYMETHODValue(String key) {
        return PayMethod.valueOf(key).getValue();
    }
    
    /**
     * 返回PAYMETHODMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getPAYMETHODMAPList() {
        return PAYMETHODMAP.entrySet().iterator();
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return PAYMETHOD
     */
    public static PayMethod getPAYMETHODMAPKey(int value) {

        PayMethod tmpKey = null;
        for (PayMethod tmpEnum : PayMethod.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回PAYMETHOD对应的描述.
     * @param value int.
     * @return String
     */
    public static String getPAYMETHODDesc(final int value) {
        return PayMethod.PAYMETHODMAP.get(
                PayMethod.getPAYMETHODMAPKey(value));
    }
}