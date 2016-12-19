 /** @Description 
 * @project 	poss-refund
 * @file 		RefundManageServiceImplTest.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-31		sunsea.li		Create 
*/
package com.pay.poss.refund.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.service.RefundManageService;

/**
 * <p>充退管理服务具体实现Test类</p>
 * @author sunsea.li
 * @since 2010-8-25
 * @see 
 */
@ContextConfiguration(locations = {"classpath*:config/env/test-dataAccessContext.xml","classpath*:config/spring/platform/*.xml",
					"classpath*:context/spring/**/*.xml",
					"classpath*:config/spring/**/*.xml"
		})
public class ServiceTestNG  extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RefundManageService refundManageService;	//充退服务
	
	@Test
	public void serviceTest(){
		RefundOrderM order = new RefundOrderM();
		order.setApplyAmount(new BigDecimal(100));
		order.setApplyFrom("test");
		order.setApplyReason("test");
		order.setApplyTime(new Date());
		order.setMemberAcc("test");
		order.setMemberAccType(1);
		order.setMemberCode("test");
		order.setMemberName("test");
		order.setMemberType("test");
		order.setOrderKy(2001009291943000042L);
		order.setStatus(1);
		order.setWorkflowKy("test");
		order.setWorkOrderKy(1l);
		order.setWorkOrderStatus("test");
		refundManageService.updateRefundOrder(order);
	}

}
