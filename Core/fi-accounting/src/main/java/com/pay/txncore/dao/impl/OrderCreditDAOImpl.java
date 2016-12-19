/**
 * 
 */
package com.pay.txncore.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.OrderCreditDAO;
import com.pay.txncore.dto.OrderCreditDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;

/**
 * @author Jiangbo.Peng
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class OrderCreditDAOImpl extends BaseDAOImpl implements
		OrderCreditDAO {

	@Override
	public Long createOrderCreidtDetail(OrderCreditDTO orderCreditDTO) {
		return (Long) super.create("createOrderCreidtDetail", orderCreditDTO) ;
	}

	@Override
	public List<Object> createOrderCreditDetailBatch(
			final List<OrderCreditDetailDTO> ocs) {
		//return super.batchCreate("createOrderCreditDetailBatch", ocs) ;
		List<Object> result = (List<Object>) getSqlMapClientTemplate().execute(
			new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					List<Object> keys = new ArrayList<Object>();
					for (OrderCreditDetailDTO pojo : ocs) {
						Object id = executor.insert(
								getNamespace().concat("createOrderCreditDetailBatch"), pojo);
						if (id != null) {
							keys.add(id);
						}
					}
					executor.executeBatch();
					return keys;
				}
			});
		return result;
	}

	@Override
	public void createOrder(OrderCreditDTO oc,
			List<OrderCreditDetailDTO> ocds) {
				
	}

	@Override
	public Long createOrderCredit(OrderCreditDTO oc) {
		return (Long) super.create("createOrderCredit", oc) ; 
	}

	

}
