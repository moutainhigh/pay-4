package com.pay.fo.order.dao.bsp;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 提现审核Dao
 * <p>
 * </p>
 * 
 * @author wucan
 * @since 2011-6-29
 * @see
 */
public class BspWithdrawAuditDaoImpl extends BaseDAOImpl implements
		BspWithdrawAuditDao {

	@Override
	public Map<String, Object> view(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"bspWithdrawAudit.queryBspWithdrawAuditList", map);
	}

	@Override
	public Page<Map<String, Object>> queryResultList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryBspWithdrawAuditList", page, map);
	}

	@Override
	public boolean updateBspWorkorder(Map<String, Object> map) {
		return super.update("bspWithdrawAudit.updateBspWorkorder", map);
	}

}
