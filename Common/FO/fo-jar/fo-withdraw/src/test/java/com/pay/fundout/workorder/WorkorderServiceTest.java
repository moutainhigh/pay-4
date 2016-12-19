/**
 *  File: WorkorderServiceTest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fundout.workorder;

import java.util.Date;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.pay.fundout.AbstractTestNG;
import com.pay.fundout.withdraw.dto.workorder.WorkOrderDto;
import com.pay.fundout.withdraw.model.work.WorkOrder;
import com.pay.fundout.withdraw.service.workorder.WorkorderService;
import com.pay.inf.dao.Page;

public class WorkorderServiceTest extends AbstractTestNG {

	@Resource
	private WorkorderService workorderService;

	// @Test
	public void testSave() {
		WorkOrderDto order = new WorkOrderDto();

		for (int i = 0; i < 20; i++) {
			order.setAuditMembercode(10000000001L);
			order.setAuditOperator("adminA");
			order.setAuditRemark("提现");
			order.setCreateDate(new Date());
			order.setCreateMembercode(10000000003L);
			order.setCreateOperator("terry");
			order.setOrderSeq(2001105301617004703L + i);
			order.setOrderType(0);
			order.setStatus(0);
			order = workorderService.createWorkorderRnTx(order);
		}

		Assert.assertNotNull(order.getSequenceId());
	}

	// @Test
	public void testUpdate() {

		WorkOrderDto order = new WorkOrderDto();

		order.setAuditMembercode(10000000002L);
		order.setAuditOperator("adminA");
		order.setAuditRemark("this is a test.");
		order.setCreateDate(new Date());
		order.setCreateMembercode(10000000001L);
		order.setCreateOperator("adminA");
		order.setOrderSeq(2001105192236003582L);
		order.setOrderType(0);
		order.setUpdateDate(new Date());
		order.setStatus(0);
		order.setExternalInfo("gwzh");
		order.setSequenceId(7L);

		boolean result = workorderService.updateWorkorderRnTx(order);

		Assert.assertTrue(result);
	}

	// @Test
	public void testFindById() {
		Long sequenceId = 5L;

		WorkOrderDto order = workorderService.findWorkorderById(sequenceId);

		Assert.assertNotNull(order);
	}

	// @Test
	public void testFindByOrderSeq() {
		Long orderSeq = 2001105192236003581L;
		WorkOrderDto order = workorderService.findByOrderSeq(orderSeq);

		Assert.assertNotNull(order);
	}

	@Test
	public void testFindByAuditMemberCode() {
		Long auditMembercode = 20000000098L;
		Integer orderType = 0;

		Page<WorkOrder> page = new Page<WorkOrder>();
		page.setPageNo(1);
		page.setPageSize(10);
		Page<WorkOrder> result = workorderService
				.findByAuditMemberCodeAndOrderType(page, auditMembercode,
						orderType);

		Assert.assertNotNull(result);
	}
}
