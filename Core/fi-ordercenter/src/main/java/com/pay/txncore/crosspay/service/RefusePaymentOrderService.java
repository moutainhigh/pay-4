package com.pay.txncore.crosspay.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dto.acquire.RefusePaymentOrderDTO;
import com.pay.txncore.crosspay.model.RefusePaymentOrder;
import com.pay.txncore.crosspay.model.RefusePaymentOrderCriteria;

public interface RefusePaymentOrderService {

	RefusePaymentOrder findById(Long id);

	long createRefusePaymentOrder(RefusePaymentOrder refusePaymentOrder);

	boolean updateRefusePaymentOrder(RefusePaymentOrder refusePaymentOrder);

	boolean deleteRefusePaymentOrder(Long id);

	List<RefusePaymentOrder> findByCriteria(RefusePaymentOrderCriteria criteria);

	public Page<RefusePaymentOrder> queryRefusePaymentOrderForPage(
			Map<String, Object> refusePaymentOrderCriteria,
			Page<RefusePaymentOrder> origPage);

	public Page<RefusePaymentOrder> queryRefusePaymentOrderForPage1(
			Map<String, Object> refusePaymentOrderCriteria,
			Page<RefusePaymentOrder> origPage);

	/**
	 * 拒付申请
	 * 
	 * @param conMap
	 * @throws Exception
	 */
	public void refuseOrderApp(Map<String, Object> conMap) throws Exception;

	/**
	 * 查询拒付订单详情
	 * 
	 * @param conMap
	 * @return
	 */
	public Map<String, Object> getRefuseOrder(Map<String, Object> conMap);

	/**
	 * 更新拒付订单状态
	 * 
	 * @param conMap
	 * @throws Exception
	 */
	public void updateRefuseOrderStatus(Map<String, Object> conMap)
			throws Exception;

	public Page<RefusePaymentOrderDTO> selectRefusePamentOrderWebSite(
			RefusePaymentOrderCriteria refusePaymentOrderCriteria,
			Page<RefusePaymentOrderDTO> origPage);
}