package com.pay.base.dao.acct.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.base.dao.acct.AcctDAO;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctAttrib;
import com.pay.base.model.AcctInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class AcctDAOImpl extends BaseDAOImpl implements AcctDAO {

	public void batchCreate(final List<Acct> acctList)
			throws DataAccessException {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for (Acct acct : acctList) {
					executor.insert(namespace.concat("insertBatchAcct"), acct);
					batch++;
					if (batch == 100) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return "";
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.dao.acct.impl.AcctDAO#createAcct(com.pay.base.model.Acct)
	 */
	public Long createAcct(Acct acct) {
		return (Long) super.create(acct);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.acct.impl.AcctDAO#createAcctAttrib(java.lang.Long)
	 */
	public Long createAcctAttrib(Long memberCode) {
		// getAttCodeById
		AcctAttrib at = new AcctAttrib();
		at.setMemberCode(memberCode);
		return (Long) this.getSqlMapClientTemplate().insert(
				getNamespace().concat("createAttrib"), at);
	}

	public void createMemberProduct(Long memberCode) throws Exception {
		// throw new AppException();
		this.getSqlMapClientTemplate().insert(
				getNamespace().concat("createMemberProduct"), memberCode);
	}

	public String getAccCodeById(Long acctAttrId) {
		return (String) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getAttCodeById"), acctAttrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.acct.impl.AcctDAO#getModelClass()
	 */
	@Override
	public Class getModelClass() {
		return Acct.class;
	}

	/**
	 * 
	 * @param memberCode
	 * @param acctTypeId
	 * @return
	 * @see com.pay.base.dao.acct.AcctDAO#getByMemberCode(long, int)
	 */
	@Override
	public List<Acct> getByMemberCode(long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getAcctByMemberCode"), map);
	}
	
	@Override
	public Acct getByMemberCode(long memberCode,Integer acctType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("acctType", acctType);
		return (Acct) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getAcctByMemberCode"), map);
	}

	@Override
	public List<Acct> getSonAcctByParentMember(long fatherMemberCode,
			int acctTypeId, String sonMemberName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fatherMemberCode", fatherMemberCode);
		map.put("acctTypeId", acctTypeId);
		map.put("sonMemberName", sonMemberName);
		return (List<Acct>) this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getSonAcctByParentMember"), map);

	}

	public int updateAcctStatus(String acctCode, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateAcctStatus"), map);
	}

	@Override
	public List<AcctInfo> getAcctInfoByMemberCode(long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getAcctInfoByMemberCode"), map);
	}

	@Override
	public AcctInfo getAcctInfoByAcctCode(String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode",acctCode);
		return (AcctInfo) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getAcctInfoByAcctCode"), map);
	}

	/* (non-Javadoc)
	 * @see com.pay.base.dao.acct.AcctDAO#queryAcctByMemberCodeAndCurrency(java.lang.Long, java.lang.String)
	 */
	
	@Override
	public List<Acct> queryAcctByMemberCodeAndCurrency(Long memberCode,
			String currency) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currency", currency) ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctByMemberCodeAndCurrency"), hMap) ;
	}

}
