package com.pay.pe.dao.paymentservice;

public interface PymtsrvPkgMatrixDAO {
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
			final Integer ordercode, final Integer paymethod);
}
