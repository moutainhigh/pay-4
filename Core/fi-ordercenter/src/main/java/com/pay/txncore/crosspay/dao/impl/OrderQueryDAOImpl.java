package com.pay.txncore.crosspay.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.OrderQueryDAO;
import com.pay.txncore.crosspay.model.OrderQueryResult;

public class OrderQueryDAOImpl extends BaseDAOImpl<OrderQueryResult> implements
		OrderQueryDAO {

	private final Log log = LogFactory.getLog(OrderQueryDAOImpl.class);

	@Override
	public List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize) {
		int page_offset = (pageNo - 1) * pageSize;
		try {
			List<OrderQueryResult> queryDetailList = getSqlMapClientTemplate()
					.queryForList("ORDER_QUERY.queryOrderDetailList",
							queryDetailPara, page_offset, pageSize);
			return queryDetailList;
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

	@Override
	public Map<String, Object> countRefundOrderList(Map queryDetailPara) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject(
				"ORDER_QUERY.countOrderDetailList", queryDetailPara);
	}

	@Override
	public List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara) {
		try {
			List<OrderQueryResult> queryDetailList = getSqlMapClientTemplate()
					.queryForList("ORDER_QUERY.queryOrderDetailList",
							queryDetailPara);
			return queryDetailList;
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

	@Override
	public List<Map> queryOrderInBackGroundForPage(String sql,
			Map<String, Object> OrderInfo, Page page) {
		try {
			int oracleEnd = page.getPageSize();
			int oracleStart = page.currentRecordIndex();

			int total = countByCriteria(sql, OrderInfo);
			page.setTotalRecord(total);
			return getSqlMapClientTemplate().queryForList(
					namespace.concat(sql), OrderInfo, oracleStart, oracleEnd);
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

	@Override
	public Map<String, Object> viewOrderDetails(String sql, Map queryParam) {

		try {
			return getSqlMapClientTemplate().queryForMap(namespace.concat(sql),
					queryParam, null);
		} catch (Exception e) {
			log.error("======>:" + e);
			return null;
		}
	}

}
