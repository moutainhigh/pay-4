///**
// * 
// */
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
//import com.pay.acc.service.account.FlushesBalanceService;
//import com.pay.acc.service.account.exception.MaAcctBalanceException;
//
///**
// * @author Administrator
// *
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class FlushesBalanceServiceTest  extends AbstractTransactionalTestNGSpringContextTests{
//	@Resource(name = "acc-flushesBalanceService")
//	private FlushesBalanceService flushesBalanceService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testAccountBalanceHandlerService(){
//		Assert.assertNotNull(this.flushesBalanceService);
//	}
//	
//	@Test
//	//冲正
//	public void testdoFlushesBalance(){
//		try {
//	        this.flushesBalanceService.doFlushesBalanceNsTx("2001012171703016117", "2010100900000000120", 501, 1000L, 30);
//        }
//        catch (MaAcctBalanceException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//        }
//	}
//	
//	
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
