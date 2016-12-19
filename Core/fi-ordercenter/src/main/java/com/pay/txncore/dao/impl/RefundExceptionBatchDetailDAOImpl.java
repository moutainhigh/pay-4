/**
 * 
 */
package com.pay.txncore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RefundExceptionBatchDetailDAO;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;

/**
 * @author PengJiangbo
 *
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class RefundExceptionBatchDetailDAOImpl extends BaseDAOImpl implements
		RefundExceptionBatchDetailDAO {

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<RefundExceptionBatchDetailDTO> insertRefundExceptionBatchDetail(
			final List<RefundExceptionBatchDetailDTO> lists, final Object object) {
		try {
			//List<RefundExceptionBatchDetailDTO> refundExceptionBatchDetailDTOs = new ArrayList<RefundExceptionBatchDetailDTO>() ;
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					int batch = 0 ;
					for(RefundExceptionBatchDetailDTO rebd : lists){
						Long batchDetailNo =(Long) executor.insert(namespace.concat("refundExceptionBatchDetail"), rebd) ;
						rebd.setBatchDetailNo(batchDetailNo);
						batch++ ;
						if(batch == 100){
							executor.executeBatch();
							batch=0;
						}
						executor.executeBatch() ;
					}
					return true;
				}
			}) ;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insertRefundExceptionBatchDetail(
			final List<RefundExceptionBatchDetailDTO> lists) {
		try {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					int batch = 0 ;
					for(RefundExceptionBatchDetailDTO rebd : lists){
						executor.insert(namespace.concat("refundExceptionBatchDetail"), rebd) ;
						
						batch++ ;
						if(batch == 100){
							executor.executeBatch();
							batch=0;
						}
						executor.executeBatch() ;
					}
					return true;
				}
			}) ;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	

}
