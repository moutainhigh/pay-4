/**
 *  File: WithdrawOrderBusiType.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-4      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.order;


/**
 * @author bill_peng
 *
 */
public enum WithdrawOrderBusiType {
	
	 UN_DEFINE_BUSI_TYPE(999,"未定义业务类型"),
	 WITHDRAW(0,"提现"),
	 Batch_FO(1,"批量出款"),
	 CREDIT_CARD_REPAYMENT(2,"信用卡还款"),
	 PAY2BANK(3,"付款到银行"),
	 MASSPAY2BANK(4,"批量银行卡还款");
	 
	 private final int code;
	 private final String description;
	 
	 /** 
	     * 
	     * 私有构造方法
	     * @param code
	     * @param description
	     */
	    private WithdrawOrderBusiType(int code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    /**
	     * @return Returns the code.
	     */
	    public int getCode() {
	        return code;
	    }

	    /**
	     * @return Returns the description.
	     */
	    public String getDescription() {
	        return description;
	    }

	    /**
	     * 通过枚举<code>code</code>获得枚举
	     * @param code
	     * @return
	     */
	    public static WithdrawOrderBusiType valueof(int code) {
	        for (WithdrawOrderBusiType busiType : values()) {
	            if (busiType.getCode()== code) {
	                return busiType;
	            }
	        }
			return UN_DEFINE_BUSI_TYPE;
	    }
}
