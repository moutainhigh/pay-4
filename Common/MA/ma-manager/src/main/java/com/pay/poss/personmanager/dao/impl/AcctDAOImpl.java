package com.pay.poss.personmanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.personmanager.dao.AcctDAO;
import com.pay.poss.personmanager.dto.AcctDto;

@SuppressWarnings("unchecked")
public class AcctDAOImpl extends BaseDAOImpl<AcctDto> implements AcctDAO {

	@Override
	public AcctDto selectAcctByMemberCode(Long memberCode) {
		return (AcctDto) this.getSqlMapClientTemplate().queryForObject(namespace.concat("findAcctByMemberCode"), memberCode);
	}

	@Override
    public List<AcctDto> queryAcctListByMap(AcctDto acctDto) {	   
	    return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectAcctByPossAcct"),acctDto);
    }

}
