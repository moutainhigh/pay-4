/**
 *  File: MerchantNotifyTest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-27   Sany        Create
 *
 */
package com.test.notify;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.api.service.notify.MerchantNotifyService;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;

/**
 * 商户通知testng
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml","classpath*:config/**/*.xml"})
public class MerchantNotifyTest extends AbstractTestNGSpringContextTests {
	
	@Resource(name = "fo-api-NotifyBatchPaymentDetail")
	private MerchantNotifyService MerchantNotifyService;
	
	@Test
	public void testNotify() {
		System.out.println("测试");
		BatchPaymentOrder order  = new BatchPaymentOrder();
		order.setBusinessBatchNo("BZ1324868464140");//批次号
		order.setCreateDate(new Date());
		order.setCreator("sandy");
		order.setFee(10000L);
		order.setIsPayerPayFee(1);
		order.setOrderAmount(50000L);
		order.setOrderId(2001112262003024805L);
		order.setOrderSmallType("103");
		order.setOrderStatus(101);
		order.setOrderType(2);
		order.setPayerAcctCode("20010100100011000000000110");
		order.setPayerAcctType(10);
		order.setPayerLoginName("pay@pay.com");
		order.setPayerMemberCode(10000000001L);
		order.setPayerMemberType(2);
		order.setPayerName("企业测");
		order.setPaymentCount(5);
		
		MerchantNotifyService.notifyMerchant(order);
	}
}
