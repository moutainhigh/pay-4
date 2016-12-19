/**
 * 
 */
package com.pay.fi.fill.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.fi.fill.dao.OrderFillRecordInfoDao;
import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class OrderFillRecordInfoDaoImpl extends BaseDAOImpl<FillRecordInfo>
		implements OrderFillRecordInfoDao {

	protected static final boolean FillRecordInfo = false;

	@Override
	public void orderFillRecordSave(final List<FillRecordInfo> list) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for(FillRecordInfo fillRecordInfo : list){
					executor.insert(namespace.concat("orderFillRecordSave"), fillRecordInfo);
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
		}) ;
	}

	@Override
	public List<FillRecordInfo> findOrderFillRecordByReqBatchNo(Long reqBatchNo) {
		return this.getSqlMapClientTemplate().queryForList(getNamespace().concat("findOrderFillRecordByreqBatchNo"), reqBatchNo);
	}

	@Override
	public int updateRecordStatusByReqBatchNo(Map<String, Object> hMap) {
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateRecordStatusByReqBatchNo"), hMap) ;
	}

	@Override
	public boolean updateFillRecordBatch(final List<FillRecordInfo> list) {
		
		try {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					int batch = 0;
					for(FillRecordInfo fillRecordInfo : list){
						executor.update(namespace.concat("updateFillRecordBatch"), fillRecordInfo) ;
						batch ++ ;
						if(batch == 500){
							executor.executeBatch() ;
							batch = 0 ;
						}
						executor.executeBatch() ;
					}
					return null;
				}
			}) ;
			return true;
		} catch (DataAccessException e) {
			return false ;
		}
	}

}
