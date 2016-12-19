 /** @Description 
 * @project 	poss-refund
 * @file 		RefundBanlanceCheck.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-25		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**对应充值充退金额表信息
 * @author Sunsea.Li
 * @since 2010-9-25
 */
public class RefundBanlanceCheck extends BaseObject{
	private static final long serialVersionUID = 7435253694034579043L;
	private Long rechargeSeq;			//充值流水号
    private BigDecimal rechargeAmount;	//充值金额
    private BigDecimal refundAmount;	//已充退金额

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public Long getRechargeSeq() {
		return rechargeSeq;
	}

	public void setRechargeSeq(Long rechargeSeq) {
		this.rechargeSeq = rechargeSeq;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}