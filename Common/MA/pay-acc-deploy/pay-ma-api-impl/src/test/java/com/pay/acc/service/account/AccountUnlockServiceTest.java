//package com.pay.acc.service.account;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.annotations.Test;
//
//import com.pay.acc.service.account.AccountUnlockService;
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountUnlockServiceTest extends
//AbstractTransactionalTestNGSpringContextTests {
//
//	@Resource(name = "acc-accountUnlockService")
//	private AccountUnlockService accountUnlockService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//
//	@Test
//	public void doVerify() {
//		accountUnlockService.unLock(10000000710L,10);
//	}
//	
//}
