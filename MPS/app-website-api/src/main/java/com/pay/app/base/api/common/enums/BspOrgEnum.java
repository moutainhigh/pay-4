package com.pay.app.base.api.common.enums;

/**
 * 登录后的商户来源标识
 * @author JINHAHA
 *
 */
public enum BspOrgEnum {
	
	CORP_NORMAL(1,"普通商户"),
	CORP_GG(3,"广钢");
	
	
	private int org;
	private String message;
	
	
	private BspOrgEnum(int org,String message){
		this.org=org;
		this.message=message;
	}

	
	public static int getCompareValue(int org){
        for (BspOrgEnum nameEnum : values()) {
            if(nameEnum.getOrg()==org){
                return org;
            }
        }
        return 0;
	}
	
	public int getOrg() {
		return org;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
