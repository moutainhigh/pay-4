/**
 * 
 */
package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.PaymentOrderExpand;

/**
 * @Description 支付订单扩展表
 * @project gateway-pay
 * @file PaymentOrderExpandDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2011 HNA Corporation. All rights reserved. Date
 *          Author Changes 2010-7-13 Elx.OuYang Create 2010-10-28 mole_zou
 *          update
 */
public interface PaymentOrderExpandDAO extends BaseDAO<PaymentOrderExpand> {

	/**
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	public PaymentOrderExpand queryPayOrderExpandByPayNO(Long paymentOrderNo);

	/**
	 * 
	 * @param expand
	 */
	public void updatePayOrderExpandByPayNO(PaymentOrderExpand expand);
}
