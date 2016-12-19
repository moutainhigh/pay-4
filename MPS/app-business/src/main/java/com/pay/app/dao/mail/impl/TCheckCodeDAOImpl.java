/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.mail.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.mail.TCheckCodeDAO;
import com.pay.app.model.TCheckCode;
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
public class TCheckCodeDAOImpl extends BaseDAOImpl implements TCheckCodeDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.mail.TCheckCodeDAO#createCheckCode(com.pay.app.model.
	 * TCheckCode)
	 */
	@Override
	public void createCheckCode(TCheckCode checkCode) {
		super.getSqlMapClientTemplate().insert(getNamespace().concat("create"),
				checkCode);
	}

	public String getOrderId() {
		return (String) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getOrderId"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.mail.TCheckCodeDAO#findStatesByCheckCode(java.lang.String
	 * )
	 */
	@Override
	public int findStatesByCheckCode(String checkCode) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findStatesByCheckCode"), checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.mail.TCheckCodeDAO#getByCheckCode(java.lang.String)
	 */
	@Override
	public TCheckCode getByCheckCode(String checkCode) {
		return (TCheckCode) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByCheckCode"), checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.mail.TCheckCodeDAO#getByMemerCodeAndStatus(java.lang.
	 * String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TCheckCode> getByMemerCodeAndStatus(long memberCode, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("status", status);
		return (List<TCheckCode>) super.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getByMemerCodeAndStatus"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.mail.TCheckCodeDAO#updateCheckCodeStates(java.lang.String
	 * )
	 */
	@Override
	public void updateCheckCodeStates(String checkCode) {
		super.getSqlMapClientTemplate().update(
				getNamespace().concat("updateStatus"), checkCode);
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

}
