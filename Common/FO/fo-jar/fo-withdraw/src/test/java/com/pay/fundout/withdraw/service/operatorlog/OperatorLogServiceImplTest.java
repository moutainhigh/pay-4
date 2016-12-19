/**
 *  File: OperatorLogServiceImplTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.operatorlog;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;

/**
 * 操作员日志服务testNgo
 * @author zliner
 *
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml","classpath:/context/*.xml" })
public class OperatorLogServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name="fundout-operatorlog-operatorlogService")
	private OperatorlogService operatorlogService;
	//set注入
	public void setOperatorlogService(final OperatorlogService param) {
		this.operatorlogService = param;
	}
	@Test
	public void testSaveOperatorLog() {
		OperatorlogDTO dto = new OperatorlogDTO();
		dto.setBusiOrderId("AAAAA1BBBBB");
		dto.setCreationDate(new Date());
		dto.setLogType(3);
		dto.setLogTypeDesc("提现未过风控处理");
		dto.setMark("提现订单号为BBB,于2010-09-15过风控");
		dto.setOperator("joss");
//		dto.setSequenceid(Long.valueOf(1));
		operatorlogService.saveOperatorLog(dto);
	}
}
