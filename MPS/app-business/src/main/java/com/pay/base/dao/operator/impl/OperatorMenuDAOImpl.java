/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.operator.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.base.dao.operator.OperatorMenuDAO;
import com.pay.base.model.OperatorMenu;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author wangzhi
 * @version $Id: OperatorMenuDAOImpl.java, v 0.1 2010-9-25 上午11:16:57 wangzhi
 *          Exp $
 */
public class OperatorMenuDAOImpl extends BaseDAOImpl implements OperatorMenuDAO {

	/**
	 * @param operatorMenu
	 * @return
	 * @see com.pay.app.dao.operator.OperatorMenuDAO#createOperatorMenu(com.pay.app.model.OperatorMenu)
	 */
	@Override
	public void createOperatorMenu(OperatorMenu operatorMenu) {
		this.getSqlMapClientTemplate().insert(getNamespace().concat("create"),
				operatorMenu);
		;
	}

	/**
	 * @param opMenuId
	 * @return
	 * @see com.pay.app.dao.operator.OperatorMenuDAO#getByOpMenuId(long)
	 */
	@Override
	public OperatorMenu getByOpMenuId(long opMenuId) {
		return (OperatorMenu) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByOpMenuId"), opMenuId);
	}

	/**
	 * @param operatorId
	 * @return
	 * @see com.pay.app.dao.operator.OperatorMenuDAO#getByOperateId(long)
	 */
	@Override
	public OperatorMenu getByOperateId(long operatorId) {
		return (OperatorMenu) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getByOperateId"), operatorId);
	}

	/**
	 * @param menuArray
	 * @param operatorId
	 * @return
	 * @see com.pay.app.dao.operator.OperatorMenuDAO#updateByOperatorId(java.lang.String,
	 *      long)
	 */
	@Override
	public int updateByOperatorId(String menuArray, long operatorId) {
		if (StringUtils.isBlank(menuArray)) {
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuArray", menuArray);
		map.put("operatorId", operatorId);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateByOperateId"), map);
	}

}
