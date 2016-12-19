/**
 * 
 */
package com.pay.acc.deal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.deal.dao.BalanceDealDAO;
import com.pay.acc.deal.dto.BalanceDealDto;
import com.pay.acc.deal.dto.BalanceDealSimpleDto;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.model.BalanceDeal;
import com.pay.acc.deal.service.BalanceDealService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class BalanceDealServiceImpl implements BalanceDealService {

	private BalanceDealDAO balanceDealDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.deal.service.BalanceDealService#createBalanceDeal(com.pay
	 * .acc.deal.model.BalanceDeal)
	 */
	@Override
	public Long createBalanceDeal(BalanceDealDto balanceDealDto)
			throws BalanceException, BalanceUnkownException {
		if (balanceDealDto == null) {
			throw new BalanceException("输入的参数有误");
		}
		Long seqId = new Long(0);
		try {
			seqId = (Long) this.balanceDealDAO.create(BeanConvertUtil.convert(
					BalanceDeal.class, balanceDealDto));
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return seqId;
	}

	@Override
	public BalanceDealSimpleDto queryBalanceDealSimpleInfoWithSerialNo(
			Long serialNo, Long amount) throws BalanceException,
			BalanceUnkownException {
		if (serialNo == null || serialNo.longValue() <= 0 || amount == null) {
			throw new BalanceException("输入的参数有误");
		}
		BalanceDealSimpleDto balanceDealSimpleDto = null;
		try {
			balanceDealSimpleDto = this.balanceDealDAO
					.queryBalanceDealSimpleInfoWithSerialNo(serialNo, amount);
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return balanceDealSimpleDto;
	}

	public List<BalanceDeal> queryWithdrawDealByAcctCodeAndDate(
			String acctCode, Date fromDate, Date toDate) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("acctCode", acctCode);
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		return balanceDealDAO.findByTemplate(
				"queryWithdrawDealByAcctCodeAndDate", paraMap);
	}

	public void setBalanceDealDAO(BalanceDealDAO balanceDealDAO) {
		this.balanceDealDAO = balanceDealDAO;
	}

	@Override
	public Integer queryDealInfoNum(String serialNo, Integer dealCode,
			Long amount) throws BalanceException, BalanceUnkownException {
		if (serialNo == null || serialNo.length() <= 0 || dealCode == null
				|| amount == null) {
			throw new BalanceException("输入的参数有误");
		}
		Integer num = null;
		try {
			num = this.balanceDealDAO.queryDealInfoCounts(serialNo, dealCode,
					amount);
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return num;
	}

	@Override
	public Integer queryDealInfoCountsByVo(Integer dealType, Long voucherNo)
			throws BalanceException, BalanceUnkownException {
		if (voucherNo == null || dealType == null) {
			throw new BalanceException("请输入有效的的凭证号或者交易类型");
		}
		Integer num = null;
		try {
			num = this.balanceDealDAO.queryDealInfoCountsByVo(dealType,
					voucherNo);
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.deal.service.BalanceDealService#updateChargeUpStatus(java
	 * .lang.Long, java.lang.Integer)
	 */
	@Override
	public Integer updateChargeUpStatus(Long seqId, Integer chargeUpStatus) {
		return this.balanceDealDAO.updateDealInfoChargeStatus(seqId,
				chargeUpStatus);
	}

	@Override
	public BalanceDealDto queryBalanceDealForFlushes(String orderId,
			Integer dealCode) {
		BalanceDeal deal = this.balanceDealDAO.queryBalanceDealForFlushes(
				orderId, dealCode);
		if (null == deal) {
			return null;
		}
		return BeanConvertUtil.convert(BalanceDealDto.class, deal);
	}

	@Override
	public boolean updateDealType(Long id, Integer dealType) {
		return this.balanceDealDAO.updateDealInfoChargeDealType(id, dealType) == 1 ? true
				: false;
	}

}
