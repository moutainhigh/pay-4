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
//import com.pay.acc.exception.MaMemberQueryException;
//import com.pay.acc.service.account.dto.MaResultDto;
//import com.pay.acc.service.member.MemberQueryService;
//import com.pay.acc.service.member.dto.MemberBaseInfoBO;
//import com.pay.acc.service.member.dto.MemberInfoDto;
//
///**
// * 
// * @date 2010-7-16
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberQueryServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-memberQueryService")
//	private MemberQueryService memberQueryService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	@Test
//	public void testMemberQueryService() {
//		Assert.assertNotNull(this.memberQueryService);
//	}
//
//	@Test()
//	public void testDoQueryMemberVerifyNsTx() throws MaMemberQueryException{
//		String loginName = "sunvy_613@163.com";
////		Long memberCode =10000000001L; 
//		Integer memberType = 2;
//		Integer acctType = 10;
//		MemberInfoDto memberInfoDto =	memberQueryService.doQueryMemberInfoNsTx(loginName, null, null, null);
//		Assert.assertNotNull(memberInfoDto);
//		System.out.println(memberInfoDto.getMemberCode() +" status :   "+memberInfoDto.getStatus());
//		Assert.assertEquals(memberInfoDto.getMemberCode().longValue(), 10000005015L);
//	}
//	
//	//Expected exception com.pay.acc.exception.MaMemberQueryException but got org.testng.TestException
//	@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoQueryMemberVerifyNsTxWithWrongMemberType() throws MaMemberQueryException{
//		String loginName = null;
//		Long memberCode = 10000000109L; 
//		Integer memberType = 2;//错误的会员类型
//		Integer acctType = 10;
//		MemberInfoDto memberInfoDto =	memberQueryService.doQueryMemberInfoNsTx(loginName, memberCode, memberType, acctType);
//		Assert.assertNull(memberInfoDto);
//	}
//	
//	//Expected exception com.pay.acc.exception.MaMemberQueryException but got org.testng.TestException
//	//错误的账户类型查询没有异常，返回null
//	@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoQueryMemberVerifyNsTxWithWrongAcctType() throws MaMemberQueryException{
//		String loginName = null;
//		Long memberCode = 10000000109L; 
//		Integer memberType = 1;
//		Integer acctType = 201111111;//错误的账户类型
//		MemberInfoDto memberInfoDto =	memberQueryService.doQueryMemberInfoNsTx(loginName, memberCode, memberType, acctType);
//		Assert.assertNull(memberInfoDto);
//	}
//	
//	//Expected exception com.pay.acc.exception.MaMemberQueryException but got org.testng.TestException
//	//错误的会员号查询没有异常，返回null
//	@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoQueryMemberVerifyNsTxWithWrongMemberCode() throws MaMemberQueryException{
//		String loginName = null;
//		Long memberCode = 10000000109L; //错误的会员号
//		Integer memberType = 1;
//		Integer acctType = 10;
//		MemberInfoDto memberInfoDto =	memberQueryService.doQueryMemberInfoNsTx(loginName, memberCode, memberType, acctType);
//		Assert.assertNull(memberInfoDto);
//	}
//	
//	//Expected exception com.pay.acc.exception.MaMemberQueryException but got org.testng.TestException
//	//错误的登录名查询没有异常，返回null
//	@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoQueryMemberVerifyNsTxWithWrongLoginName() throws MaMemberQueryException{
//		String loginName = "12341359792kljfdlskjh";
//		Long memberCode = 10000000109L; //错误的会员号
//		Integer memberType = 1;
//		Integer acctType = 10;
//		MemberInfoDto memberInfoDto =	memberQueryService.doQueryMemberInfoNsTx(loginName, memberCode, memberType, acctType);
//		Assert.assertNull(memberInfoDto);
//	}
//	
//	@Test
//	public void testQueryMemberBaseInfoByMemberCode() throws MaMemberQueryException{
//		Long memberCode = 10000000690L; 
//		MemberBaseInfoBO memberBaseInfoBO = memberQueryService.queryMemberBaseInfoByMemberCode(memberCode);
//		Assert.assertNotNull(memberBaseInfoBO);
//	}
//	
//	@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testQueryMemberBaseInfoByMemberCodeWithException() throws MaMemberQueryException{
//		Long memberCode = 10000000109L; 
//		MemberBaseInfoBO memberBaseInfoBO = memberQueryService.queryMemberBaseInfoByMemberCode(memberCode);
//		Assert.assertNull(memberBaseInfoBO);
//	}
//	
//	@Test
//	public void testDoVerifyLoginPassword(){
//		MaResultDto result = memberQueryService.doVerifyLoginPassword("13764563880", "asdf1234");
//		MaResultDto result1 = memberQueryService.doVerifyLoginPassword("13764563880", "asdfasd4");
//		MaResultDto result2 = memberQueryService.doVerifyLoginPassword("13764563880", "asdfasdf3");
//		MaResultDto result3 = memberQueryService.doVerifyLoginPassword("13764563880", "asdfasdf4");
//		MaResultDto result4 = memberQueryService.doVerifyLoginPassword("13764563880", "lv840214");
//		MaResultDto result6 = memberQueryService.doVerifyLoginPassword("13764563880", "asdf12341");
//		MaResultDto result7 = memberQueryService.doVerifyLoginPassword("13764563880", "asdf1234");
//		Assert.assertNotNull(result);
//	}
//	
//	@Test
//	public void testQueryMemberBaseInfo() throws MaMemberQueryException{
//		String ssoUserId="123456";
//		MemberBaseInfoBO memberBaseInfoBO = memberQueryService.queryMemberBaseInfo(ssoUserId);
//		Assert.assertNotNull(memberBaseInfoBO);
//	}
//	
//	@Test
//	public void testQueryLastLoginTime(){
//		String lastLoginTime = memberQueryService.queryLastLoginTime(1111l);
//		Assert.assertNotNull(lastLoginTime);
//	}
//	
//	//@Test
////	public void testqueryMemberWhiteOrBlackList() throws AppUnTxException{
////		@SuppressWarnings("unused")
////		List<BlackWhiteListDto> list = memberQueryService.queryMemberWhiteOrBlackList(new Long(123), 123);
////	}
//}
