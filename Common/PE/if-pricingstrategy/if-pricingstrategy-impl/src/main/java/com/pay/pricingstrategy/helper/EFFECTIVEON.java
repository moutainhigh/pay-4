package com.pay.pricingstrategy.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 生效范围
 */
public  enum EFFECTIVEON {
   GLOBLE(1),SPECIFIDESERVERLEVEL(2),SPECIFIEDMEMBER(3);
   private  int value;
   
   public int getValue(){
	   return value;
   }
   
   EFFECTIVEON(int value){
	   this.value = value;
   }
   public final static Map<EFFECTIVEON, String> EFFECTIVEONMAP;
   
   static{
    EFFECTIVEONMAP = new EnumMap<EFFECTIVEON,String>(EFFECTIVEON.class);
	EFFECTIVEONMAP.put(EFFECTIVEON.GLOBLE, "所有会员");
	EFFECTIVEONMAP.put(EFFECTIVEON.SPECIFIDESERVERLEVEL, "特定服务等级");
	EFFECTIVEONMAP.put(EFFECTIVEON.SPECIFIEDMEMBER, "特定会员");
   }
	 /**
	 * 返回EFFECTIVEON对应的值 Example getValue(EFFECTIVEON.GLOBLE);
	 * 
	 * @param key
	 * @return int 
	 */
	public static int getValue(EFFECTIVEON key) {


		return key.getValue();
	}

	/**
	 * 返回EFFECTIVEON对应的值 Example getEFFECTIVEONValue("GLOBLE");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getEFFECTIVEONValue(String key) {
		return EFFECTIVEON.valueOf(key).getValue();
	}
	
	/**
     * 返回EFFECTIVEONMAP Entry list
     * 
     * @return Iterator
     */
    public static Iterator getEFFECTIVEONMAPList() {
        return EFFECTIVEONMAP.entrySet().iterator();
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return RANGBY
     */
    public static EFFECTIVEON getEFFECTIVEONMAPKey(int value) {
    	EFFECTIVEON tmpKey = null;
        for (EFFECTIVEON tmpEnum : EFFECTIVEON.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }

        return tmpKey;
    }
    /**
     * 返回EFFECTIVEON对应的描述.
     * @param value int.
     * @return String
     */
    public static String getEFFECTIVEONDesc(final int value) {
        return EFFECTIVEON.EFFECTIVEONMAP.get(
                EFFECTIVEON.getEFFECTIVEONMAPKey(value));
    }
}
