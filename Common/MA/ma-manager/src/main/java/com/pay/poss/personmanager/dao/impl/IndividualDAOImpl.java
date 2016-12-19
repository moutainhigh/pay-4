package com.pay.poss.personmanager.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.personmanager.dao.IndividualDAO;
import com.pay.poss.personmanager.dto.IndividualDto;

public class IndividualDAOImpl extends BaseDAOImpl<IndividualDto> implements IndividualDAO {

	@Override
	public IndividualDto selectIndividualDtoByMemberCode(Long memberCode) {
		return (IndividualDto) this.getSqlMapClientTemplate().queryForObject(namespace.concat("selectPossIndividual"), memberCode);
	}


	@Override
	public void updateIndividualInfo(Map paraMap) {
		this.getSqlMapClientTemplate().update(namespace.concat("updateIndividualInfo"), paraMap);
	}
	
}
