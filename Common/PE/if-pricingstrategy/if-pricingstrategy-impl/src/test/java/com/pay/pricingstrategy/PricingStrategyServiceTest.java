//package com.pay.pricingstrategy;
//
//import javax.annotation.Resource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.pricingstrategy.service.CalPricingStrategyParam;
//import com.pay.pricingstrategy.service.PricingStrategyService;
//
//
//@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
//@TransactionConfiguration(transactionManager = "pricingstrategyTransactionManager", defaultRollback = true)
//@Transactional
//public class PricingStrategyServiceTest extends
//AbstractTransactionalTestNGSpringContextTests{
//	
//	@Resource
//	private PricingStrategyService pricingStrategyService;
//	
//	//会员  固定费用
//	@Test
//	public void testCalculatePriceByMember() {
////		230  10015225196       123514
////		4	116502	123514	1			1	0	0	5000	0	0	0
//		Integer paymentServiceCode = 230;
//		Long memberCode = 10015225196L;
//		Integer serviceLevelCode = null;		
//
//		Long transactionAmount = 1500L*1000;
//		Integer terminaltype = 1;
//		String reservedCode = null;
//		
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(memberCode);
//		calParam.setPaymentServiceCode(paymentServiceCode);
//		calParam.setServiceLevelCode(serviceLevelCode);
//		calParam.setTransactionAmount(transactionAmount);
//		calParam.setTerminaltype(terminaltype);
//		calParam.setReservedCode(reservedCode);
//		calParam.setMfDatetime(new java.util.Date());
//		
//		Long fee = pricingStrategyService.calculatePrice(calParam);
//		
//		Assert.assertEquals(fee.longValue(), 5*1000);
//	}
//	
//	
//	//会员  固定费用_费率 及上下限
//	@Test
//	public void testCalculatePriceByMember2() {
//		//		2	420	104021	10012606580	3	2008/5/15	1900/1/1	0	1	4	
////	1	100009	104021	1	1900/1/1	1900/1/1	1	0	0	0	0	0	0		
//
//		
//		Integer paymentServiceCode = 420;
//		Long memberCode = 10012606580L;
//		Integer serviceLevelCode = null;		
//
//		Long transactionAmount = 1500L*1000;
//		Integer terminaltype = 1;
//		String reservedCode = null;
//		
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(memberCode);
//		calParam.setPaymentServiceCode(paymentServiceCode);
//		calParam.setServiceLevelCode(serviceLevelCode);
//		calParam.setTransactionAmount(transactionAmount);
//		calParam.setTerminaltype(terminaltype);
//		calParam.setReservedCode(reservedCode);
//		calParam.setMfDatetime(new java.util.Date());
//		
//		Long fee = pricingStrategyService.calculatePrice(calParam);
//		
//		Assert.assertEquals(fee.longValue(), 0);
//	}
//	
//	
//	//全局  固定费用
//	@Test
//	public void testCalculatePriceByMember3() {
////		1	791	488		
//
////		7	1000	488	1			1	0	0	1000	0	0	0		
//
//		Integer paymentServiceCode = 791;
////		Long memberCode = 10015225196L;
//		Long memberCode = null;
//		Integer serviceLevelCode = null;		
//
//		Long transactionAmount = 1500L*1000;
//		Integer terminaltype = 1;
//		String reservedCode = null;
//		
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(memberCode);
//		calParam.setPaymentServiceCode(paymentServiceCode);
//		calParam.setServiceLevelCode(serviceLevelCode);
//		calParam.setTransactionAmount(transactionAmount);
//		calParam.setTerminaltype(terminaltype);
//		calParam.setReservedCode(reservedCode);
//		calParam.setMfDatetime(new java.util.Date());
//		
//		Long fee = pricingStrategyService.calculatePrice(calParam);
//		
//		Assert.assertEquals(fee.longValue(), 1000);
//	}
//	
//	
//	//全局   固定费用_费率 及上下限
//	@Test
//	public void testCalculatePriceByMember4() {
////		1	460	70514	
////		6	70506	70514	1			1	0	0	0	100	99999999000	5000
//
//		Integer paymentServiceCode = 460;
////		Long memberCode = 10015225196L;
//		Long memberCode = null;
//		Integer serviceLevelCode = null;		
//
//		Long transactionAmount = 1500L*1000;
//		Integer terminaltype = 1;
//		String reservedCode = null;
//		
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(memberCode);
//		calParam.setPaymentServiceCode(paymentServiceCode);
//		calParam.setServiceLevelCode(serviceLevelCode);
//		calParam.setTransactionAmount(transactionAmount);
//		calParam.setTerminaltype(terminaltype);
//		calParam.setReservedCode(reservedCode);
//		calParam.setMfDatetime(new java.util.Date());
//		
//		Long fee = pricingStrategyService.calculatePrice(calParam);
//		
//		Assert.assertEquals(fee.longValue(), 15000);
//	}
//	
//	
//
//	//服务等级   费率 及上下限
//	@Test
//	public void testCalculatePriceByMember5() {
////		1	230	421	企业提现收费	5	200		2	2007/4/17	1900/1/1	0	1	2	
////		7	347	421	1	1900/1/1	1900/1/1	1	0	0	0	10	50000	5000		
//		Integer paymentServiceCode = 230;
////		Long memberCode = 10015225196L;
//		Long memberCode = null;
//		Integer serviceLevelCode = 200;		
//
//		Long transactionAmount = 1500L*1000;
//		Integer terminaltype = 1;
//		String reservedCode = null;
//		
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(memberCode);
//		calParam.setPaymentServiceCode(paymentServiceCode);
//		calParam.setServiceLevelCode(serviceLevelCode);
//		calParam.setTransactionAmount(transactionAmount);
//		calParam.setTerminaltype(terminaltype);
//		calParam.setReservedCode(reservedCode);
//		calParam.setMfDatetime(new java.util.Date());
//		
//		Long fee = pricingStrategyService.calculatePrice(calParam);
//		
//		Assert.assertEquals(fee.longValue(), 5000L);
//	}
//	
//	
//		
//
//	
//	
//	
//	
//
//}
