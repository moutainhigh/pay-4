package com.pay.txncore.crosspay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrderCriteria;
import com.pay.txncore.crosspay.service.PartnerWithdrawOrderService;
import com.pay.util.StringUtil;

public class PartnerWithdrawOrderServiceImpl implements
		PartnerWithdrawOrderService {

	private BaseDAO partnerWithdrawOrderDao;

	public void setPartnerWithdrawOrderDao(
			BaseDAO<PartnerWithdrawOrder> partnerWithdrawOrderDao) {
		this.partnerWithdrawOrderDao = partnerWithdrawOrderDao;
	}

	@Override
	public PartnerWithdrawOrder findById(Long id) {
		return (PartnerWithdrawOrder) partnerWithdrawOrderDao.findById(id);
	}

	@Override
	public List<PartnerWithdrawOrder> findByCriteria(
			PartnerWithdrawOrderCriteria criteria) {
		return partnerWithdrawOrderDao.findByCriteria(criteria);
	}

	@Override
	public long createPartnerWithdrawOrder(
			PartnerWithdrawOrder partnerWithdrawOrder) {
		return (Long) partnerWithdrawOrderDao.create(partnerWithdrawOrder);
	}

	@Override
	public boolean updatePartnerWithdrawOrder(
			PartnerWithdrawOrder partnerWithdrawOrder) {
		return partnerWithdrawOrderDao.update(partnerWithdrawOrder);
	}

	@Override
	public boolean deletePartnerWithdrawOrder(Long id) {
		return partnerWithdrawOrderDao.delete(id);
	}

	@Override
	public Page<PartnerWithdrawOrder> queryPartnerWithdrawOrderForPage(
			PartnerWithdrawOrderCriteria partnerWithdrawOrderCriteria,
			Page<PartnerWithdrawOrder> origPage) {
		// 转换成查询page对象
		List<PartnerWithdrawOrder> resultList = partnerWithdrawOrderDao
				.findByCriteria(partnerWithdrawOrderCriteria, origPage);
		origPage.setResult(resultList);
		// 转换成页面对象
		return origPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.crosspay.service.withdraw.WithdrawService#sumWithdrawAmount
	 * (java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public Long sumWithdrawAmount(Long partnerId, String startDate,
			String endDate) {

		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		if (!StringUtil.isEmpty(startDate)) {
			paraMap.put("startDate", startDate);
		}
		if (!StringUtil.isEmpty(endDate)) {
			paraMap.put("endDate", endDate);
		}
		return (Long) partnerWithdrawOrderDao.findObjectByTemplate(
				"sumWithdrawAmount", paraMap);
	}

	@Override
	public List<PartnerWithdrawOrder> queryWithdrawOrder(Long partnerId,
			String startDate, String endDate) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		return partnerWithdrawOrderDao.findByTemplate("queryWithdrawOrder",
				paraMap);
	}

	@Override
	public Page<PartnerWithdrawOrder> queryPartnerWithdrawOrderForPage(
			Map<String, Object> partnerWithdrawOrderCriteria,
			Page<PartnerWithdrawOrder> origPage) {
		// 转换成查询page对象
		List<PartnerWithdrawOrder> resultList = partnerWithdrawOrderDao
				.findByCriteria("queryWithdrawOrderList",
						partnerWithdrawOrderCriteria, origPage);
		origPage.setResult(resultList);
		// 转换成页面对象
		return origPage;

	}
}