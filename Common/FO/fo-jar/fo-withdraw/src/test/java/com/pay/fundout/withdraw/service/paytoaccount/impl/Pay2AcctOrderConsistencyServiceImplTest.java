package com.pay.fundout.withdraw.service.paytoaccount.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.orderconsistency.PayToAccountConsistencyService;

@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml" })
public class Pay2AcctOrderConsistencyServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-pay2Acct-orderconsistency-Service")
	private PayToAccountConsistencyService testService;

	//@Test
	public void testCon() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", "2010-12-30 00:00:00");
		map.put("endDate", "2010-12-30 23:00:00");
		map.put("parentOrder", "2001012301129025776");
		List<Pay2AcctOrder> orderList = testService.searchMassPayOrder101(map);
		System.out.println("0-----------------------------"+orderList.size());
		for(Pay2AcctOrder o:orderList){
			System.out.println(o.getSequenceId());
		}
		
		map.put("parentOrder", "2001012301300025792");
		 orderList = testService.searchMassPayOrder112(map);
		 System.out.println("2-----------------------------"+orderList.size());
			for(Pay2AcctOrder o:orderList){
				System.out.println(o.getSequenceId());
			}
		
	}

	//@Test
	public void test2() throws Exception{
		testService.createBackFundmentOrder(2001012301300025806L);
	}
	//@Test
	public void test3() throws Exception{
		testService.massPayOrderProceedToFail(2001012301136025783L);
	}
	@Test
	public void test4() throws Exception{
		testService.massPayOrderProceedToSuccess(2001012301136025781L);
	}
}
