/**
 *  File: UserLogURL.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-9   lihua     Create
 *
 */
package com.pay.app.common.userlogurl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 */
public class UserLogURL {

	private static Map<String, String> map;

	static {
		map = new HashMap<String, String>();
		map.put("/index.htm", UserLogType.LOGIN.getTypeId() + ","
				+ UserLogType.LOGIN.getTypeInfo());// 登录
		map.put("/login.htm", UserLogType.LOGIN.getTypeId() + ","
				+ UserLogType.LOGIN.getTypeInfo());// 登录
		map.put("/acctCharge.htm", UserLogType.COST.getTypeId() + ","
				+ UserLogType.COST.getTypeInfo());// 充值
		map.put("/insertlinker.htm", UserLogType.ADDLINKER.getTypeId() + ","
				+ UserLogType.ADDLINKER.getTypeInfo());// 添加联系人
		map.put("/paypassword.htm", UserLogType.UPDATEPAYPWD.getTypeId() + ","
				+ UserLogType.UPDATEPAYPWD.getTypeInfo());// 修改支付密码
		map.put("/sendlinktoemail.htm", UserLogType.FINDPAYPWD.getTypeId() + ","
				+ UserLogType.FINDPAYPWD.getTypeInfo());// 找回支付密码-通过邮件
		map.put("/updatepaypwdbysafequestion.htm", UserLogType.FINDPAYPWD
				.getTypeId()
				+ "," + UserLogType.FINDPAYPWD.getTypeInfo());// 找回支付密码-通过安全问题
		map.put("/updategreeting.htm", UserLogType.UPDATEGREETING.getTypeId()
				+ "," + UserLogType.UPDATEGREETING.getTypeInfo());// 修改问候语
		map.put("", UserLogType.PAY.getTypeId() + ","
				+ UserLogType.PAY.getTypeInfo());// 余额支付
		map.put("", UserLogType.PAY.getTypeId() + ","
				+ UserLogType.PAY.getTypeInfo());// 网银支付
		map.put("/updatesafequestion.htm", UserLogType.SAFEQUESTION.getTypeId()
				+ "," + UserLogType.SAFEQUESTION.getTypeInfo());// 设置安全问题
		map.put("/queryBalanceList.htm", UserLogType.QUERYBALANCE.getTypeId()
				+ "," + UserLogType.QUERYBALANCE.getTypeInfo());// 余额查询
		map.put("/queryHistoryTradeList.htm", UserLogType.QUERYTRADE.getTypeId()
				+ "," + UserLogType.QUERYTRADE.getTypeInfo());// 交易记录明细
		map.put("/queryHistoryRefundList.htm", UserLogType.QUERYTRADE
				.getTypeId()
				+ "," + UserLogType.QUERYTRADE.getTypeInfo());// 退款记录明细
		map.put("/pay2accountPay.htm", UserLogType.PAYMENT.getTypeId() + ","
				+ UserLogType.PAYMENT.getTypeInfo());// 付款
		map.put("", UserLogType.CASH.getTypeId() + ","
				+ UserLogType.CASH.getTypeInfo());// 提现
		map.put("", UserLogType.COMPLETEUSERINFO.getTypeId() + ","
				+ UserLogType.COMPLETEUSERINFO.getTypeInfo());// 补全资料
	}

	public String getAction(String url) {
		return this.getStr(this.map.get(url),1);
	}
	/**
	 * 获取指定位置字符串
	 * @param str
	 * @param i 
	 * @return
	 */
	private String getStr(String str,int i){
		String[] temp = StringUtils.split(str, ",");
		if (temp != null && temp.length > i) {
			return temp[i];
		} else {
			return "";
		}
	}
	
	public String getLogType(String url) {
		return this.getStr(this.map.get(url),0);
	}

	public static void main(String[] args) {
		UserLogURL userLogURL = new UserLogURL();
		System.out.print(userLogURL.getLogType("/index.htm"));
	}
}
