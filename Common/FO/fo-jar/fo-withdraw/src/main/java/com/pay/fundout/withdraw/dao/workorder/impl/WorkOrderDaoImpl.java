/**
 *  File: WorkOrderDaoImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fundout.withdraw.dao.workorder.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.withdraw.dao.workorder.WorkOrderDao;
import com.pay.fundout.withdraw.model.work.WorkOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class WorkOrderDaoImpl extends BaseDAOImpl<WorkOrder> implements
		WorkOrderDao {

	/**
	 * 
	 */
	public WorkOrder findByOrderSeq(final Long orderSeq) {

		return findObjectByCriteria("work_order.findByOrderSeq", orderSeq);
	}

	@Override
	public Page<WorkOrder> findByAuditMemberCodeAndOrderType(
			Page<WorkOrder> page, Long auditMembercode, final Integer orderType) {
		Map paraMap = new HashMap();
		paraMap.put("auditMembercode", auditMembercode);
		paraMap.put("orderType", orderType);
		return findByQuery("work_order.findByAuditMemberCode", page, paraMap);
	}
}
