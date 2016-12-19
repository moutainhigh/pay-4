/**
 * 
 */
package com.pay.channel.dao.impl;

import com.pay.channel.dao.PaymentChannelCategoryDAO;
import com.pay.channel.model.PaymentChannelCategory;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author chaoyue
 *
 */
public class PaymentChannelCategoryDAOImpl extends
		BaseDAOImpl<PaymentChannelCategory> implements
		PaymentChannelCategoryDAO {

	@Override
	public PaymentChannelCategory queryByCode(String code) {

		return super.findObjectByCriteria("findByCode", code);
	}

}
