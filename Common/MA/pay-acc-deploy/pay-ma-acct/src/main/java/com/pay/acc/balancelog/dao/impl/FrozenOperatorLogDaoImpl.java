/**
 * 
 */
package com.pay.acc.balancelog.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pay.acc.balancelog.dao.FrozenOperatorLogDao;
import com.pay.acc.balancelog.model.FrozenOperatorLog;
import com.pay.inf.dao.impl.BaseDAOImpl;


/**
 * @author Administrator
 *
 */
public class FrozenOperatorLogDaoImpl extends BaseDAOImpl<FrozenOperatorLog> implements FrozenOperatorLogDao {


	@Override
	public List<Long> batchAddFrozenLog(
			List<FrozenOperatorLog> frozenOperatorLogs) throws SQLException {
		SqlMapClient client = this.getSqlMapClient();
		List<Long> list = new ArrayList<Long>();
		String insertId = namespace.concat("create");
		client.startBatch();
		for(FrozenOperatorLog dto: frozenOperatorLogs){
			Long id = (Long) client.insert(insertId, dto);
			list.add(id);
		}
		int insertCo  = client.executeBatch();
		return list;
	}

	
	

	



	
}
