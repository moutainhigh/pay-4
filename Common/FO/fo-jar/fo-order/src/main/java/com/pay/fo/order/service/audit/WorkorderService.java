/**
 *  File: WorkorderService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fo.order.service.audit;

import java.util.Map;

import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.model.audit.WorkOrder;
import com.pay.inf.dao.Page;

public interface WorkorderService {

	/**
	 * 
	 * @param workorder
	 * @return
	 */
	public WorkOrderDto createWorkorderRnTx(WorkOrderDto workorder);

	
	/**
	 * 
	 * @param workorder
	 * @return
	 */
	public boolean updateWorkorderRnTx(WorkOrderDto workorder);
	
	/**
	 * 更新bsp工单状态
	 * 
	 * @param map
	 * @return
	 */
	public boolean updateBspWorkorderRnTx(Map<String, Object> map);
	
	/**
	 * 更新工单状态
	 * @param param
	 * @return
	 */
	public boolean updateWorkOrderWithOldStatusRnTx(Map<String, Object> param);

	/**
	 * 
	 * @param sequenceId
	 * @return
	 */
	public WorkOrderDto findWorkorderById(final Long sequenceId);

	/**
	 * 
	 * @param orderSeq
	 * @return
	 */
	public WorkOrderDto findByOrderSeq(final Long orderSeq);

	/**
	 * 
	 * @param page
	 * @param auditMembercode
	 * @return
	 */
	public Page<WorkOrder> findByAuditMemberCodeAndOrderType(
			Page<WorkOrder> page, final Long auditMembercode,
			final Integer orderType);
}
