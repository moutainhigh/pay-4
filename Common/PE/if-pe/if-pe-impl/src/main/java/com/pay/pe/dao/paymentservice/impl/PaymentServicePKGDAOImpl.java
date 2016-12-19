package com.pay.pe.dao.paymentservice.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.paymentservice.PaymentServicePKGDAO;
import com.pay.pe.model.PaymentServicePKG;

public final class PaymentServicePKGDAOImpl extends IbatisDAOSupport implements
		PaymentServicePKGDAO {

	private Log logger = LogFactory.getLog(getClass());

	public PaymentServicePKG addPaymentServicePKG(
			final PaymentServicePKG paymentServicePKG) {
		return (PaymentServicePKG) super.saveObject(paymentServicePKG);
	}

	public PaymentServicePKG changePaymentServicePKG(
			final PaymentServicePKG paymentServicePKG) {
		super.updateObject(paymentServicePKG);
		return paymentServicePKG;
	}

	public void removePaymentServicePKG(final Integer paymentServicePKGCode) {
		super.deleteObjectById(paymentServicePKGCode);
	}

	public List getAllPaymentServicePKG() {
		return (List) super.findBySelective(null);
	}

	/*
	 * dev need
	 */
	public PaymentServicePKG getPaymentServicePKG(
			final Integer paymentServicePKGCode) {
		return (PaymentServicePKG) super.findObjectById(paymentServicePKGCode);
	}

	public List getPaymentServicePKGByName(final String paymentServicePKGName) {
		PaymentServicePKG paymentServicePKG = new PaymentServicePKG();
		paymentServicePKG.setPaymentServicePackageName(paymentServicePKGName);
		return super.findBySelective(paymentServicePKG);
	}

}
