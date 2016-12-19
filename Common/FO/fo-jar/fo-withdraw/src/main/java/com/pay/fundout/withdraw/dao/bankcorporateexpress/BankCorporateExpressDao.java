package com.pay.fundout.withdraw.dao.bankcorporateexpress;

import java.util.List;

import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressResDTO;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossException;

public interface BankCorporateExpressDao extends
		BaseDAO<BankCorporateExpressResDTO> {
	/**
	 * 查询指定条件的所有数据
	 * 
	 * @param params
	 * @return
	 * @throws PlatformDaoException
	 */
	List<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(
			Object... params) throws PlatformDaoException;

	Page<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(
			final Page<BankCorporateExpressResDTO> page, Object... params)
			throws PlatformDaoException;

	Page<BankCorporateExpressResDTO> bankCorporateExpressReAuditQuery(
			final Page<BankCorporateExpressResDTO> page, Object... params)
			throws PlatformDaoException;

	boolean auditUpdate(BankCorporateExpressResDTO dto) throws PossException;
}
