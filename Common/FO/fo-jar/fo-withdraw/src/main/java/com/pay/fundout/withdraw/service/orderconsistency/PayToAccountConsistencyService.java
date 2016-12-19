package com.pay.fundout.withdraw.service.orderconsistency;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;

public interface PayToAccountConsistencyService {
	/**
	 * 搜索批量付款子订单状态为101
	 * @param map
	 * 交易流水号
	 * 起始时间和结束时间
	 * @return
	 * @throws Exception
	 */
	public List<Pay2AcctOrder> searchMassPayOrder101(Map<String,String> map) throws Exception;
	
	/**
	 * 搜索批量付款子订单状态为112
	 * @param map
	 * 交易流水号
	 * 起始时间和结束时间
	 * @return
	 * @throws Exception
	 */
	public List<Pay2AcctOrder> searchMassPayOrder112(Map<String,String> map) throws Exception;
	
	/**
	 * 批量付款子订单状态为101置为成功
	 * @param pay2AcctOrder
	 * @return
	 * @throws Exception
	 */
	public boolean massPayOrderProceedToSuccess(long orderId) throws Exception;
	/**
	 * 批量付款子订单状态为101置为失败
	 * @param pay2AcctOrder
	 * @return
	 * @throws Exception
	 */
	public boolean massPayOrderProceedToFail(long orderId) throws Exception;
	/**
	 * 批量付款子订单状态为112 创建退款单
	 * @param pay2AcctOrder
	 * @return
	 * @throws Exception
	 */
	public boolean createBackFundmentOrder(long orderId) throws Exception;
	
}
