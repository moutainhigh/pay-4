package com.pay.pricingstrategy.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public enum SERVICELEVEL {
	/**
	 * 普通消费者.
	 */
    MEMBER_PERSONAL(100),
    /**
     * 个人商家.
     */
    MEMBER_PERSONAL_MERCHANT(101),
    /**
     * 新单位客户.
     */
    MEMBER_UNIT(200),
    /**
     * 中小单位客户.
     */
    MEMBER_UNIT_MIDDLE_MIN(201),
    /**
     * 大单位客户.
     */
    MEMBER_UNIT_MAX(202);
    
    private int value;
    public int getValue() {
        return value;
    }

    SERVICELEVEL(int value) {
        this.value = value;
    }
    
    public final static Map<SERVICELEVEL, String> SERVICELEVELMAP;
    
    static{
        SERVICELEVELMAP = new EnumMap<SERVICELEVEL, String>(SERVICELEVEL.class);
        SERVICELEVELMAP.put(SERVICELEVEL.MEMBER_PERSONAL, "个人普通会员");
        SERVICELEVELMAP.put(SERVICELEVEL.MEMBER_PERSONAL_MERCHANT, "个人卖家");
        SERVICELEVELMAP.put(SERVICELEVEL.MEMBER_UNIT, "企业普通会员");
        SERVICELEVELMAP.put(SERVICELEVEL.MEMBER_UNIT_MIDDLE_MIN, "中小企业客户");
        SERVICELEVELMAP.put(SERVICELEVEL.MEMBER_UNIT_MAX, "大企业客户");
    }
    
    /**
     * 返回SERVICELEVEL对应的值 Example getValue(SERVICELEVEL.RESERVEMEMBER)
     * 
     * @param key
     * @return int
     */
    public static int getValue(SERVICELEVEL key) {
        return key.getValue();
    }

    /**
     * 返回SERVICELEVEL对应的值 Example getSERVICELEVELValue("RESERVEMEMBER");
     * 
     * @param key
     * @return int
     */
    public static int getSERVICELEVELValue(String key) {
        return SERVICELEVEL.valueOf(key).getValue();
    }

    /**
     * 返回SERVICELEVELMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getSERVICELEVELMAPList() {
        return SERVICELEVELMAP.entrySet().iterator();
    }

    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return CRDBTYPE
     */
    public static SERVICELEVEL getSERVICELEVELMAPKey(int value) {

        SERVICELEVEL tmpKey = null;
        for (SERVICELEVEL tmpEnum : SERVICELEVEL.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回SERVICELEVEL对应的描述.
     * @param value int.
     * @return String
     */
    public static String getSERVICELEVELDesc(final int value) {
        return SERVICELEVEL.SERVICELEVELMAP.get(
                SERVICELEVEL.getSERVICELEVELMAPKey(value));
    }
}
