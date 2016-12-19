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
//import com.pay.acc.service.account.SafeQuestionVerifyService;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class SafeQuestion4VerifyLoginpwdServiceTest extends
//		AbstractTransactionalTestNGSpringContextTests {
//
//	@Resource(name = "acc-safeQuestion4VerifyLoginpwdService")
//	private SafeQuestionVerifyService safeQuestionVerifyService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//
//	@Test
//	public void testSafeQuestionVerifyService() {
//		Assert.assertNotNull(this.safeQuestionVerifyService);
//	}
//
//	@Test
//	public void doVerify() {
//		safeQuestionVerifyService.doVerify(10000000710L, 1, "shanghai");
//		safeQuestionVerifyService.doVerify(10000000710L, 1, "sh");
//		safeQuestionVerifyService.doVerify(10000000710L, 1, "sh");
//		safeQuestionVerifyService.doVerify(10000000710L, 1, "sh");
//	}
//
//}
