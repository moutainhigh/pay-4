//package com.pay.acc.service.account;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import junit.framework.Assert;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.annotations.Test;
//
//
//
///**
// * @author Administrator
// *
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class BookkeepingServiceTest extends AbstractTransactionalTestNGSpringContextTests{
//	@Resource(name = "acc-bookkeepingService")
//	private BookkeepingService bookkeepingService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//
//	@Test
//	public void isSuccess(){
//		
//		boolean r = bookkeepingService.isChargeSuccess("2001012171703016006", 121,1);
//		Assert.assertNotNull(r);
//
//		boolean r1= bookkeepingService.isChargeSuccess("20010121717030160061", 121,1);
//		Assert.assertNotNull(r1);
//	
//	}
//
//}
