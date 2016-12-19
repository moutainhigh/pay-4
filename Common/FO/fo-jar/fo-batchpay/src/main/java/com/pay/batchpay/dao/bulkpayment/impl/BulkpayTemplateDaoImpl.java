/**
 * 
 */
package com.pay.batchpay.dao.bulkpayment.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.batchpay.dao.bulkpayment.BulkpayTemplateDao;
import com.pay.batchpay.dto.BulkPaymentTemplate;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
@SuppressWarnings("deprecation")
public class BulkpayTemplateDaoImpl extends BaseDAOImpl implements
		BulkpayTemplateDao {

	@Override
	public void insertBulkpayBatch(Map<String, Object> map) {
		this.getSqlMapClientTemplate().insert(this.getNamespace().concat("insertBulkpayBatch"), map) ;
	}

	@Override
	public List<BulkPaymentTemplate> findBulkpayByBulkorderNo(String bulkorderNo) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("findBulkpayByBulkorderNo"), bulkorderNo) ;
	}

	@Override
	public void insertBulkpayFundoutBatch(Map<String, Object> map) {
		this.getSqlMapClientTemplate().insert(this.getNamespace().concat("insertBulkpayFundoutBatch"), map) ;
	}

	@Override
	public List<?> insertBulkpayFundoutBatch2(final Map<String, Object> map) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0 ;
				for(int i=0; i<10; i++){
				executor.insert(namespace.concat("insertBulkpayFundoutBatch2"), map) ;
					batch++ ;
					if (batch == 100) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return null;
			}
		}) ;
		return null;
	}

}
