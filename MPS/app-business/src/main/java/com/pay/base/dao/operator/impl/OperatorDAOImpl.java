/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.dao.operator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.dao.operator.OperatorDAO;
import com.pay.base.model.Operator;
import com.pay.base.model.OperatorExtLoginInfo;
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
	 * .Operator)
	 */
	@Override
	public void createOperator(Operator operator) {
		this.getSqlMapClientTemplate().insert(getNamespace().concat("create"),
				operator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#geOperatorById(long)
	 */
	@Override
	public Operator getOperatorById(long operatorId) {
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
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
	public Operator getByIdentityMemCode(String identity, long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identity", identity);
		map.put("memberCode", memberCode);
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByIdentityAndMemberCode"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#getByIdentity(java.lang.String)
	 */
	public Operator getByIdentity(String identity) {
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByIdentity"), identity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.operator.OperatorDAO#geOperatorInfo(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Operator> getOperatorInfo(long memberCode, Integer endNum,
			Integer beginNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("endNum", endNum);
		map.put("beginNum", beginNum);
		return (List<Operator>) this.getSqlMapClientTemplate().queryForList(
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

	/**
	 * 
	 * @param memberCode
	 * @return
	 * @see com.pay.base.dao.operator.OperatorDAO#getOperatorPageCount(long)
	 */
	public int getOperatorPageCount(long memberCode) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getOperatorPageCountByMemberCode"),
				memberCode);
	}

	/**
	 * 
	 * @param memberCode
	 * @param endNum
	 * @param beginNum
	 * @return
	 * @see com.pay.base.dao.operator.OperatorDAO#getOperatorPageInfo(long,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<OperatorExtLoginInfo> getOperatorPageInfo(long memberCode,
			Integer endNum, Integer beginNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("endNum", endNum);
		map.put("beginNum", beginNum);
		return (List<OperatorExtLoginInfo>) this
				.getSqlMapClientTemplate()
				.queryForList(
						getNamespace()
								.concat("getOperatorPageInfoByMemberCode"), map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.operator.OperatorDAO#updateOperator(com.pay.app.model
	 * .TOperator)
	 */
	@Override
	public int updateOperator(Operator operator) {
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperator"), operator);
	}

	public int updateOperatorPWD(String pwd, Long operatorId, Long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", pwd);
		map.put("operatorId", operatorId);
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperatorLoginPassWord"), map);
	}

	/**
	 * 
	 * @param memberCode
	 * @param operatorId
	 * @param status
	 * @return
	 * @see com.pay.base.dao.operator.OperatorDAO#updateOperatorStatus(long,
	 *      long, int)
	 */
	@Override
	public int updateOperatorStatus(long memberCode, long operatorId, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperatorStatus"), map);
	}

	@Override
	public int updateOperatorPayPassWord(long memberCode, long operatorId,
			String payPassWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("payPassWord", payPassWord);
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateOperatorPayPwd"), map);
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
	public Operator getByLogin(String identity, String loginPwd, Long memberCode) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("identity", identity);
		map.put("loginPwd", loginPwd);
		map.put("memberCode", memberCode);
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
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

	/**
	 * 
	 * @param password
	 * @param identity
	 * @param operatorId
	 * @return
	 * @see com.pay.base.dao.operator.OperatorDAO#activeOperator(java.lang.String,
	 *      java.lang.String, java.lang.Long)
	 */
	public int activeOperator(String password, String identity, Long operatorId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("identity", identity);
		map.put("password", password);
		map.put("operatorId", operatorId);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("activeOperator"), map);
	}

	public Operator getAdminOperator(Long memberCode) {
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getAdminOperator"), memberCode);
	}

	/* (non-Javadoc)
	 * @see com.pay.base.dao.operator.OperatorDAO#checkOperatorPaymentPwd(java.util.Map)
	 */
	@Override
	public Operator checkOperatorPaymentPwd(Map<String, String> hMap) {
		return (Operator) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("checkOperatorPaymentPwd"), hMap);
	}
}
