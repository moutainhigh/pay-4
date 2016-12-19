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
//import com.pay.acc.exception.MaAcctUpdateException;
//import com.pay.acc.service.account.AccountUpdateService;
//
///**
// * 
// * @date 2010-7-16
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountUpdateServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-accountUpdateService")
//	private AccountUpdateService accountUpdateService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	
//	@Test
//	public void testDoQueryMemberBankNsTx()throws MaAcctUpdateException{
//		try {
//			Long memberCode=10000000109L;
//			Integer accountType=10;
//			String payPwd="11111111a";
//			boolean flag = accountUpdateService.doUpdateAccountForPayPwdRnTx(memberCode,accountType,payPwd);
//			Assert.assertNotNull(flag);
//			//Assert.assertFalse(flag);
//		} catch (MaAcctUpdateException e) {
//			// TODO Auto-generated catch block
////			System.out.println("******************"+e.getErrorEnum().getMessage()+"******************");
//		}
//	}
//	
//}
