/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.dao.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dao.member.MemberDAO;
import com.pay.base.dto.MemberBalancesDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.model.Member;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 会员基本信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:27:25
 * 
 */
public class MemberDAOImpl extends BaseDAOImpl implements MemberDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.member.TMemberDAO#findMemberByMemCode(long)
	 */
	@Override
	public Member findMemberByMemCode(long memberCode) {
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMemberByMemCode"), memberCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.member.TMemberDAO#updateMember(com.pay.app.
	 * model.Member)
	 */
	@Override
	public int updateMember(Member member) {
		if (member != null) {
			return this.getSqlMapClientTemplate().update(
					getNamespace().concat("updateMember"), member);
		}
		return 0;
	}

	public int updateServiceLevelByMemCode(int serviceLevelCode, long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceLevelCode", serviceLevelCode);
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateServiceLevelByMemCode"), map);
	}

	public int editMemberTypeByMemberCode(Map map) {
		if (map != null) {
			return this.getSqlMapClientTemplate().update(
					namespace.concat("updateMemberType"), map);
		}
		return 0;
	}

	@Override
	public Member findMemberByLoginName(String loginName) {
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMemberByLoginName"), loginName);
	}

	@Override
	public Long findMemberCodeByLoginName(String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findMemberCodeByLoginName"), map);
	}

	@Override
	public Long findMemberCodeByLogin(String loginName, String loginPwd,
			int memberType) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("loginName", loginName);
		map.put("loginPwd", loginPwd);
		map.put("type", memberType);
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMemberByLogin"), map);
	}

	public Member findMemberByLogin(String loginName, Integer type) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("loginName", loginName);
		map.put("type", type);
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findMemberByLogin"), map);
	}

	@Override
	public Long createIndividualMember(Member member) {
		return (Long) super.create(member);
	}

	public Long createCorpMember(Member member) {
		Object id = this.getSqlMapClientTemplate().insert(
				getNamespace().concat("createCorpMember"), member);
		if (id == null)
			return 0l;
		return (Long) id;
	}

	public int updateMemberStatus(Long memberCode) {
		if (memberCode != null) {
			return this.getSqlMapClientTemplate().update(
					getNamespace().concat("updateMemberStatus"), memberCode);
		}
		return 0;
	}

	public Member convertIndividualMember(MemberInfoDto memberInfo) {
		Member member = new Member();
		member.setLoginName(memberInfo.getLoginName().toLowerCase());
		member.setGreeting(memberInfo.getGreeting());
		member.setSecurityQuestion(memberInfo.getSecurityQuestion());
		member.setSecurityAnswer(memberInfo.getSecurityAnswer());
		member.setLoginType(memberInfo.getRegType());
		member.setLoginPwd(memberInfo.getPassword());
		member.setSsoUserId(memberInfo.getSsoUserId());
		member.setServiceLevelCode(ServiceLevelEnum.INDIVIDUAL_GENERAL
				.getValue());
		return member;
	}

	public boolean checkIndividualLoginName(String loginName) {
		int result = 1;
		result = (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getCountByLoginName"), loginName);
		if (result == 0)
			return true;

		return false;
	}

	public Integer checkIndividualLoginNameAndStatus(String loginName) {
		Object obj = this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getStatusByLoginName"), loginName);
		if (obj != null)
			return (Integer) obj;
		return -1;
	}

	@Override
	public Integer validateSecurMemberQuestion(Long memberCode,
			Integer questionId, String securInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("questionId", questionId);
		map.put("securInfo", securInfo);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("validateSecurMemberQuestion"), map);
	}

	public Member queryMemberBySsoUserid(String ssoUserid) {
		return (Member) this.findObjectByTemplate("queryMemberBySsoUserid",
				ssoUserid);
	}

	public Member queryPersonMemberBySsoUserid(String ssoUserid) {
		return (Member) this.findObjectByTemplate(
				"queryPersonMemberBySsoUserid", ssoUserid);
	}

	public boolean updateMemberSsoUserid(Map<String, Object> ssoMap) {
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateMemberSsoUserid"), ssoMap) == 1;
	}

	/**
	 * 
	 * @param memberCode
	 * @param status
	 * @return
	 * @see com.pay.base.dao.member.MemberDAO#updateStatusByMemberCode(long,
	 *      int)
	 */
	@Override
	public int updateStatusByMemberCode(long memberCode, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateStatusByMemberCode"), map);
	}

	@Override
	public List<MemberBalancesDto> querySonMemberByParent(
			Map<String, Object> param) {

		return (List<MemberBalancesDto>) getSqlMapClientTemplate()
				.queryForList(getNamespace().concat("querySonMemberByParent"),
						param);
	}

	@Override
	public int querySonMemberCountByParent(Map<String, Object> param) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("querySonMemberCountByParent"), param);
	}

}