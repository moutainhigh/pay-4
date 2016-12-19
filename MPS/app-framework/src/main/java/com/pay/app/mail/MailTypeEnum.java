package com.pay.app.mail;

public enum MailTypeEnum {
    
    INDIVIDUAL("1","个人"),
    /**企业 */
    ENTERPRICE("2","企业"),
    /**个人 */
    CHANGE_PWD("1","修改密码");
    
    private String mailType;
    private String memo;
    
    MailTypeEnum(String mailType,String memo){
        this.mailType=mailType;
        this.memo=memo;
    }
    
    public String getValue(){
        return mailType;
    }
    
    public String getMemo(){
        return memo;
    }
    
    public MailTypeEnum getMailTypeEnum(String  mailType){
        MailTypeEnum[] mailEnum=this.values();
        for (MailTypeEnum nameEnum : mailEnum) {
            if(nameEnum.getValue().equals(mailType)){
                return nameEnum;
            }
        }
        return null;
    }
}
