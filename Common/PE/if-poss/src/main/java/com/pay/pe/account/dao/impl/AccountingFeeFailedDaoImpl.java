package com.pay.pe.account.dao.impl;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.account.dao.AccountingFeeFailedDao;
import com.pay.pe.account.dao.EntryQueryDao;
import com.pay.pe.account.dto.AccountingFeeFailedDto;
import com.pay.pe.account.dto.AccountingFeeFailedParamDto;

/**
 * 
 * @author ddr
 * 
 */
@SuppressWarnings("unchecked")
public class AccountingFeeFailedDaoImpl extends BaseDAOImpl implements
		AccountingFeeFailedDao {

	private EntryQueryDao entryQueryDao;

	public void setEntryQueryDao(EntryQueryDao entryQueryDao) {

		this.entryQueryDao = entryQueryDao;
	}

	@Override
	public Page<AccountingFeeFailedDto> search(AccountingFeeFailedParamDto dto,
			Page<AccountingFeeFailedDto> pageParam) {
		return entryQueryDao.findByQuery(this.namespace.concat("search"),
				pageParam, dto);
	}

}
