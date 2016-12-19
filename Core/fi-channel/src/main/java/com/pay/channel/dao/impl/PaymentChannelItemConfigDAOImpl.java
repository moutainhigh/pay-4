/**
 * 
 */
package com.pay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.PaymentChannelItemConfigDAO;
import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentChannelItemConfigDAOImpl extends
		BaseDAOImpl<PaymentChannelItemConfig> implements
		PaymentChannelItemConfigDAO {

	@Override
	public List<PaymentChannelItemConfig> queryPaymentChannelItemConfig(
			String paymentType, String memberCode) {
		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(paymentType)) {
			paraMap.put("paymentType", paymentType);
		}
		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode);
		}

		return super.findByCriteria(paraMap);
	}

	@Override
	public List<PaymentChannelItemConfig> findPaymentChannelItemConfig(
			String paymentType, String memberCode) {
		
		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(paymentType)) {
			paraMap.put("paymentType", paymentType);
		}
		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode);
		}

		return super.findByCriteria("findPaymentChannelItemConfig",paraMap);
	}

}
