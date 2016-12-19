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
//import com.pay.acc.service.account.AccountLockService;
//import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
//import com.pay.acc.service.account.constantenum.AcctTypeEnum;
//import com.pay.acc.service.account.exception.MaAcctLockException;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountLockServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name = "acc-accountLockService")
//	private AccountLockService accountLockService;
//	
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testAccountLockService(){
//		Assert.assertNotNull(this.accountLockService);
//	}
//	@Test
//	public void testDoHandlerAccountLockAcctRnTx(){
//		Long memberCode =50000002008l;
//		try {
//		 boolean result=this.accountLockService.doHandlerAccountLockRnTx(memberCode, AcctTypeEnum.BASIC_CNY, AcctLockTypeEnum.UNFREEZE_ACCOUNT);
//		 Assert.assertTrue(result);
//		} catch (MaAcctLockException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void testDoHandlerAccountLockFreezeInRnTx(){
//		Long memberCode =50000002008l;
//		try {
//			boolean result=this.accountLockService.doHandlerAccountLockRnTx(memberCode, AcctTypeEnum.BASIC_CNY, AcctLockTypeEnum.UNFREEZE_IN);
//			Assert.assertTrue(result);
//		} catch (MaAcctLockException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void testDoHandlerAccountLockFreezeOutRnTx(){
//		Long memberCode =10000005054L;
//		try {
//			boolean result=this.accountLockService.doHandlerAccountLockRnTx(memberCode, AcctTypeEnum.BASIC_CNY, AcctLockTypeEnum.UNFREEZE_OUT);
//			Assert.assertTrue(result);
//		} catch (MaAcctLockException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
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
