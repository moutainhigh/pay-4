///**
// * 
// */
//package com.pay.acc.service.member;
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
//import com.pay.acc.service.member.MemberCreateService;
//
///**
// * 
// * @date 2010-7-16
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberCreateServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-memberCreateService")
//	private MemberCreateService memberCreateService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	@Test
//	public void testdoCreateTempMemberRdTx()throws Exception{
//		memberCreateService.doCreateTempMemberRdTx("jinhaha@gmail.com");
//	}
//}
