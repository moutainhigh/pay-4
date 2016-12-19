package com.pay.base.common.enums;

public enum OrginEnum {
    PUBLIC_LOCAL(0,"公共"),
    INDIVIDUAL_LOCAL(1,"个人会员"),
    CORP_LOCAL(2,"企业"),
    SHOP(3,"商城");
    
    private int orgin;
    private String memo;
    
    OrginEnum(int orgin,String memo){
        this.orgin=orgin;
        this.memo=memo;
    }
    
    public int getValue(){
        return orgin;
    }
    
    public String getMemo(){
        return memo;
    }
    
    public static OrginEnum getOrginEnum(int orgin){
        for (OrginEnum nameEnum : values()) {
            if(nameEnum.getValue()==orgin){
                return nameEnum;
            }
        }
        return null;
    }
}
