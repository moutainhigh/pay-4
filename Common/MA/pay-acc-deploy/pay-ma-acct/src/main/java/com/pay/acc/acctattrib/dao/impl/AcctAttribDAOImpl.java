/**
 * 
 */
package com.pay.acc.acctattrib.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.acctattrib.dao.AcctAttribDAO;
import com.pay.acc.acctattrib.model.AcctAttrib;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 * 
 */
public class AcctAttribDAOImpl extends BaseDAOImpl<AcctAttrib> implements
		AcctAttribDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.acctattrib.dao.AcctAttribDao#queryAcctAttribWithAcctCode
	 * (java.lang.Long)
	 */
	public AcctAttrib queryAcctAttribWithAcctCode(String acctCode) {
		return (AcctAttrib) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryAcctAttribWithAcctCode"), acctCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.acctattrib.dao.AcctAttribDAO#updateAcctAttribPwd(java.util
	 * .Map)
	 */
	public int updateAcctAttribPwd(Map<String, Object> map) {
		int status = this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateAcctAttribPwd"), map);
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.acctattrib.dao.AcctAttribDAO#queryAcctAttribWithMemberAcctCode
	 * (java.lang.Long)
	 */
	@Override
	public AcctAttrib queryAcctAttribWithMemberAcctCode(Long memberAcctCode) {
		return (AcctAttrib) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryAcctAttribWithMemberAcctCode"),
				memberAcctCode);
	}

	@Override
	public boolean updateAcctAllowInStatus(String acctCode, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateAcctAllowInStatus"), map) == 1;
	}

	@Override
	public boolean updateAcctAllowOutStatus(String acctCode, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateAcctAllowOutStatus"), map) == 1;
	}

	@Override
	public boolean updateAcctAllowOutStatusWithMemberCode(String memberCode,
			Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("status", status);
		return this
				.getSqlMapClientTemplate()
				.update(this.namespace
						.concat("updateAcctAllowOutStatusWithMemberCode"),
						map) > 0;
	}

	@Override
	public boolean updateAcctFreeze(String acctCode, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateAcctFreeze"), map) == 1;
	}

	@Override
	public int countAcctAttribFreeze(String acctCode) {
		Integer obj = (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countAcctAttribFreeze"), acctCode);
		if (null == obj) {
			return 0;
		}
		return obj;
	}

	@Override
	public int countAllowOutRecord(String acctCode) {
		Integer obj = (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countAllowOutRecord"), acctCode);
		if (null == obj) {
			return 0;
		}
		return obj;
	}

	@Override
	public int countPossAcctAttrib(Map<String, Object> paramMap) {
		Integer obj = (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countPossAcctAttrib"), paramMap);
		if (null == obj) {
			return 0;
		}
		return obj;
	}

	@Override
	public boolean isAllowAcctrib(Map<String, Object> paramMap) {
		Integer obj = (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countIsAllowAcctrib"), paramMap);
		if (null == obj || obj == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean countIsAllowAcctribByMemberCode(Map<String, Object> paramMap) {
		Integer obj = (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countIsAllowAcctribByMemberCode"), paramMap);
		if (null == obj || obj == 0) {
			return false;
		}
		return true;
	}

}
