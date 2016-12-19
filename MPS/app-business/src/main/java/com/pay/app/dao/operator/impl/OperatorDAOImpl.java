/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.operator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.operator.OperatorDAO;
import com.pay.app.model.OperatorExtLoginInfo;
import com.pay.app.model.TOperator;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 操作员信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午06:45:04
 * 
 */
public class OperatorDAOImpl extends BaseDAOImpl implements OperatorDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.operator.OperatorDAO#createOperator(com.pay.app.model
	 * .TOperator)
	 */
	@Override
	public void createOperator(TOperator operator) {
		this.getSqlMapClientTemplate().insert(getNamespace().concat("create"),
				operator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#getOperatorById(long)
	 */
	@Override
	public TOperator getOperatorById(long operatorId) {
		return (TOperator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByOperatorId"), operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.operator.OperatorDAO#getByIdentityMemCode(java.lang.String
	 * , long)
	 */
	@Override
	public TOperator getByIdentityMemCode(String identity, long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identity", identity);
		map.put("memberCode", memberCode);
		return (TOperator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByIdentityAndMemberCode"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#getByIdentity(java.lang.String)
	 */
	public TOperator getByIdentity(String identity) {
		return (TOperator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByIdentity"), identity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#getOperatorInfo(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TOperator> getOperatorInfo(long memberCode, Integer endNum,
			Integer beginNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("endNum", endNum);
		map.put("beginNum", beginNum);
		return (List<TOperator>) this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("findByMemberCode"), map);
	}

	/**
	 * 
	 * @param memberCode
	 * @param endNum
	 * @param beginNum
	 * @param operatorName
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 * @see com.pay.app.dao.operator.OperatorDAO#getOperatorLoginInfo(long,
	 *      java.lang.Integer, java.lang.Integer, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OperatorExtLoginInfo> getOperatorLoginInfo(long memberCode,
			Integer endNum, Integer beginNum, String operatorName,
			String startTime, String endTime, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("endNum", endNum);
		map.put("beginNum", beginNum);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		map.put("operatorName", operatorName);
		return (List<OperatorExtLoginInfo>) this.getSqlMapClientTemplate()
				.queryForList(
						getNamespace().concat(
								"getOperatorExtLoginInfoByMemberCode"), map);
	}

	/**
	 * 
	 * @param memberCode
	 * @param operatorName
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 * @see com.pay.app.dao.operator.OperatorDAO#getOperatortLoginCount(long,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int getOperatortLoginCount(long memberCode, String operatorName,
			String startTime, String endTime, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		map.put("operatorName", operatorName);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getOperatorExtLoginCountByMemberCode"),
				map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.operator.OperatorDAO#updateOperator(com.pay.app.model
	 * .TOperator)
	 */
	@Override
	public int updateOperator(TOperator operator) {
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperator"), operator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#updateOperatorStatus(long, int)
	 */
	@Override
	public int updateOperatorStatus(long operatorId, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperatorStatus"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#queryCountByMemberCode(long)
	 */
	@Override
	public int queryCountByMemberCode(long memberCode) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("queryCountByMemberCode"), memberCode);
	}

	@Override
	public TOperator getByLogin(String identity, String loginPwd,
			Long memberCode) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("identity", identity);
		map.put("loginPwd", loginPwd);
		map.put("memberCode", memberCode);
		return (TOperator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByLogin"), map);

	}

	/**
	 * 
	 * @param memberCode
	 * @return
	 * @see com.pay.app.dao.operator.OperatorDAO#getOperatorNameByMemCode(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperatorNameByMemCode(long memberCode) {
		return (List<String>) this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getOperatorNameByMemberCode"),
				memberCode);
	}

}
