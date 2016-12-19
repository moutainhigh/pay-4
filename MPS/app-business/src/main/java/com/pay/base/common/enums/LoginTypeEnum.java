package com.pay.base.common.enums;

public enum LoginTypeEnum {
    mobile(1,"手机"),
    email(2,"E-mail");

    
    
    private int loginType;
    private String memo;
    
    
    LoginTypeEnum(int loginType,String memo){
        this.loginType=loginType;
        this.memo=memo;
    }
    
    public int getValue(){
        return loginType;
    }
    
    public String getMemo(){
        return memo;
    }
    
    public static LoginTypeEnum getScaleEnum(int loginType){
        for (LoginTypeEnum nameEnum : values()) {
            if(nameEnum.getValue()==loginType){
                return nameEnum;
            }
        }
        return null;
    }
}
