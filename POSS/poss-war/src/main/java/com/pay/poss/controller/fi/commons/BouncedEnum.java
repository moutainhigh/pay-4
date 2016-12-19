/**
 * 
 */
package com.pay.poss.controller.fi.commons;


/**
 * 拒付数据字段
 *  File: BouncedEnum.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年5月16日   mmzhang     Create
 *
 */
public enum BouncedEnum {

	//拒付类型 0拒付1调单2内部调单
		bouncedType0("0","拒付"),
		bouncedType1("1","银行调单"),
		bouncedType2("2","内部调单"),
		//拒付金额状态cpFalg：1-全额，2-部分
		cpFalg1("1","全额"),
		cpFalg2("2","部分"),
		//登记状态RegisterFlag1已登记，0未登记
		registerFlag1("1","已登记"),
		registerFlag0("0","未登记"),
		registerFlag2("2","已删除"),
		//amountType资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
		amountType1("1","直接扣款"),
		amountType2("2","已扣款"),
		amountType3("3","冻结"),
		amountType4("4","已冻结"),
		amountType5("5","解冻"),
		amountType6("6","已解冻"),
		amountType7("7","申诉失败扣款"),
		amountType8("8","申诉失败扣款成功"),
		//匹配状态:0为无匹配 1已匹配2退款中 3匹配多笔4未清算
		status0("0","无匹配"),
		status1("1","已匹配"),
		status2("2","退款中"),
		status3("3","匹配多笔"),
		status4("4","未清算或清算失败"),
		status5("5","批量异常"),
		status6("6","可删除"),
		//1.拒付订单的审批状态：0未处理 1已上传资料2已递交银行3申诉失败待复核4申诉成功待复核5申诉失败6申诉成功7不申诉
		statusAudit0("0","未处理"),
		statusAudit1("1","已上传资料"),
		statusAudit2("2","已递交银行"),
		statusAudit3("3","申诉失败待复核"),
		statusAudit4("4","申诉成功待复核"),
		statusAudit5("5","申诉失败"),
		statusAudit6("6","申诉成功"),
		statusAudit7("7","不申诉"),
		//accountingFlg 是否扣款记账：0登记未记账，1-登记不够扣款，任务循环扣款 3 调单后拒付，以前调单解冻标志
		accountingFlg0("0","登记未记账"),
		accountingFlg1("1","登记不够扣款，任务循环扣款"),
		accountingFlg2("2","调单后拒付，以前调单解冻"),
		//字段类型：0卡号，2ip，1邮箱'; 
		batchStatus0("0","未提交"),
		batchStatus1("1","已提交"),
		//gc标志：8 gc，5 ipay'; 
		gcflag8("8","gc"),
		gcflag5("5","ipay"),
		//调单标志：0 初始化，1 发生调单 
		checkflag0("0","初始化"),
		checkflag1("1","发生调单")
		;
		
		private String type;
		
		private String name;
		
		private BouncedEnum(String type,String name){
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
		System.out.print(BouncedEnum.amountType8.getType());
	}
}
