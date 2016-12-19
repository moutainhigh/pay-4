//package com.pay.acc.service.account;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.acc.service.account.EntryPriseRiskControlService;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class EntryPriseRiskControlServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//
//	@Resource(name="acc-entryPriseRiskControlService")
//	EntryPriseRiskControlService entryPriseRiskControlService;
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	@Test
//	public void testEntryPriseRiskControlServiceTest(){
//		Assert.assertNotNull(this.entryPriseRiskControlService);
//	}
//	
//	@Test
//	public void testqueryRiskControl(){
//		boolean flag=entryPriseRiskControlService.queryRiskControl(20000000015L, 203);
//		System.err.println("风控等级："+flag);
//	}
//	
//	@Test
//	public void testquerySettlePeriod(){
//		boolean flag=entryPriseRiskControlService.querySettlePeriod(20000000068L, 3);
//		System.err.println(" 结算周期： "+flag);
//	}
//}
