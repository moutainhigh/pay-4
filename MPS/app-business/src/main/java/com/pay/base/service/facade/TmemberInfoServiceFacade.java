package com.pay.base.service.facade;

public interface TmemberInfoServiceFacade {
	/**
	 * 查询交易明细URL
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryTransactionDetailURL(String partner) throws Exception;
}
