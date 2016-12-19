/**
 * 
 */
package com.pay.acc.deal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.pay.acc.deal.dao.BalanceEntryDAO;
import com.pay.acc.deal.dto.BalanceEntryDto;
import com.pay.acc.deal.exception.BalanceException;
import com.pay.acc.deal.exception.BalanceUnkownException;
import com.pay.acc.deal.model.BalanceEntry;
import com.pay.acc.deal.service.BalanceEntryService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class BalanceEntryServiceImpl implements BalanceEntryService {

	private BalanceEntryDAO balanceEntryDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.deal.service.BalanceEntryService#createBalanceEntry(com.pay
	 * .acc.deal.model.BalanceEntry)
	 */
	@Override
	public Long createBalanceEntry(BalanceEntryDto balanceEntryDto)
			throws BalanceException, BalanceUnkownException {

		if (balanceEntryDto == null) {
			throw new BalanceException("输入的参数有误");
		}
		Long seqId = new Long(0);
		try {
			seqId = (Long) this.balanceEntryDAO.create(BeanConvertUtil.convert(
					BalanceEntry.class, balanceEntryDto));
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return seqId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.deal.service.BalanceEntryService#queryBalanceEntryForTransAmount
	 * (java.lang.String, java.lang.String)
	 */
	public Long queryBalanceEntryForTransAmount(String acctCode,
			String strEndDate) throws BalanceException, BalanceUnkownException {

		if (null == acctCode || !StringUtils.hasText(acctCode)) {
			throw new BalanceException("acctCode参数输入 " + acctCode + " 不正确");
		} else if (null == strEndDate || !StringUtils.hasText(strEndDate)) {
			throw new BalanceException("strEndDate参数输入 " + strEndDate + " 不正确");
		}

		Long transAmounts = 0L;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("acctCode", acctCode);
		paraMap.put("strEndDate", strEndDate);
		try {
			transAmounts = this.balanceEntryDAO
					.queryBalanceEntryForTransAmount(
							"queryBalanceEntryForTransAmount", paraMap);
		} catch (Exception e) {
			throw new BalanceUnkownException(e);
		}
		return transAmounts;
	}

	public void setBalanceEntryDAO(BalanceEntryDAO balanceEntryDAO) {
		this.balanceEntryDAO = balanceEntryDAO;
	}

	@Override
	public Long querySumTwoWeekAddAmount(String acctCode, Long date) {

		return this.balanceEntryDAO.sumTwoWeekAddValue(acctCode, date);
	}

	@Override
	public Long querySumTwoWeekMinusValue(String acctCode, Long date) {
		Long amount = this.balanceEntryDAO.sumTwoWeekPayeeMinusValue(acctCode,
				date)
				+ this.balanceEntryDAO
						.sumTwoWeekPayerMinusValue(acctCode, date);
		return amount;
	}

	@Override
	public Long queryWithdrawalBanalce(String acctCode, Long date) {

		return this.balanceEntryDAO.queryWithdrawalBanalce(acctCode, date);
	}

	@Override
	public Long sumTwoWeekMinusValue(String acctCode, Long date) {
		return this.balanceEntryDAO.sumTwoWeekMinusValue(acctCode, date);
	}

	@Override
	public List<BalanceEntry> queryBalanceEntryBySerialNo(String orderId,
			Integer dealCode) {
		return this.balanceEntryDAO.queryBalanceEntryBySerialNo(orderId,
				dealCode);
	}

	@Override
	public Long queryWithdrawalRefund(String acctCode, Long date) {
		return this.balanceEntryDAO.sumTwoWeekWithdrawRefund(acctCode, date);
	}

	@Override
	public List<BalanceEntry> queryCrdrSumByAcctCode(String acctCode,
			String startAt, String endAt) {
		return this.balanceEntryDAO.queryCrdrSumByAcctCode(acctCode, startAt,
				endAt);
	}

	@Override
	public Long queryBalanceByAcctCode(String acctCode, String endAt) {
		return this.balanceEntryDAO.queryBalanceByAcctCode(acctCode, endAt);
	}

	@Override
	public Long selectBalanceByAcctCodeAndDate(String acctCode, Date date) {
		BalanceEntry dto = this.balanceEntryDAO.selectBalanceByAcctCodeAndDate(
				acctCode, date);
		if (null != dto) {
			return dto.getBalance();
		}
		return 0L;
	}
}
