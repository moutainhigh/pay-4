package com.pay.pricingstrategy.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 价格策略类型
 * 
 */
public enum PRICESTRATEGYTYPE {
    FIXEDCHARGE(1), CHARGERATIO(2), CHARGERATIOANDLOWERLIMIT(3), CHARGERATIOANDUPPERlIMIT(
            4), CHARGERATIOANDUPPERlIMITANDLOWERLIMIT(5), FIXEDCHARGE_CHARGERATIO(
            6), FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT(7), FIXEDCHARGE_CHARGERATIOANDLOWERLIMIT(
            8), FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT(9);
    private int value;

    public int getValue() {
        return value;
    }

    PRICESTRATEGYTYPE(int value) {
        this.value = value;
    }

    public final static Map<PRICESTRATEGYTYPE, String> PRICESTRATEGYTYPEMAP;

    static {
        PRICESTRATEGYTYPEMAP = new EnumMap<PRICESTRATEGYTYPE, String>(
                PRICESTRATEGYTYPE.class);
        PRICESTRATEGYTYPEMAP.put(PRICESTRATEGYTYPE.FIXEDCHARGE, "固定费用");
        PRICESTRATEGYTYPEMAP.put(PRICESTRATEGYTYPE.CHARGERATIO, "费率");
        PRICESTRATEGYTYPEMAP.put(PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMIT,
                "费率及上限");
        PRICESTRATEGYTYPEMAP.put(PRICESTRATEGYTYPE.CHARGERATIOANDLOWERLIMIT,
                "费率及下限");
        PRICESTRATEGYTYPEMAP.put(
                PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMITANDLOWERLIMIT,
                "费率及上下限");
        PRICESTRATEGYTYPEMAP.put(
                PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIO,
                "固定费用_费率");
        PRICESTRATEGYTYPEMAP.put(
                PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT,
                "固定费用_费率及上限");
        PRICESTRATEGYTYPEMAP.put(
                PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDLOWERLIMIT,
                "固定费率_费率及下限");
        PRICESTRATEGYTYPEMAP
                .put(
                        PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT,
                        "固定费用_费率及上下限");
    }

    /**
     * 返回PRICESTRATEGYTYPE对应的值 Example getValue(PRICESTRATEGYTYPE.FIXEDCHARGE);
     * 
     * @param key
     * @return int
     */
    public static int getValue(PRICESTRATEGYTYPE key) {

        return key.getValue();
    }

    /**
     * 返回PRICESTRATEGYTYPE对应的值 Example getPRICESTRATEGYTYPEValue("FIXEDCHARGE");
     * 
     * @param key
     * @return int
     */
    public static int getPRICESTRATEGYTYPEValue(String key) {
        return PRICESTRATEGYTYPE.valueOf(key).getValue();
    }

    /**
     * 返回PRICESTRATEGYTYPEMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getPRICESTRATEGYTYPEMAPList() {
        return PRICESTRATEGYTYPEMAP.entrySet().iterator();
    }

    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return PRICESTRATEGYTYPE
     */
    public static PRICESTRATEGYTYPE getPRICESTRATEGYTYPEMAPKey(int value) {
        PRICESTRATEGYTYPE tmpKey = null;
        for (PRICESTRATEGYTYPE tmpEnum : PRICESTRATEGYTYPE.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }

    /**
     * 返回PRICESTRATEGYTYPE对应的描述.
     * 
     * @param value
     *            int.
     * @return String
     */
    public static String getPRICESTRATEGYTYPEDesc(final int value) {
        return PRICESTRATEGYTYPE.PRICESTRATEGYTYPEMAP.get(PRICESTRATEGYTYPE
                .getPRICESTRATEGYTYPEMAPKey(value));
    }
}
