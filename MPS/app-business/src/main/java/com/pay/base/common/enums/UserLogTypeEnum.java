package com.pay.base.common.enums;

public enum UserLogTypeEnum {
    LOGIN(1, "登录"), PAY(2, "支付"), PAYMENT(3, "付款"), COST(4, "充值"), PAYCONFIRM(
            5, "确认付款"), QUERYBALANCE(6, "余额查询"), QUERYTRADE(7, "交易查询"), SAFEQUESTION(
            8, "设置安全问题"), UPDATEPAYPWD(9, "修改支付密码"), NOTICE(10, "订阅通知"), ADDLINKER(
            11, "添加联系人"), FINDPAYPWD(12, "找回支付密码"), UPDATEGREETING(13, "修改问候语"), CASH(
            14, "提现"), COMPLETEUSERINFO(15, "补全资料");
    
    private int typeId;
    private String description;
    
    private UserLogTypeEnum(int typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }
    
    public int getValue(){
        return typeId;
    }
    
    public String getDescription(){
        return description;
    }
    
    public static UserLogTypeEnum getUserLogTypeEnum(int typeId){
        for (UserLogTypeEnum nameEnum : values()) {
            if(nameEnum.getValue()==typeId){
                return nameEnum;
            }
        }
        return null;
    }
}
