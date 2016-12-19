package com.pay.poss.personmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.operatelogmanager.model.PossOperate;
import com.pay.poss.personmanager.dao.PersonMemberDAO;
import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctAssociateDto;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctInfoSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.dto.PersonalLoginHistoryDto;
import com.pay.poss.personmanager.model.Member;

@SuppressWarnings("unchecked")
public class PersonMemberDAOImpl extends BaseDAOImpl<Member> implements
		PersonMemberDAO {

	@Override
	public List<PersonalAcctInfoSearchDto> queryPersonalAcctInfoList(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPersonalAcctInfoList"), paramMap);
	}

	@Override
	public int countPersonalAcctInfo(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPersonalAcctInfo"), paramMap);
	}

	@Override
	public List<PersonMemberInfoDto> queryPersonInfoList(Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("selectPersonlMemberInfo"), paramMap);
	}

	@Override
	public int countPersonInfo(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPersonlMemberInfo"), paramMap);
	}

	@Override
	public int countPersonMember(PersonMemberSearchDto memberSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPossMember"), memberSearchDto);
	}

	@Override
	public PersonMemberSearchDto selectPersonMember(Long memberCode) {
		PersonMemberSearchDto memberSearchDto = new PersonMemberSearchDto();
		memberSearchDto.setMemberCode(memberCode);
		return (PersonMemberSearchDto) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("selectPossMember"),
						memberSearchDto);

	}

	@Override
	public List<PersonalAcctInfoSearchDto> queryPersonalLoginHistoryList(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPersonalLoginHistoryList"), paramMap);
	}

	@Override
	public int countPersonalLoginHistory(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPersonalLoginHistory"), paramMap);
	}

	@Override
	public List<PersonalAcctAssociateDto> queryPersonalAcctAssociatelist(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPersonalAcctAssociatelist"), paramMap);
	}

	@Override
	public int countqueryPersonalAcctAssociate(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countqueryPersonalAcctAssociate"), paramMap);
	}

	@Override
	public List<PersonalAcctBalanceDto> queryPersonalAcctBalanceList(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPersonalAcctBalanceList"), paramMap);
	}

	@Override
	public int countPersonalAcctBalanceList(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPersonalAcctBalanceList"), paramMap);
	}

	public String queryBalance(Map paramMap) {
		return (String) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryStrBalance"), paramMap);
	}

	public Double queryNumBalance(Map paramMap) {
		return (Double) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryNumBalance"), paramMap);
	}

	public List<PersonalAcctTradeTotalDto> queryPeraonalAcctTradeTotal(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPeraonalAcctTradeTotal"), paramMap);
	}

	@Override
	public void updatePersonalMemberAcctOperate(Map paramMap) {
		this.getSqlMapClientTemplate().update(
				namespace.concat("updatePersonalMemberAcctOperate"), paramMap);
	}

	@Override
	public void updatePersonalMemberOperate(Map paramMap) {
		this.getSqlMapClientTemplate().update(
				namespace.concat("updatePersonalMemberOperate"), paramMap);
	}

	@Override
	public PersonMemberInfoDto selectPersonlMemberInfoDetail(Map paramMap) {
		return (PersonMemberInfoDto) this.getSqlMapClientTemplate()
				.queryForObject(
						namespace.concat("selectPersonlMemberInfoDetail"),
						paramMap);
	}

	@Override
	public PersonMemberInfoDto selectMemberBankAcct(Map paramMap) {
		return (PersonMemberInfoDto) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("selectMemberBankAcct"),
						paramMap);
	}

	@Override
	public PersonalLoginHistoryDto selectMemberLoginIp(Map paramMap) {
		return (PersonalLoginHistoryDto) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("selectMemberLoginIp"),
						paramMap);
	}

	@Override
	public List<PersonalAcctInfoSearchDto> queryPersonalMemberAcctMessage(
			Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryPersonalMemberAcctMessage"), paramMap);
	}

	@Override
	public int countPersonalMemberAcctMessage(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPersonalMemberAcctMessage"), paramMap);
	}

	public void insertPossOperater(PossOperate possOperate) {
		this.getSqlMapClientTemplate()
				.insert("possoperate.create", possOperate);
	}

	public void updatePossOperater(Map<String, Object> paramMap) {
		this.getSqlMapClientTemplate().update(
				"possoperate.updatePossOperateByCondition", paramMap);
	}

	public void updateMemberOperateByCondition(Map<String, Object> paramMap) {
		this.getSqlMapClientTemplate().update(
				"possoperate.updateMemberOperateByCondition", paramMap);
	}

	public PersonMemberInfoDto selectPersonlMemberIsPaperFile(
			Long paramMemberCode) {
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", paramMemberCode);
		return (PersonMemberInfoDto) this.getSqlMapClientTemplate()
				.queryForObject(
						namespace.concat("selectPersonlMemberIsPaperFile"),
						paramMap);
	}

	public AcctAttribDto selectAcctAttrib(Map<String, Object> paramMap) {
		return (AcctAttribDto) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectAcctAttrib"), paramMap);
	}

	public List<PossOperate> selectAcctFrozenOperateByAcctCode(
			Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				"possoperate.selectAcctFrozenOperateByAcctCode", paramMap);
	}

	public Integer countBindMatrixCard(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countBindMatrixCard"), paramMap);
	}

	public PersonMemberInfoDto selectMemberByMemberCode(Map paramMap) {
		return (PersonMemberInfoDto) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("selectMemberByMemberCode"),
						paramMap);
	}
}
