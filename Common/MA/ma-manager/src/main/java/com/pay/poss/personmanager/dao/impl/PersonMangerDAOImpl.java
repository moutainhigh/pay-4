package com.pay.poss.personmanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.personmanager.dao.PersonMangerDAO;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.model.Member;

@SuppressWarnings("unchecked")
public class PersonMangerDAOImpl extends BaseDAOImpl<Member> implements PersonMangerDAO {

	@Override
	public List<PersonMemberSearchDto> queryPersonList(PersonMemberSearchDto memberSearchDto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectPossMember"), memberSearchDto);
	}

	@Override
	public int countPersonMember(PersonMemberSearchDto memberSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(namespace.concat("countPossMember"), memberSearchDto);
	}

	@Override
	public PersonMemberSearchDto selectPersonMember(Long memberCode) {
		PersonMemberSearchDto memberSearchDto = new PersonMemberSearchDto();
		memberSearchDto.setMemberCode(memberCode);
		return (PersonMemberSearchDto) this.getSqlMapClientTemplate().queryForObject(namespace.concat("selectPossMember"), memberSearchDto);

	}
	
}
