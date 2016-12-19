package com.pay.poss.featuremenu.enums;


public enum ScaleEnum {
    PUBLIC(1,"公共"),
    ENTERPRICE(2,"企业"),
    INDIVIDUAL(3,"个人");
    
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
