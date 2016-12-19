package com.pay.poss.personmanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.personmanager.dao.BankAcctDAO;
import com.pay.poss.personmanager.dto.BankAcctDto;

public class BankAcctDAOImpl extends BaseDAOImpl<BankAcctDto> implements BankAcctDAO {

	@Override
	public List<BankAcctDto> selectBankAcctDtoList(BankAcctDto bankAcctDto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectBankAcctByBankAcct"),bankAcctDto);
	}

}
