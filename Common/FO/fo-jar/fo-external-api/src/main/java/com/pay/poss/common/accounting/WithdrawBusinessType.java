/**
 *  File: WithdrawBusinessType.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.common.accounting;
/**
 * 出款业务类型枚举类
 * @author zliner
 *
 */
public enum WithdrawBusinessType {
	WITHDRAW_REQ_PERSON("10", "个人提现申请成功"),
	WITHDRAW_ORDER_SUCC_PERSON("11", "个人提现订单成功"),
	WITHDRAW_ORDER_FAIL_PERSON("12", "个人提现订单失败"),
	WITHDRAW_REFUNDORDER_PERSON("13", "个人提现退款订单失败"),
	ACCTCHARGE_REFUND_REQ_PERSON("14", "个人充退请求成功"),
	ACCTCHARGE_REFUND_ORDER_SUCC_PERSON("15","个人充退订单成功"),
	ACCTCHARGE_REFUND_ORDER_FAIL_PERSON("16","个人充退订单失败"),
	PAYTOBANK_REQ_PERSON("17","个人付款到银行卡提交请求"),
	PAYTOBANK_ORDER_SUCC_PERSON("18","个人付款到银行卡出款成功"),
	PAYTOBANK_ORDER_FAIL_PERSON("19","个人付款到银行卡出款失败"),
	PAYTOACCT_ORDER_PERSON_I("20","付款到账户内部"),
	PAYTOACCT_ORDER_PERSON_O("21","付款到账户外部"),
	PAYTOACCT_BATCHORDER_REQ_PERSON("22","批量付款到账户提交请求"),
	PAYTOACCT_BATCHORDER_SUCC_PERSON("23","批量付款到账户订单成功"),
	PAYTOACCT_BATCHORDER_FAIL_PERSON("24","批量付款到账户订单失败"),
	BANKREFUND_ORDER_REQ("25","银行退款请求"),
	BANKREFUND_ORDER_SUCC("26","银行退款成功"),
	BANKREFUND_ORDER_FAIL("27","银行退款失败"),
	CREDITCARD_REPAY_REQ("28","信用卡还款申请成功"),
	CREDITCARD_REPAY_SUCC("29","信用卡还款成功"),
	CREDITCARD_REPAY_FAIL("30","信用卡还款失败"),
	BANKREFUND_WITHDRAW_ORDER_SUCC("260","提现银行退款成功"),
	BANKREFUND_PAY2BANK_ORDER_SUCC("263","付款到银行退款成功"),
	MASSPAYTOBANK_ORDER_REQ("31","批量付款到银行申请"),
	MASSPAYTOBANK_ORDER_SUCC("32","批量付款到银行单笔出款成功"),
	MASSPAYTOBANK_ORDER_FAIL("33","批量付款到银行单笔出款失败"),
	MASSPAYTOBNAK_ORDER_REJECT("34","批量付款到银行复核拒绝"),
	WITHDRAWCHANNEL_CLOSED("35","出款渠道关闭"),
	FUNDADJUSTMENT_ORDER_REQ("36","资金调拨申请"),
	FUNDADJUSTMENT_ORDER_SUCC("37","资金调拨成功"),
	FUNDADJUSTMENT_ORDER_FAIL("38","资金调拨失败");
	
	
	
	//业务类型
	private final String businessType;
	//业务描述
    private final String description;
    /**
     * 私有构造方法
     * @param businessType
     * @param description
     */
    private WithdrawBusinessType(String businessType, String description) {
        this.businessType = businessType;
        this.description = description;
    }

    /**
     * @return Returns the code.
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>businessType</code>获得枚举
     * @param businessType  
     * @return WithdrawBusinessType
     */
    public static WithdrawBusinessType getByBusinessType(String businessType) {
        for (WithdrawBusinessType type : values()) {
            if (type.getBusinessType().equals(businessType)) {
                return type;
            }
        }
        return null;
    }
}
