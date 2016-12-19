/**
 *  File: WithdrawOrderCallBackType.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.ordercallback;
/**
 * 出款业务类型枚举类
 * @author zliner
 *
 */
public enum WithdrawOrderCallBackType {
	WITHDRAW_REQ_PERSON("10101", "个人提现申请成功"),
	WITHDRAW_ORDER_SUCC_PERSON("11111", "个人提现订单成功"),
	WITHDRAW_ORDER_FAIL_PERSON("12112", "个人提现订单失败"),
	WITHDRAW_REFUNDORDER_PERSON("13111", "个人提现退款订单成功"),
	ACCTCHARGE_REFUND_REQ_PERSON("14101", "个人充退请求成功"),
	ACCTCHARGE_REFUND_ORDER_SUCC_PERSON("15111","个人充退订单成功"),
	ACCTCHARGE_REFUND_ORDER_FAIL_PERSON("16112","个人充退订单失败"),
	PAYTOBANK_REQ_PERSON("17101","个人付款到银行卡提交请求"),
	PAYTOBANK_ORDER_SUCC_PERSON("18111","个人付款到银行卡出款成功"),
	PAYTOBANK_ORDER_FAIL_PERSON("19112","个人付款到银行卡出款失败"),
	PAYTOACCT_ORDER_SUCC_PERSON("20111","个人付款到账户成功");
	//业务类型
	private final String businessType;
	//业务描述
    private final String description;
    /**
     * 私有构造方法
     * @param businessType
     * @param description
     */
    private WithdrawOrderCallBackType(String businessType, String description) {
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
     * @return WithdrawOrderCallBackType
     */
    public static WithdrawOrderCallBackType getByBusinessType(String businessType) {
        for (WithdrawOrderCallBackType type : values()) {
            if (type.getBusinessType().equals(businessType)) {
                return type;
            }
        }
        return null;
    }
}
