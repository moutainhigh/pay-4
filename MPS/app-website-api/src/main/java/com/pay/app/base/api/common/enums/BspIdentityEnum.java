package com.pay.app.base.api.common.enums;




/**
 * 登录后的商户身份标识
 * @author JINHAHA
 *
 */
public enum BspIdentityEnum {
	
	CORP_NORMAL(0,"普通商户"),
	CORP_TRADINGMANAGER(1,"交易中心管理员"),
	CORP_TRADING(2,"交易商");
	
	private int identity;
	private String message;
	
	
	private BspIdentityEnum(int identity,String message){
		this.identity=identity;
		this.message=message;
	}
	
	public static int getCompareValue(int identity){
	        for (BspIdentityEnum nameEnum : values()) {
	            if(nameEnum.getIdentity()==identity){
	                return identity;
	            }
	        }
	        return 0;
	}
	
	public int getIdentity() {
		return identity;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
