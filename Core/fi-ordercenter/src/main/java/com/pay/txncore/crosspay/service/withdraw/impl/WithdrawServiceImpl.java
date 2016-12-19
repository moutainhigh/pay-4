/**
 * 
 */
package com.pay.txncore.crosspay.service.withdraw.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.txncore.crosspay.dao.PartnerClearCycleDAO;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;
import com.pay.txncore.crosspay.service.PartnerWithdrawOrderService;
import com.pay.txncore.crosspay.service.withdraw.WithdrawService;

/**
 * @author chaoyue
 * 
 */
public class WithdrawServiceImpl implements WithdrawService {

	private PartnerWithdrawOrderService partnerWithdrawOrderService;
	private PartnerClearCycleDAO partnerClearCycleDao;

	public void setPartnerWithdrawOrderService(
			PartnerWithdrawOrderService partnerWithdrawOrderService) {
		this.partnerWithdrawOrderService = partnerWithdrawOrderService;
	}

	public void setPartnerClearCycleDao(
			PartnerClearCycleDAO partnerClearCycleDao) {
		this.partnerClearCycleDao = partnerClearCycleDao;
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

		return partnerWithdrawOrderService.sumWithdrawAmount(partnerId,
				startDate, endDate);
	}

	@Override
	public void withdraw(Long partnerId, Long amount) throws Exception {
		// 判断结算金额
		// 获取可提现金额
		Long withdrawableAmount = (Long) partnerClearCycleDao
				.findObjectByTemplate("getWithdrawableAmount", partnerId);
		if (null == withdrawableAmount) {
			throw new Exception("结算商户配置不存在");
		}
		if (amount > withdrawableAmount) {
			throw new Exception("提现金额大于可提现金额");
		}

		PartnerWithdrawOrder partnerWithdrawOrder = new PartnerWithdrawOrder();
		partnerWithdrawOrder.setAmount(amount);
		partnerWithdrawOrder.setCreateDate(new Date());
		partnerWithdrawOrder.setPartnerId(String.valueOf(partnerId));
		partnerWithdrawOrder.setStatus(1);
		partnerWithdrawOrderService
				.createPartnerWithdrawOrder(partnerWithdrawOrder);
		Map<String, Object> param_map = new HashMap<String, Object>();
		param_map.put("partnerId", partnerId);
		param_map.put("amount", (-amount));
		partnerClearCycleDao.updatePartnerWithDraw(param_map);
	}

	@Override
	public Long withdrawAbleAmount(Long partnerId) throws Exception {
		// 获取可提现金额
		Long withdrawableAmount = (Long) partnerClearCycleDao
				.findObjectByTemplate("getWithdrawableAmount", partnerId);
		if (null == withdrawableAmount) {
			throw new Exception("结算商户配置不存在");
		}
		return withdrawableAmount;
	}

	@Override
	public List<PartnerWithdrawOrder> queryWithdrawOrder(Long partnerId,
			String startDate, String endDate) {

		return partnerWithdrawOrderService.queryWithdrawOrder(partnerId,
				startDate, endDate);
	}

}
