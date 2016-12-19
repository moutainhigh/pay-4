package com.pay.base.common.enums;

public enum PayChainEnum {
	EFFECT(1,"有效"),
    CLOSED(2,"关闭");
    
    
    private int payChainStatus;
    private String memo;
    
    PayChainEnum(int payChainStatus,String memo){
        this.payChainStatus=payChainStatus;
        this.memo=memo;
    }
    
    public int getValue(){
        return payChainStatus;
    }
    
    public String getMemo(){
        return memo;
    }
    
    public static PayChainEnum getOrginEnum(int payChainStatus){
        for (PayChainEnum nameEnum : values()) {
            if(nameEnum.getValue()==payChainStatus){
                return nameEnum;
            }
        }
        return null;
    }
}
