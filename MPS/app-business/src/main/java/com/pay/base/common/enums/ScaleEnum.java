package com.pay.base.common.enums;


public enum ScaleEnum {
	/**PUBLIC */
    PUBLIC(1,"公共"),
    /**企业 */
    ENTERPRICE(2,"企业"),
    /**个人 */
    INDIVIDUAL(3,"个人"),
    /**个人卖家 */
    INDIVIDUAL_SELLER(4,"个人卖家"),
    TRADING_CENTER(10,"交易中心");
    private int scale;
    private String memo;
    
    ScaleEnum(int appScale,String memo){
        this.scale=appScale;
        this.memo=memo;
    }
    
    public int getValue(){
        return scale;
    }
    
    public String getMemo(){
        return memo;
    }
    
    public static ScaleEnum getScaleEnum(int appScale){
        for (ScaleEnum nameEnum : values()) {
            if(nameEnum.getValue()==appScale){
                return nameEnum;
            }
        }
        return null;
    }
}
