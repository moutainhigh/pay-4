package com.pay.pe.dao.paymentservice;

import java.util.List;

import com.pay.pe.model.PaymentServicePKG;

/**
 * @服务组的dao
 */
public interface PaymentServicePKGDAO {

	/**
	 * @插入一个payment服务组
	 * @param paymentServicePKG
	 *            PaymentServicePKG
	 * @return PaymentServicePKG
	 * @since 1.0
	 */
	PaymentServicePKG addPaymentServicePKG(
			final PaymentServicePKG paymentServicePKG);

	/**
	 * @更新服务组
	 * @param paymentServicePKG
	 *            PaymentServicePKG
	 * @return PaymentServicePKG
	 */
	PaymentServicePKG changePaymentServicePKG(
			PaymentServicePKG paymentServicePKG);

	/**
	 * @删除一个服务组
	 * @param paymentServicePKGCode
	 *            String
	 */
	void removePaymentServicePKG(final Integer paymentServicePKGCode);

	/**
	 * @得到所有服务组
	 * @return List < PaymentServicePKGDTO >
	 */
	List<PaymentServicePKG> getAllPaymentServicePKG();

	/**
	 * @根据code得到服务组
	 * @param paymentServicePKGCode
	 *            String
	 * @return PaymentServicePKG
	 */
	PaymentServicePKG getPaymentServicePKG(final Integer paymentServicePKGCode);

	/**
	 * @根据服务组名称等到所有服务组
	 * @param paymentServiceName
	 *            String
	 * @return < PaymentServicePKGDTO >
	 */
	List<PaymentServicePKG> getPaymentServicePKGByName(
			final String paymentServiceName);

}
