//package com.pay.pricingstrategy;
//
//import java.sql.Timestamp;
//import java.util.Date;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.pricingstrategy.util.PricingStrategyUtil;
//
//@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
//@TransactionConfiguration(transactionManager = "pricingstrategyTransactionManager", defaultRollback = true)
//@Transactional
//public class PricingStrategyUtilTest extends
//		AbstractTransactionalTestNGSpringContextTests {
//
//	@Test
//	public void testFormatFee() {
//		Long money = 50L * 1000;
//		money = PricingStrategyUtil.formatFee(money);
//
//		Assert.assertEquals(money.longValue(), 10);
//	}
//
//	@Test
//	public void testBetweenEquals() {
//		Assert.assertTrue(PricingStrategyUtil
//				.isBetweenEquals(100L, 100L, 8000L));
//		Assert.assertTrue(PricingStrategyUtil
//				.isBetweenEquals(100L, 200L, 8000L));
//		
//		Assert.assertFalse(PricingStrategyUtil
//				.isBetweenEquals(100L, 80L, 8000L));
//		Assert.assertFalse(PricingStrategyUtil
//				.isBetweenEquals(100L, 8001L, 8000L));
//	}
//	
//	@Test
//	public void testRound() {
//		Assert.assertEquals(PricingStrategyUtil.round(0.5D, 0),1);
//		Assert.assertEquals(PricingStrategyUtil.round(0.4D, 0),0);
//	}
//	
//	
//	@Test
//	public void testIsNullDate() {
//		Assert.assertTrue(PricingStrategyUtil.isNullDate(null));		
//	}
//	
//	@Test
//	public void testGetDate() {
//		Date date = PricingStrategyUtil.getDate(2000, 11, 1);
////		Assert.assertEquals(date.getYear(),100);		
//		
//	}
//	
//	@Test
//	public void testBetweenEquals2() {
//		Timestamp min = new Timestamp(PricingStrategyUtil.getDate(2000, 10, 1).getTime());
//		
//		Timestamp mid = new Timestamp(PricingStrategyUtil.getDate(2000, 11, 1).getTime());
//		
//		Timestamp max = new Timestamp(PricingStrategyUtil.getDate(2001, 10, 1).getTime());
//		
//		Timestamp d1900 = new Timestamp(PricingStrategyUtil.getDate(1900, 1, 1).getTime());
//		
//		Assert.assertTrue(PricingStrategyUtil.isBetweenEquals( min, mid, max));
//		Assert.assertTrue(PricingStrategyUtil.isBetweenEquals( min, mid, d1900));
//		
//		Assert.assertTrue(PricingStrategyUtil.isBetweenEquals( min, mid, null));
//	}
//	
//	
//	
//	
//}
