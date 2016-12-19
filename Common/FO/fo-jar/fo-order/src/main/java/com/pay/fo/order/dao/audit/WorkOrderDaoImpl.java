/**
 *  File: WorkOrderDaoImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fo.order.dao.audit;

import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.model.audit.WorkOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class WorkOrderDaoImpl extends BaseDAOImpl<WorkOrder> implements
		WorkOrderDao {

	/**
	 * 
	 */
	public WorkOrder findByOrderSeq(final Long orderSeq) {

		return super
				.findObjectByCriteria("findByOrderSeq", orderSeq);
	}

	@Override
	public Page<WorkOrder> findByAuditMemberCodeAndOrderType(
			Page<WorkOrder> page, Long auditMembercode, final Integer orderType) {
		Map paraMap = new HashMap();
		paraMap.put("auditMembercode", auditMembercode);
		paraMap.put("orderType", orderType);
		return findByQuery("findByAuditMemberCode", page, paraMap);
	}

	@Override
	public boolean updateWorkOrderWithOldStatus(Map<String, Object> param) {
		return this.getSqlMapClientTemplate().update(
				"work_order.updateWorkOrderWithOldStatus", (WorkOrder) param) == 1;
	}
}
