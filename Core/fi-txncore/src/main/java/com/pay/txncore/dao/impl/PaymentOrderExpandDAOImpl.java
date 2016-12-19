/**
 * 
 */
package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.model.PaymentOrderExpand;

/**
 * @Description 支付订单扩展表
 * @project gateway-pay
 * @file PaymentOrderExpandDAOImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2011 HNA Corporation. All rights reserved. Date
 *          Author Changes 2010-7-14 Elx.OuYang Create
 */
@SuppressWarnings("unchecked")
public class PaymentOrderExpandDAOImpl extends BaseDAOImpl<PaymentOrderExpand>
		implements PaymentOrderExpandDAO {

	public PaymentOrderExpand queryPayOrderExpandByPayNO(Long paymentOrderNo) {
		PaymentOrderExpand payOrderExpand = (PaymentOrderExpand) getSqlMapClientTemplate()
				.queryForObject(
						"paymentOrderExpand.queryPayOrderExpandByPayNO",
						paymentOrderNo);
		return payOrderExpand;
	}

	public void updatePayOrderExpandByPayNO(PaymentOrderExpand expand) {
		getSqlMapClientTemplate().update(
				"paymentOrderExpand.updatePayOrderExpandByPayNO", expand);

	}
}
