/**
 *  File: WorkorderServiceImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fundout.withdraw.service.workorder.impl;

import java.util.Map;

import com.pay.fundout.withdraw.dao.bsp.BspWithdrawAuditDao;
import com.pay.fundout.withdraw.dao.workorder.WorkOrderDao;
import com.pay.fundout.withdraw.dto.workorder.WorkOrderDto;
import com.pay.fundout.withdraw.model.work.WorkOrder;
import com.pay.fundout.withdraw.service.workorder.WorkorderService;
import com.pay.inf.dao.Page;
import com.pay.util.BeanConvertUtil;

public class WorkorderServiceImpl implements WorkorderService {

	private WorkOrderDao workOrderDao;
	private BspWithdrawAuditDao bspwithdrawauditdao;

	@Override
	public WorkOrderDto createWorkorderRnTx(WorkOrderDto workorder) {

		Long sequenceId = (Long) workOrderDao.create(BeanConvertUtil.convert(
				WorkOrder.class, workorder));
		workorder.setSequenceId(sequenceId);
		return workorder;
	}

	@Override
	public boolean updateWorkorderRnTx(WorkOrderDto workorder) {
		return workOrderDao.update(BeanConvertUtil.convert(WorkOrder.class,
				workorder));
	}
	
	/**
	 * 更新bsp工单状态
	 * 
	 * @param map
	 * @return
	 */
	public boolean updateBspWorkorderRnTx(Map<String, Object> map){
		return bspwithdrawauditdao.updateBspWorkorder(map);
	}

	@Override
	public WorkOrderDto findWorkorderById(Long sequenceId) {
		return BeanConvertUtil.convert(WorkOrderDto.class,
				workOrderDao.findById(sequenceId));
	}

	public void setWorkOrderDao(WorkOrderDao workOrderDao) {
		this.workOrderDao = workOrderDao;
	}

	@Override
	public WorkOrderDto findByOrderSeq(Long orderSeq) {

		return BeanConvertUtil.convert(WorkOrderDto.class,
				workOrderDao.findByOrderSeq(orderSeq));
	}

	@Override
	public Page<WorkOrder> findByAuditMemberCodeAndOrderType(
			Page<WorkOrder> page, Long auditMembercode, final Integer orderType) {
		return workOrderDao.findByAuditMemberCodeAndOrderType(page, auditMembercode,
				orderType);
	}

	public void setBspwithdrawauditdao(BspWithdrawAuditDao bspwithdrawauditdao) {
		this.bspwithdrawauditdao = bspwithdrawauditdao;
	}

}
