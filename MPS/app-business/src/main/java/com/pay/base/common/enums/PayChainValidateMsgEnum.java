/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.common.enums;

/**
 * @author fjl
 * @date 2011-9-21
 */
public enum PayChainValidateMsgEnum {
	
	LEGAL(1,"合法的支付链"),
	
	NOT_EXISTS(100,"支付链不存在"),
	CLOSED(101,"支付链已经关闭"),
	EXPIRED(102,"支付链已过期");	
	
	private int code;
	private String msg;
	
	PayChainValidateMsgEnum(int code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public static PayChainValidateMsgEnum get(int code){
        for (PayChainValidateMsgEnum e : values()) {
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }

}
