/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.acc.checkcode.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.checkcode.dao.CheckCodeDAO;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 会员激活信息DAO<br>
 * 对应ACC库
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午03:09:31
 * 
 */
public class CheckCodeDAOImpl extends BaseDAOImpl implements CheckCodeDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#createCheckCode(com.pay.app.
	 * model.TCheckCode)
	 */
	@Override
	public void createCheckCode(CheckCode checkCode) {
		super.getSqlMapClientTemplate().insert(getNamespace().concat("create"),
				checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#findStatesByCheckCode(java.lang
	 * .String)
	 */
	@Override
	public int findStatesByCheckCode(String checkCode) {
		Integer states = (Integer) super.getSqlMapClientTemplate()
				.queryForObject(getNamespace().concat("findStatesByCheckCode"),
						checkCode);
		if (states != null) {
			return states;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#getByCheckCode(java.lang.String)
	 */
	@Override
	public CheckCode getByCheckCode(String checkCode) {
		return (CheckCode) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByCheckCode"), checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#getByMemerCodeAndStatus(java.lang
	 * .String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckCode> getByMemerCodeAndStatus(long memberCode, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("status", status);
		return (List<CheckCode>) super.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getByMemerCodeAndStatus"), map);
	}

	public CheckCode getByCheckCodeAndOrigin(String checkCode, String origin) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkCode", checkCode);
		map.put("origin", origin);
		return (CheckCode) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByCheckCodeAndOrigin"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#updateCheckCodeStates(java.lang
	 * .String)
	 */
	@Override
	public void updateCheckCodeStates(String checkCode) {
		super.getSqlMapClientTemplate().update(
				getNamespace().concat("updateStatus"), checkCode);
	}

	public void updateCheckStateByMemCode(long memberCode, String checkCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("checkCode", checkCode);
		super.getSqlMapClientTemplate().update(
				getNamespace().concat("updateCheckStateByMemCode"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.mail.TCheckCodeDAO#updateCreateTime(java.lang.String)
	 */
	@Override
	public void updateCreateTime(String checkCode) {
		super.getSqlMapClientTemplate().update(
				getNamespace().concat("updateCreateTime"), checkCode);
	}

	@Override
	public String getOrderId() {
		return (String) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getOrderId"));
	}

	@Override
	public void updateCheckCodeStates2Used(String memberCode, String origin) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberCode", memberCode);
		param.put("origin", origin);
		super.getSqlMapClientTemplate().update(
				getNamespace().concat("updateCheckCodeStates2Used"), param);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CheckCode> getByMemCodeAndStatOrigin(long memberCode,
			int status, String origin) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("status", status);
		map.put("origin", origin);
		return (List<CheckCode>) super.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getByMemberCodeAndStatusOrigin"), map);
	}

	@Override
	public CheckCode getByLastCheckCode(Long memberCode, String businessType,
			String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("mobile", mobile);
		map.put("origin", businessType);
		return (CheckCode) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByMemberCodeAndOrigin"), map);
	}

	@Override
	@SuppressWarnings("unchecked")
	public CheckCode getByMemerCode(String memerCode) {
		CheckCode ck = null;
		List<CheckCode> listCk = super.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getByMemerCode"), memerCode);
		if (listCk != null && listCk.size() > 0)
			ck = listCk.get(0);
		return ck;
	}

	@SuppressWarnings("unchecked")
	public CheckCode getByMemerCode(String memerCode, String origin) {
		CheckCode ck = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memerCode", memerCode);
		map.put("origin", origin);
		List<CheckCode> listCk = super.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getByMemberCodeAndOrigin"), map);
		if (listCk != null && listCk.size() > 0)
			ck = listCk.get(0);
		return ck;
	}

	@Override
	public int findStatesByMemerCode(String memerCode) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByMemerCodeCount"), memerCode);
	}
}
