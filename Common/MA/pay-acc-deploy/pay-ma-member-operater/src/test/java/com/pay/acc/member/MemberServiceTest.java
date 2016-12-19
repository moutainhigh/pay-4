//package com.pay.acc.member;
//
//import junit.framework.Assert;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.Test;
//
//import com.pay.acc.member.dto.MemberDto;
//import com.pay.acc.member.service.MemberCreateTempService;
//import com.pay.acc.member.service.MemberService;
//
////@ContextConfiguration(locations = { "classpath*:context/*.xml" })
////@TransactionConfiguration(transactionManager = "maTxManager", defaultRollback = true)
////@Transactional
////public class MemberVerifyServiceTest extends AbstractTransactionalTestNGSpringContextTests {
////@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml", "classpath*:config/spring/riskconctrol/*.xml" })
//@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:ibatis/accmember/member.xml"})
//public class MemberServiceTest extends AbstractTestNGSpringContextTests{
//	
//	@Autowired
//	private MemberService memberService;
//	@Autowired
//	private MemberCreateTempService memberCreateTempService;
//	
////	@Resource(name="dataSourceAcc")
////	public void setDataSource(DataSource dataSource){
////		super.setDataSource(dataSource);
////	}
//	
//	@Test
//	public void testMemberService(){
//		Assert.assertNotNull(this.memberService);
//	}
//	
//	
//	@Test
//	public void testQueryMemberByMemberCode(){
//		Long memberCode=10000000029L;
//		try {
//			MemberDto member= memberService.queryMemberByMemberCode(memberCode);
//			Assert.assertNotNull(member);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//}
