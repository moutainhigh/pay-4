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
//import com.pay.acc.exception.MaMemberException;
//import com.pay.acc.exception.MaMemberQueryException;
//import com.pay.acc.member.dto.MemberInfoDto;
//import com.pay.acc.service.account.AccountInfoService;
//import com.pay.acc.service.account.dto.MaResultDto;
//import com.pay.acc.service.account.dto.VerifyResultDto;
//
///**
// * 
// * @date 2010-9-26
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountInfoServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-accountInfoService")
//	private AccountInfoService accountInfoService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	@Test
//	public void testAccountInfoService(){
//		Assert.assertNotNull(this.accountInfoService);
//	}
//	
//	
//	//@Test
//	public void testDoResetPayPwdRnTx()throws MaMemberException{
//		long memberCode=10000000072L;
//		int accountType=10;
//		String newPayPwd="test1234";
//		int flag = accountInfoService.doResetPayPwdRnTx(memberCode,accountType,newPayPwd);
//		Assert.assertEquals(flag, 1);
//		//Assert.assertFalse(flag);
//	}
//	
//	//@Test(expectedExceptions=MaMemberException.class)
//	public void testDoResetPayPwdRnTxWithException()throws MaMemberException{
//		long memberCode=10001110000072L;
//		int accountType=10;
//		String newPayPwd="test1234";
//		int flag = accountInfoService.doResetPayPwdRnTx(memberCode,accountType,newPayPwd);
//		Assert.assertNotSame(flag, 1);
//		//Assert.assertFalse(flag);
//	}
//	
//	
//	
//	//@Test(description="测试验证支付密码")
//	public void testDoVerifyPayPasswordNsTx()throws MaMemberQueryException{
//		Long memberCode=10000000072L;
//		Integer accountType=10;
//		String payPwd="qwerqwer2";
//		int flag = accountInfoService.doVerifyPayPasswordNsTx(memberCode,accountType,payPwd);
//		Assert.assertEquals(flag, 1);
//	}
//	
//	//@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoVerifyPayPasswordNsTxWithException()throws MaMemberQueryException{
//		Long memberCode=10000000072L;
//		Integer accountType=1023;
//		String payPwd="test1234";
//		int flag = accountInfoService.doVerifyPayPasswordNsTx(memberCode,accountType,payPwd);
//		Assert.assertNotSame(flag, 1);
//	}
//	
//	
//	//@Test(expectedExceptions=MaMemberQueryException.class)
//	public void testDoVerifySecurityQuestionNsTxWithException()throws MaMemberQueryException{
//		long memberCode=10000000351L;
//		int securQuestionId=1;
//		String answer="123321";
//		int flag = accountInfoService.doVerifySecurityQuestionNsTx(memberCode,securQuestionId,answer);
//		Assert.assertNotSame(flag,1);
//		//Assert.assertFalse(flag);
//		
//	}
//	
//	//@Test
//	public void testdoVerifyPayPassword(){
//		Long memberCode=50000000010L;
//		Integer accountType=10;
//		String payPwd="asdfasdf";
//		Long operatorId=200000L;
//		MaResultDto dto=this.accountInfoService.doVerifyPayPassword(memberCode, accountType, payPwd,null);
//		if(null!=dto){
//			System.out.println("status  : "+dto.getResultStatus()+" errorCode :" +dto.getErrorCode()+" errormsg :" +dto.getErrorMsg());
//			VerifyResultDto verifyDto=(VerifyResultDto) dto.getObject();
//			if(null!=verifyDto){
//				System.out.println("totalTime: "+verifyDto.getTotalTime()+ " leavingTime : "+verifyDto.getLeavingTime() +" leavingminutes: "+verifyDto.getLeavingMinute());
//
//			}
//		}
//	}
//	
//	@Test
//	public void testdoVerifyPayPassword2(){
//		String loginName = "pay@pay.com";
//		String operatorName = "admin11";
//		Integer accountType=10;
//		String payPwd="asdfasdf";
//		
//		MaResultDto dto=this.accountInfoService.doVerifyPayPassword(loginName,operatorName, accountType, payPwd);
//		if(null!= dto && dto.getResultStatus() == 1){
//			System.out.println("status  : "+dto.getResultStatus()+" errorCode :" +dto.getErrorCode()+" errormsg :" +dto.getErrorMsg());
//			
//			MemberInfoDto m = (MemberInfoDto) dto.getObject();
//			
//			System.out.println("memberCode: "+m.getMemberCode()+ " login pwd : "+m.getLoginPwd() +" greeting: "+m.getGreeting()
//					+" pay pwd :" + m.getPayPassWord() + " operatorId :" + m.getOperatorId());			
//		}else if(dto != null){
//			System.out.println("status  : "+dto.getResultStatus()+" errorCode :" +dto.getErrorCode()+" errormsg :" +dto.getErrorMsg());
//			
//			if(dto.getObject() != null){
//				VerifyResultDto verifyDto=(VerifyResultDto) dto.getObject();
//				System.out.println("totalTime: "+verifyDto.getTotalTime()+ " leavingTime : "+verifyDto.getLeavingTime() +" leavingminutes: "+verifyDto.getLeavingMinute());
//			}
//		}
//		
//	}
//}
