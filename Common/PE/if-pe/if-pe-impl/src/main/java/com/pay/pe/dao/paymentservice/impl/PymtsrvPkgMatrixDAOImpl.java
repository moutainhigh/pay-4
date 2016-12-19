package com.pay.pe.dao.paymentservice.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.paymentservice.PymtsrvPkgMatrixDAO;
import com.pay.pe.model.PymtsrvPkgMatrix;

public class PymtsrvPkgMatrixDAOImpl extends IbatisDAOSupport implements
		PymtsrvPkgMatrixDAO {
	private static Log logger = LogFactory
			.getLog(PymtsrvPkgMatrixDAOImpl.class);

	/**
	 * 得到支付服务组代码.
	 * 
	 * @param dealcode
	 *            Integer 支付方式
	 * @param ordercode
	 *            Integer 订单类型
	 * @param paymethod
	 *            Integer 订单到帐方式
	 * @return Integer paymentservicepkgcode
	 */
	public Integer getPymtpkgcodeByMatrix(final Integer dealcode,
			final Integer ordercode, final Integer paymethod) {

		PymtsrvPkgMatrix pymtsrvPkgMatrix = new PymtsrvPkgMatrix();
		pymtsrvPkgMatrix.setDealCode(dealcode);
		pymtsrvPkgMatrix.setOrderType(ordercode);
		pymtsrvPkgMatrix.setOrderPayMethod(paymethod);

		PymtsrvPkgMatrix pymtsrvPkgMatrix2 = (PymtsrvPkgMatrix) super
				.findObjectBySelective(pymtsrvPkgMatrix);
		if (pymtsrvPkgMatrix2 == null) {
			logger.error("Error! dealcode=" + dealcode + " ; ordercode="
					+ ordercode + " ; paymethod=" + paymethod);
			return null;
		}
		return pymtsrvPkgMatrix2.getPaymentServicePkgCode();
	}

}
