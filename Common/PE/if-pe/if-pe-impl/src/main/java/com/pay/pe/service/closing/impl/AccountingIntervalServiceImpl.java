package com.pay.pe.service.closing.impl;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.pe.dao.closing.AccountingIntervalDao;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.service.closing.AccountingIntervalService;

public class AccountingIntervalServiceImpl implements AccountingIntervalService {
	private AccountingIntervalDao accountingIntervalDao;
	
	public void setAccountingIntervalDao(AccountingIntervalDao accountingIntervalDao) {
		this.accountingIntervalDao = accountingIntervalDao;
	}

	public boolean isAvailable(Date date) {
		return accountingIntervalDao.isAvailable(date);
	}
	
	/**
	 * 检查list中的交易时间是否大于关帐时间
	 * 
	 * @param transcationTimes
	 *            交易时间列表
	 * @return
	 */
	public boolean isAvailable(Date[] transcationTimes) {
		Date date = accountingIntervalDao.getFirstAvailableDate();// 获得已关帐的日期
		
		if(date == null){
			return true;
		}
		
		for (Date transcationTime : transcationTimes) {
			if (transcationTime.getTime() - date.getTime() < 0) {// 交易时间小于关帐时间
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查list中的交易时间是否大于关帐时间
	 * 
	 * @param transcationTimes
	 *            交易时间列表
	 * @return
	 */
	public boolean isAvailable(List<AccountEntryDTO> entries) {
		Date date = this.accountingIntervalDao.getFirstAvailableDate();// 获得已关帐的日期
		
		if(date == null){
			return true;
		}

		if (entries != null && entries.size() > 0) {
			for (AccountEntryDTO entry : entries) {
				Assert.isTrue(entry != null
						&& entry.getTransactiondate()!= null,
						"entry and entry.tansactiontime must not be null and ");
				if (entry.getTransactiondate().getTime() - date.getTime() < 0) {// 交易时间小于关帐时间
					return false;
				}
			}
		}
		return true;

	}

}
