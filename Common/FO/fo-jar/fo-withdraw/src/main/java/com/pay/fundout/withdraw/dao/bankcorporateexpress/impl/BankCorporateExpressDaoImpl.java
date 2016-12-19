package com.pay.fundout.withdraw.dao.bankcorporateexpress.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.bankcorporateexpress.BankCorporateExpressDao;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressResDTO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossException;

@SuppressWarnings("unchecked")
public class BankCorporateExpressDaoImpl extends
		BaseDAOImpl<BankCorporateExpressResDTO> implements
		BankCorporateExpressDao {

	@Override
	public Page<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(
			Page<BankCorporateExpressResDTO> page, Object... params)
			throws PlatformDaoException {
		return super.findByQuery(
				"BANKCORPORATEEXPRESS.bankCorporateExpressFailQuery", page,
				params);
	}

	@Override
	public Page<BankCorporateExpressResDTO> bankCorporateExpressReAuditQuery(
			Page<BankCorporateExpressResDTO> page, Object... params)
			throws PlatformDaoException {
		return super.findByQuery(
				"BANKCORPORATEEXPRESS.bankCorporateExpressFailQuery", page,
				params);
	}

	@Override
	public boolean auditUpdate(BankCorporateExpressResDTO dto)
			throws PossException {
		return super.update("BANKCORPORATEEXPRESS.auditUpdate", dto);
	}

	/**
	 * 查询指定条件的所有数据
	 * 
	 * @param params
	 * @return
	 * @throws PlatformDaoException
	 */
	@Override
	public List<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(
			Object... params) throws PlatformDaoException {
		// TODO Auto-generated method stub
		return super.findByQuery(
				"BANKCORPORATEEXPRESS.bankCorporateExpressFailQuery", params);
	}
}
