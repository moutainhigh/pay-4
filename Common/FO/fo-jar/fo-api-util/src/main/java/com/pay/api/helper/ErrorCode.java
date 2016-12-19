/**
 *  File: ErrorCode.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.helper;

/**
 * 付款api返回错误码
 */
public interface ErrorCode {

	String SUCCESS = "0000";
	String SUCCESS_DESC = "成功";
	String FAIL = "0001";
	String FAIL_DESC = "失败";
	String DOING = "0002";
	String DOING_DESC = "处理中";

	String ORDER_REPEAT = "1000";
	String ORDER_INVALID = "1001";
	String ORDER_INVALID_DESC = "订单不存在";
	String ORDERID_INVALID = "1002";
	String ORDERID_INVALID_DESC = "订单号验证错误";
	String SIGN_INVALID = "1003";
	String SIGN_INVALID_DESC = "验签失败";
	String AMOUNT_INVALID = "1004";
	String AMOUNT_INVALID_DESC = "金额或笔数验证错误";
	String BUSINESS_INVALID = "1005";
	String BUSINESS_INVALID_DESC = "暂不支持该银行";
	String REMARK_INVALID = "1006";
	String REMARK_INVALID_DESC = "附言验证错误";
	String BANKNAME_INVALID = "1007";
	String BANKNAME_INVALID_DESC = "银行名称验证错误";
	String BANKBRANCHE_INVALID = "1008";
	String BANKBRANCHE_INVALID_DESC = "开户行验证错误";
	String PROVINCE_INVALID = "1009";
	String PROVINCE_INVALID_DESC = "省份地区不正确";
	String CITY_INVALID = "1010";
	String CITY_INVALID_DESC = "城市名称不正确";
	String PAYEEACCOUNT_INVALID = "1011";
	String PAYEEACCOUNT_INVALID_DESC = "收款账户格式不正确";
	String CURRENCYCODE_INVALID = "1012";//1012	货币类型不正确
	String CURRENCYCODE_INVALID_DESC = "货币类型不正确";//1012	货币类型不正确
	String AUDITFLAG_INVALID = "1013";
	String AUDITFLAG_INVALID_DESC = "复核类型不正确";
	String FEETYPE_INVALID = "1014";
	String FEETYPE_INVALID_DESC = "手续费类型不正确";
	String PAYTYPE_INVALID = "1015";
	String PAYTYPE_INVALID_DESC = "付款类型不正确";
	String REQUESTTIME_INVALID = "1016";
	String REQUESTTIME_INVALID_DESC = "请求日期不正确";
	String VERSION_INVALID = "1017";
	String VERSION_INVALID_DESC = "接口版本不正确";
	String REQUESTITEM_INVALID = "1018";
	String REQUESTITEM_INVALID_DESC = "无请求明细数据";
	String BUSINESSNO_INVALID = "1019";
	String BUSINESSNO_INVALID_DESC = "批次号重复或验证不正确";
	String PAYEETYPE_INVALID = "1020";
	String PAYEETYPE_INVALID_DESC = "收款方类型不正确";
	String SIGNTYPE_INVALID = "1021";
	String SIGNTYPE_INVALID_DESC = "加密类型不正确";
	String AMOUNT_LIMIT_INVALID = "1022";
	String AMOUNT_LIMIT_INVALID_DESC = "实际出款金额不能小于申请金额";
	String QUERY_TIME_INVALID = "1023";
	String QUERY_TIME_INVALID_DESC = "查询时间格式不正确";
	String QUERY_TIME_LIMIT = "1024";
	String QUERY_TIME_LIMIT_DESC = "结束时间不能早于起始时间并且结束时间减去起始时间不能超过七天";
	String QUERY_TYPE_INVALID = "1025";
	String QUERY_TYPE_INVALID_DESC = "查询类型错误";

	String MERCHANT_INVALID = "2000";
	String MERCHANT_INVALID_DESC = "商户不存在";
	String MERCHANTSTATUS_INVALID = "2001";
	String MERCHANTSTATUS_INVALID_DESC = "商户状态异常";
	String MERCHANTACCOUNT_INVALID = "2002";
	String MERCHANTACCOUNT_INVALID_DESC = "商户账户状态异常";
	String MERCHANTBALANCE_NOTENOUGH = "2003";
	String MERCHANTBALANCE_NOTENOUGH_DESC = "商户余额不足";
	String EXCEED_LIMIT = "2004";
	String EXCEED_LIMIT_DESC = "超出限额";

	String MEMBER_INVALID = "3000";
	String MEMBER_INVALID_DESC = "收款方会员不存在";
	String MEMBERSTATUS_INVALID = "3001";
	String MEMBERSTATUS_INVALID_DESC = "收款方会员状态异常";
	String MEMBERACCOUNT_INVALID = "3002";
	String MEMBERACCOUNT_INVALID_DESC = "收款方会员账户状态异常";
	String MERCHANT_IS_MEMBER = "3003";
	String MERCHANT_IS_MEMBER_DESC = "不能付款给自己";
	String MEMBER_NAME_INVALID = "3004";
	String MEMBER_NAME_INVALID_DESC = "收款姓名验证异常";
	String CREDITCARD_INVALID = "3005";
	String MOBILE_INVALID = "3006";
	String MOBILE_INVALID_DESC = "收款方手机号码不正确";

	String AUDITING = "4000";
	String EXCEPTION = "9999";
}
