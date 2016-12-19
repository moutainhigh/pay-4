/**
 *  File: WithdrawOrderStatus.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.common.order;

/**
 * 出款订单状态枚举类
 * @author zliner
 *
 */
public enum WithdrawOrderStatus {
		APPLY(100, "申请失败"),//初始申请状态，还未记账
		INIT(101, "订单初始化"), //第一步申请记账成功，处于进行中的状态
		SUCCESS(111, "订单处理成功"), 
		FAIL(112, "订单处理失败"), 
		COMPLETE(114, "订单已退款");

		private int value;

		private String description;

		private WithdrawOrderStatus(int value, String desc) {
			this.value = value;
			this.description = desc;
		}

		/**
		 * Get Integer representation of the order status.
		 * 
		 * @return
		 */
		public int getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
		
		public static String getDescByValue(int value) {
			WithdrawOrderStatus[] orderStatus = WithdrawOrderStatus.values();
			for(int i=0; i<orderStatus.length; i++) {
				if(orderStatus[i].getValue() == value) {
					return orderStatus[i].getDescription();
				}
			}
			return null;
		}
}
