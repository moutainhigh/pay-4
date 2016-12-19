package com.pay.pe.dao.paymentservice;

import java.util.List;
import java.util.Map;

import com.pay.pe.model.PaymentService;

public interface PaymentServiceDAO {
	
	/**
	 * @插入一种支付服务
	 * @param paymentService
	 *            PaymentService
	 * @return PaymentService
	 */
	PaymentService addPaymentService(PaymentService paymentService);

	/**
	 * @更新一种支付服务
	 * @param paymentService
	 *            PaymentService
	 * @return PaymentService
	 */
	PaymentService changePaymentService(PaymentService paymentService);

	/**
	 * @删除一种支付服务
	 * @param paymentServiceCode
	 *            String
	 */
	void removePaymentService(Integer paymentServiceCode);

	/**
	 * @获得所有支付服务
	 * @return List < PaymentServiceDTO >
	 */
	List<PaymentService> getAllPaymentService();

	/**
	 * @获得所有支付服务
	 * @return List < PaymentServiceDTO >
	 */
	List<PaymentService> getAllPaymentServiceByDTO(Map map);

	/**
	 * @更新
	 * @return PaymentService
	 */
	PaymentService changePaymentService();

	/**
	 * @得到一种支付服务
	 * @param paymentServiceCode
	 * @return
	 */
	PaymentService getPaymentService(Integer paymentServiceCode);

	/**
	 * @ 根据paymentServiceName得到支付服务对象
	 * 
	 * @param paymentServiceName
	 *            String
	 * @return List < PaymentService >
	 */
	List<PaymentService> getAllPaymentServiceByName(String paymentServiceName);

	/**
	 * 查询在某一支付组paymentServicePkgCode下，某个支付服务类型的所有支付服务。
	 * 
	 * @param paymentServicePkgCode
	 *            支付组
	 * @param paymentServiceCodeType
	 *            支付服务类型
	 * @return 所以支付服务
	 */
	List<PaymentService> getPaymentServices(
			final Integer paymentServicePkgCode,
			final Integer paymentServiceCodeType);
}
