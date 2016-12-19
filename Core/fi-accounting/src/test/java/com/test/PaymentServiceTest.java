/**
 * 
 */
package com.test;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PaymentService;

/**
 * @author chaoyue
 *
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class PaymentServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "txncore-paymentService")
	private PaymentService paymentService;

	@Test
	public void testLiqu() {

		PartnerSettlementOrder partnerSettlementOrder = new PartnerSettlementOrder();
		partnerSettlementOrder.setAmount(80000L);
		partnerSettlementOrder.setAssureAmount(80000L);
		partnerSettlementOrder.setOrderAmount(80000L);
		partnerSettlementOrder.setSettlementCurrencyCode("USD");
		partnerSettlementOrder.setId(1591505241357000133L);
		partnerSettlementOrder.setOrderId("1591505241357000133");
		partnerSettlementOrder.setCurrencyCode("USD");
		partnerSettlementOrder.setPartnerId(10000003593L);
		paymentService.liquidationAssureRnTx(partnerSettlementOrder);
	}
}
