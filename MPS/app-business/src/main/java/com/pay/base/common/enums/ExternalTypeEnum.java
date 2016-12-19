/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.common.enums;

/**
 * @author fjl
 * @date 2011-9-20
 */
public enum ExternalTypeEnum {
	YCARD(1,"易卡充值"),PAY_CHAIN(2,"支付链");
	
	private int type;
	private String memo;
	
	ExternalTypeEnum(int type,String memo){
		this.type = type;
		this.memo = memo;
	}

	/**
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getMemo() {
		return memo;
	}
	
	
	/**
	 * @param type
	 * @return
	 */
	public static ExternalTypeEnum getByCode(int type) {
		for (ExternalTypeEnum item : values()) {
			if (item.type == type) {
				return item;
			}
		}
		return null;
	}
	
}
