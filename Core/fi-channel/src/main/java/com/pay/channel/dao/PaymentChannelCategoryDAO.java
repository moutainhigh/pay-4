/**
 * 
 */
package com.pay.channel.dao;

import com.pay.channel.model.PaymentChannelCategory;
import com.pay.inf.dao.BaseDAO;

/**
 * @author chaoyue
 *
 */
public interface PaymentChannelCategoryDAO extends
		BaseDAO<PaymentChannelCategory> {

	/**
	 * 根据CODE查询类别
	 * 
	 * @param code
	 * @return
	 */
	PaymentChannelCategory queryByCode(String code);
}
