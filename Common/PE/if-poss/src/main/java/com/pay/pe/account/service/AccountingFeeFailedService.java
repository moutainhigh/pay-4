package com.pay.pe.account.service;

import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccountingFeeFailedDto;
import com.pay.pe.account.dto.AccountingFeeFailedParamDto;

/**
 * 
 * @author ddr
 * 
 */
public interface AccountingFeeFailedService {

	/**
	 * 根据条件查询
	 * 
	 * @param pageParam
	 * @param dto
	 * @return list
	 */
	public Page<AccountingFeeFailedDto> searchPage(
			AccountingFeeFailedParamDto dto,
			Page<AccountingFeeFailedDto> pageParam);

	/**
	 * 执行扣费
	 * 
	 * @param memberCode
	 * @param dealType
	 * @return
	 */
	public boolean chargeback(long voucherCode, int dealType);

}
