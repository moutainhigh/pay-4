package com.pay.pe.dao.paymentservice;

import java.util.List;

import com.pay.pe.model.PaymentSrvPkgAssignment;

public interface PaymentSrvPkgAssignmentDAO {

	/**
	 * @插入一个支付组分配的支付服务
	 * @param paymentSrvPkgAssignment
	 *            PaymentSrvPkgAssignment
	 * @return PaymentSrvPkgAssignment
	 */
	PaymentSrvPkgAssignment addPaymentSrvPkgAssignment(
			PaymentSrvPkgAssignment paymentSrvPkgAssignment);

	/**
	 * @插入一个支付组分配的支付服务
	 * @param paymentSrvPkgCode
	 *            String
	 * @param paymentServiceCode
	 *            String
	 * @return PaymentSrvPkgAssignment
	 */
	PaymentSrvPkgAssignment addPaymentSrvPkgAssignmentByCode(
			Integer paymentSrvPkgCode, Integer paymentServiceCode);

	/**
	 * @删除一个支付组分配的支付服务
	 * @param paymentSrvPkgAssignmentCode
	 *            String
	 * @param paymentServiceCode
	 *            String
	 */
	void removePaymentSrvPkgAssignmentByCode(Integer paymentSrvPkgCode,
			Integer paymentServiceCode);

	/**
	 * @ 根据分配对象删除
	 * 
	 * @param payAssignment
	 *            PaymentSrvPkgAssignment
	 */
	void removePaymentSrvPkgAssignment(PaymentSrvPkgAssignment payAssignment);

	/**
	 * @根据paymentservicepkgcode得到所有PaymentSrvPkgAssignment对象
	 * @param paymentservicepkgcode
	 *            String
	 * @return List < PaymentSrvPkgAssignment >
	 */
	List getAllPaymentSrvPkgAssignment(Integer paymentservicepkgcode);

	/**
	 * @根据支付服务的paymentServiceCode得到所有PaymentSrvPkgAssignment对象
	 * @param paymentservicecode
	 *            String
	 * @return List < PaymentSrvPkgAssignment >
	 */
	List<PaymentSrvPkgAssignment> getAllAssignedToPaymentServicePKG(
			Integer paymentservicecode);
}
