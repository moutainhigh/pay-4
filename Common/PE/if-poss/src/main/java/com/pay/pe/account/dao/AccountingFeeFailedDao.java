package com.pay.pe.account.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccountingFeeFailedDto;
import com.pay.pe.account.dto.AccountingFeeFailedParamDto;

/**
 * accumulated_chargeback_log 失败扣费记录表
 * 
 * @author ddr
 * 
 */
public interface AccountingFeeFailedDao extends BaseDAO {

	/**
	 * 分页查询计费失败的记录
	 * 
	 * @param dto
	 * @param pageParam
	 * @return
	 */
	public Page<AccountingFeeFailedDto> search(AccountingFeeFailedParamDto dto,
			Page<AccountingFeeFailedDto> pageParam);

}
