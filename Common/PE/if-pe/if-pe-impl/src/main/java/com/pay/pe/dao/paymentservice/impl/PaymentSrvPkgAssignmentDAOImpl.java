package com.pay.pe.dao.paymentservice.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.paymentservice.PaymentServiceDAO;
import com.pay.pe.dao.paymentservice.PaymentServicePKGDAO;
import com.pay.pe.dao.paymentservice.PaymentSrvPkgAssignmentDAO;
import com.pay.pe.model.PaymentSrvPkgAssignment;

public final class PaymentSrvPkgAssignmentDAOImpl extends IbatisDAOSupport
		implements PaymentSrvPkgAssignmentDAO {

	private PaymentServiceDAO paymentSerDao;
	private PaymentServicePKGDAO paymentServicePKGDao;

	public PaymentServiceDAO getPaymentSerDao() {
		return paymentSerDao;
	}

	public void setPaymentSerDao(PaymentServiceDAO paymentSerDao) {
		this.paymentSerDao = paymentSerDao;
	}

	public PaymentServicePKGDAO getPaymentServicePKGDao() {
		return paymentServicePKGDao;
	}

	public void setPaymentServicePKGDao(
			PaymentServicePKGDAO paymentServicePKGDao) {
		this.paymentServicePKGDao = paymentServicePKGDao;
	}

	public PaymentSrvPkgAssignment addPaymentSrvPkgAssignment(
			final PaymentSrvPkgAssignment paymentSrvPkgAssignment) {
		return (PaymentSrvPkgAssignment) super
				.saveObject(paymentSrvPkgAssignment);
	}

	public void removePaymentSrvPkgAssignmentByCode(
			final Integer paymentSrvPkgCode, final Integer paymentServiceCode) {
		PaymentSrvPkgAssignment paymentSrvPkgAssignment = new PaymentSrvPkgAssignment();
		paymentSrvPkgAssignment.setPaymentServicePkgCode(paymentSrvPkgCode);
		paymentSrvPkgAssignment.setPaymentServiceCode(paymentServiceCode);
		super.deleteBySelective(paymentSrvPkgAssignment);
	}

	public void removePaymentSrvPkgAssignment(
			PaymentSrvPkgAssignment payAssignment) {
		Assert.notNull(payAssignment);
		this.removePaymentSrvPkgAssignmentByCode(
				payAssignment.getPaymentServicePkgCode(),
				payAssignment.getPaymentServiceCode());
	}

	/*
	 * dev need
	 */
	public List getAllPaymentSrvPkgAssignment(
			final Integer paymentservicepkgcode) {
		PaymentSrvPkgAssignment paymentSrvPkgAssignment = new PaymentSrvPkgAssignment();
		paymentSrvPkgAssignment.setPaymentServicePkgCode(paymentservicepkgcode);
		return super.findBySelective(paymentSrvPkgAssignment);
	}

	public List<PaymentSrvPkgAssignment> getAllAssignedToPaymentServicePKG(
			final Integer paymentservicecode) {
		PaymentSrvPkgAssignment paymentSrvPkgAssignment = new PaymentSrvPkgAssignment();
		paymentSrvPkgAssignment.setPaymentServiceCode(paymentservicecode);
		return super.findBySelective(paymentSrvPkgAssignment);
	}

	public PaymentSrvPkgAssignment addPaymentSrvPkgAssignmentByCode(
			final Integer paymentSrvPkgCode, final Integer paymentServiceCode) {
		PaymentSrvPkgAssignment assignment = new PaymentSrvPkgAssignment();
		assignment.setPaymentServiceCode(paymentServiceCode);
		assignment.setPaymentServicePkgCode(paymentSrvPkgCode);
		return (PaymentSrvPkgAssignment) this
				.addPaymentSrvPkgAssignment(assignment);
	}
}
