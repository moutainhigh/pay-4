/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;

/**
 * @author fangzhang
 *
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class BatchPay2BankRequestServiceTest extends AbstractTestNGSpringContextTests {
	
	@Resource(name="fo-order-batchPay2BankRequestService")
	private BatchPay2BankRequestService batchPaymentService;

	@Test
	public void testProcessCompleteBatchPay2BankOrder(){
		batchPaymentService.processCompleteBatchPay2BankOrder();
	}
}
