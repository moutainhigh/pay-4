/**
 * 
 */
package com.pay.txncore.commons;


/**
 * 拒付数据字段
 *  File: BouncedEnum.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年5月16日   mmzhang     Create
 *
 */
public enum OrderCreditEnum {

		//creditFlag风控标志：1 未评分 ， 2 风控拒绝，3 风控通过，4 已经申请
		creditFlag1("1","未评分"),
		creditFlag2("2","风控拒绝"),
		creditFlag3("3","风控通过"),
		creditFlag4("4"," 已经申请"),
		//advanceFlag授信标志：0默认，1提前清算';
		advanceFlag0("0","默认"),
		advanceFlag1("1","提前清算"),
		advanceFlag2("2","提前清算完成"),
		//字段类型：0卡号，2ip，1邮箱'; 
		type0("0","卡号"),
		type1("1","邮箱"),
		type2("2","ip"),
		;
		
		private String type;
		
		private String name;
		
		private OrderCreditEnum(String type,String name){
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}
	public static void main(String[] args) {
	}
}
