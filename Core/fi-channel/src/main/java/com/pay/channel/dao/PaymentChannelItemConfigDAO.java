/**
 * 
 */
package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.inf.dao.BaseDAO;

/**
 * @author huhb
 * 
 */
public interface PaymentChannelItemConfigDAO extends
		BaseDAO<PaymentChannelItemConfig> {

	/**
	 * 获取商户配置通道
	 * 
	 * @param paymentType
	 * @param memberCode
	 * @return
	 */
	List<PaymentChannelItemConfig> queryPaymentChannelItemConfig(
			String paymentType, String memberCode);
	
	List<PaymentChannelItemConfig> findPaymentChannelItemConfig(String paymentType, String memberCode);
}
