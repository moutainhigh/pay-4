package com.pay.acc.balancelog.service;

import com.pay.acc.balancelog.dto.BalanceLogDto;
import com.pay.acc.balancelog.exception.BalanceLogException;
import com.pay.acc.balancelog.exception.BalanceLogUnknowExcetpion;

public interface BalanceLogService {
	/**
	 * 保存余额日志
	 * @param balanceLogDto
	 * @return
	 * @throws BalanceLogException 
	 * @throws BalanceLogUnknowExcetpion 
	 */
	public Long createBalanceLog(BalanceLogDto balanceLogDto) throws BalanceLogException, BalanceLogUnknowExcetpion;

}
