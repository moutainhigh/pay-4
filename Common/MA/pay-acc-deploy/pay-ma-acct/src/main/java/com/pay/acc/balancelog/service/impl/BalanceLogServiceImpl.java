/**
 * 
 */
package com.pay.acc.balancelog.service.impl;

import com.pay.acc.balancelog.dao.BalanceLogDao;
import com.pay.acc.balancelog.dto.BalanceLogDto;
import com.pay.acc.balancelog.exception.BalanceLogException;
import com.pay.acc.balancelog.exception.BalanceLogUnknowExcetpion;
import com.pay.acc.balancelog.model.BalanceLog;
import com.pay.acc.balancelog.service.BalanceLogService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class BalanceLogServiceImpl implements BalanceLogService {
	private BalanceLogDao balanceLogDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.balancelog.service.BalanceLogService#createBalanceLog
	 * (com.pay.acc.balancelog.dto.BalanceLogDto)
	 */
	@Override
	public Long createBalanceLog(BalanceLogDto balanceLogDto) throws BalanceLogException, BalanceLogUnknowExcetpion {
		if (balanceLogDto == null) {
			throw new BalanceLogException("输入参数不能为空");
		}

		try {
			return (Long) this.balanceLogDao.create(BeanConvertUtil.convert(BalanceLog.class, balanceLogDto));
		} catch (Exception e) {

			throw new BalanceLogUnknowExcetpion(e);
		}

	}

	/**
	 * @param balanceLogDao
	 *            the balanceLogDao to set
	 */
	public void setBalanceLogDao(BalanceLogDao balanceLogDao) {
		this.balanceLogDao = balanceLogDao;
	}

}
