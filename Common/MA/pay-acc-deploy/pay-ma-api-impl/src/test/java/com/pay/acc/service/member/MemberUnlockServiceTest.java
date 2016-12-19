//package com.pay.acc.service.member;
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
//import com.pay.acc.service.member.MemberUnlockService;
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberUnlockServiceTest  extends
//AbstractTransactionalTestNGSpringContextTests {
//
//	
//	@Resource(name = "acc-memberUnlockService")
//	private MemberUnlockService memberUnlockService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//
//	@Test
//	public void doVerify() {
//		memberUnlockService.unLock(10000000710L);
//	}
//}
