/**
 * 
 */
package com.pay.fo.order.dao.audit;

import com.pay.fo.order.model.audit.SingleAuditQueryInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author NEW
 * 
 */
public class SingleAuditQueryDAOImpl extends BaseDAOImpl<SingleAuditQueryInfo>
		implements SingleAuditQueryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.order.dao.audit.SingleAuditQueryDAO#queryAuditList(com.pay
	 * .inf.dao.Page, com.pay.fo.order.model.audit.SingleAuditQueryInfo)
	 */
	@Override
	public Page<SingleAuditQueryInfo> queryAuditList(
			Page<SingleAuditQueryInfo> page, SingleAuditQueryInfo info) {
		return super
				.findByQuery("queryAuditList", page, info);
	}

}
