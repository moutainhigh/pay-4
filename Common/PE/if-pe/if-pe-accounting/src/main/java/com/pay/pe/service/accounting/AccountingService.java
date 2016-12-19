package com.pay.pe.service.accounting;

import java.util.List;

import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingEntryDetailDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;

public interface AccountingService {

	/**
	 * 获取订单类型
	 * 
	 * @return
	 */
	Integer getOrderCode();

	/**
	 * 获取交易类型
	 * 
	 * @return
	 */
	Integer getDealCode();

	/**
	 * 
	 * @param accountingDto
	 * @return
	 */
	List<AccountingEntryDetailDto> generateDealEntry(AccountingDto accountingDto)
			throws Exception;

	/**
	 * 调用pe算费，不产生分录
	 * 
	 * @param accountingDto
	 * @return
	 */
	AccountingFeeRes caculateFee(AccountingFeeRe accountingFeeRe)
			throws MaAccountQueryUntxException;

	/**
	 * 调用记账
	 * 
	 * @param accountingDto
	 */
	void doAccounting(AccountingDto accountingDto)
			throws MaAcctBalanceException, MaAccountQueryUntxException;

	/**
	 * 调用记账且返回更新余额状态
	 * 
	 * @param accountingDto
	 * @return 1-成功，0-记账不成功
	 */
	int doAccountingReturn(AccountingDto accountingDto);
}
