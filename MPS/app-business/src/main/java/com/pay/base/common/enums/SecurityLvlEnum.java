package com.pay.base.common.enums;

public enum SecurityLvlEnum {
 
	 /** 锁定*/
     LOCK(0,"锁定"),
     /** 口令卡用户非口令卡登录*/
     UNMAXTRIX(1,"口令卡用户非口令卡登录"),
     /** 普通登录*/
     NORMAL(2,"普通登录"),
     /** 口令卡登录*/
     MAXTRIX(3,"口令卡登录"),
     /** 数字证书 */
     CERTIFICATE(4,"数字证书"),
     /** U盾 */
     U_SHIELD(5,"U盾"),
     BSP_ADMIN(6,"交易中心管理员"),
     BSP_MERCHANT(7,"交易商"),
     /** 安全登录 */
     SECURITY(8,"安全登录");
     
    private  int securityLvl;
    private String memo;
    
    SecurityLvlEnum(int securityLvl,String memo){
        this.securityLvl=securityLvl;
        this.memo=memo;
    }
    
    public  int getValue(){
        return securityLvl;
    }
    
    public String getMemo(){
        return memo;
    }
    
  
    
    public static SecurityLvlEnum getSecurityLvlEnum(int securityLvl){
        for (SecurityLvlEnum nameEnum : values()) {
            if(nameEnum.getValue()==securityLvl){
                return nameEnum;
            }
        }
        return null;
    }
}
