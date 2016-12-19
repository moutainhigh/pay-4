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
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.acc.exception.MaMemberVerifyException;
//import com.pay.acc.service.member.MemberVerifyService;
//import com.pay.acc.service.member.dto.MemberVerifyDto;
//import com.pay.acc.service.member.dto.MemberVerifyResult;
//
///**
// * 
// * @date 2010-7-16
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberVerifyServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-memberVerifyService")
//	private MemberVerifyService accMemberVerifyService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	//@Test
//	public void testMemberVerifyService() {
//		Assert.assertNotNull(this.accMemberVerifyService);
//	}
//
//	
//	//@Test
//	public void testDoQueryRealNameVerifyNsTx()throws MaMemberVerifyException{
//		long memberCode = 10000000114L;
//		boolean bool = accMemberVerifyService.doQueryRealNameVerifyNsTx(memberCode);
//		Assert.assertEquals(bool, true);
//	}
//	
//	//@Test(expectedExceptions=MaMemberVerifyException.class)
//	public void testDoQueryRealNameVerifyNsTxWithException() throws MaMemberVerifyException{
//		boolean bool = accMemberVerifyService.doQueryRealNameVerifyNsTx(10000000114L);
//		Assert.assertEquals(bool, false);
//	}
//
//	//@Test
//	public void testDoVerifyMemberSecurityQuestionNsTx()throws MaMemberVerifyException{
//		boolean flag=false;  
//		flag = accMemberVerifyService.doVerifyMemberSecurityQuestionNsTx(10000000114L,1,"123321");
////		Assert.assertNotNull(flag);
//		Assert.assertFalse(flag);
//	}
//	
//	//@Test
//	public void testDoQueryMemberVerifyInfoNsTx() throws MaMemberVerifyException{
//		MemberVerifyDto memberVerifyDto=null;
//		try {
//			memberVerifyDto = accMemberVerifyService.doQueryMemberVerifyInfoNsTx(10000000087L);
//		} catch (MaMemberVerifyException e) {
////			System.out.println("---------------"+e.getErrorEnum().getMessage());
//			e.printStackTrace();
//		}
//		Assert.assertNotNull(memberVerifyDto);
//	}
//	
//	@Test
//	public void testQueryMemberVerifyByMemberCode()throws Exception{
//		long a = 0 ;
//		MemberVerifyResult result = accMemberVerifyService.QueryMemberVerifyByMemberCode(a);
//		System.out.println(result.isVerify());
//		System.out.println(result.getMemberLevel());
//	}
//}
