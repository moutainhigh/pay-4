/**
 * 
 */
package com.pay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.PaymentChannelItemDAO;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentChannelItemDAOImpl extends BaseDAOImpl<PaymentChannelItem>
		implements PaymentChannelItemDAO {

	@Override
	public PaymentChannelItem findByOrgCode(String orgCode) {
		return super.findObjectByCriteria("findByOrgCode", orgCode);
	}

	@Override
	public List<PaymentChannelItem> queryPaymentChannelItemConfig(
			String paymentType, String memberCode, String orgCode) {
		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(paymentType)) {
			paraMap.put("paymentType", paymentType);
		}
		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode);
		}
		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		return super.findByCriteria("queryPaymentChannelItemConfig",paraMap);
	}

	@Override
	public List<PaymentChannelItem> queryPaymentChannelItem(
			Map<String, Object> params) {
		return super.findByCriteria("queryPartnerSecondChannel",params);
	}

	@Override
	public List<PaymentChannelItem> queryPaymentChannelItemConfig(
			Map<String, Object> params) {
		return super.findByCriteria("queryPaymentChannelItemConfig2",params);
	}

}
