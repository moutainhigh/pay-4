package com.pay.base.dao.acctattrib.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.base.dao.acctattrib.AcctAttribDAO;
import com.pay.base.model.AcctAttrib;
import com.pay.base.model.PseudoAcct;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AcctAttribDAOImpl extends BaseDAOImpl<AcctAttrib> implements AcctAttribDAO{

	public int updateAcctAttribPwd(Map<String, Object> map) {
		int status = this.getSqlMapClientTemplate().update(this.namespace.concat("updateAcctAttribPwd"), map);
		return status;
	}

	public Long createAcctAttrib(AcctAttrib acctAttrib) {
		return (Long)super.create(acctAttrib);
	}


	public void createBatchAcctAttrib(final List<AcctAttrib> acctAttribList)
			throws DataAccessException {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				int batch = 0;
				for(AcctAttrib acctAttrib : acctAttribList){
					executor.insert(namespace.concat("insertBatchAcctAttrib"), acctAttrib);
					batch++;
					if(batch == 100)
					{
						executor.executeBatch(); 
						batch=0;
					}
					executor.executeBatch();
				}
				return "";
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see com.pay.base.dao.acctattrib.AcctAttribDAO#queryAcctCurrencyByMemberCode(java.lang.Long)
	 */
	@Override
	public List<PseudoAcct> queryAcctCurrencyByMemberCode(Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctCurrencyByMemberCode"), memberCode) ;
	}
	@Override
	public AcctAttrib checkPaymentPwd(Map<String, String> map) {
		List<AcctAttrib> attribDtos=super.findByCriteria("checkPaymentPwd", map);
		return  attribDtos==null||attribDtos.size()<=0?null:attribDtos.get(0);
	}
}
