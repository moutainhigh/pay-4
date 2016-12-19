/**
 * 
 */
package com.pay.channel.dao;

import java.util.List;
import java.util.Map;

import com.pay.channel.model.PaymentChannelItem;
import com.pay.inf.dao.BaseDAO;

/**
 * @author chaoyue
 *
 */
public interface PaymentChannelItemDAO extends BaseDAO<PaymentChannelItem> {

	PaymentChannelItem findByOrgCode(String orgCode);

	List<PaymentChannelItem> queryPaymentChannelItemConfig(String paymentType,
			String memberCode, String orgCode);
	
	List<PaymentChannelItem> queryPaymentChannelItem(Map<String,Object> params);
	
	List<PaymentChannelItem> queryPaymentChannelItemConfig(Map<String,Object> params);

}
