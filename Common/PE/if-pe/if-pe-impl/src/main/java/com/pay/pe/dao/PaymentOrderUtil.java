package com.pay.pe.dao;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.util.MfDate;
import com.pay.util.RandomUtil;
import com.pay.util.StringUtil;

public class PaymentOrderUtil {
	private IbatisDAOSupport defaultDao;

	/**
	 * 生成paymentorderid.
	 * 
	 * @param orderType
	 * @return String
	 */
	public String generateOrderId(int orderType) {
		Assert.notNull(orderType);
		StringBuffer orderId = new StringBuffer();
		orderId.append(orderType)
				.append(new MfDate().toString("yyyyMMdd"))
				.append(StringUtil.fillZero(
						this.defaultDao.getNextID("SEQ_PAYMENTORDER_ORDERID")
								.toString(), 10))
				.append(RandomUtil.randomDegital(4));
		return orderId.toString();
	}

	public void setDefaultDao(IbatisDAOSupport defaultDao) {
		this.defaultDao = defaultDao;
	}
}
