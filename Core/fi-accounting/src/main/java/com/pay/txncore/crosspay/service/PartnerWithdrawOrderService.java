package com.pay.txncore.crosspay.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrderCriteria;

public interface PartnerWithdrawOrderService {

	PartnerWithdrawOrder findById(Long id);

	long createPartnerWithdrawOrder(PartnerWithdrawOrder partnerWithdrawOrder);

	boolean updatePartnerWithdrawOrder(PartnerWithdrawOrder partnerWithdrawOrder);

	boolean deletePartnerWithdrawOrder(Long id);

	List<PartnerWithdrawOrder> findByCriteria(
			PartnerWithdrawOrderCriteria criteria);

	public Page<PartnerWithdrawOrder> queryPartnerWithdrawOrderForPage(
			PartnerWithdrawOrderCriteria partnerWithdrawOrderCriteria,
			Page<PartnerWithdrawOrder> origPage);

	public Page<PartnerWithdrawOrder> queryPartnerWithdrawOrderForPage(
			Map<String, Object> partnerWithdrawOrderCriteria,
			Page<PartnerWithdrawOrder> origPage);

	/**
	 * 统计历史提现金额
	 * 
	 * @param partnerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Long sumWithdrawAmount(Long partnerId, String startDate, String endDate);

	/**
	 * 
	 * @param partnerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PartnerWithdrawOrder> queryWithdrawOrder(Long partnerId,
			String startDate, String endDate);
}