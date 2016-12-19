package com;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgCode;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class FundOutCalFeeServiceTest extends
//		AbstractTransactionalTestNGSpringContextTests {
	AbstractTestNGSpringContextTests{
	@Resource
	private PEService peService;
	private  String getOrderCode(int pkgCode){
//		int a = Integer.parseInt(pkgCode);
		int a = pkgCode ;
		int b = a/10 ;
		int c = b*10 ;
//		System.out.println(c);
		return c+"" ;
	}
	
	private CalFeeRequest initCalFeeRequest(int PkgCode,int payer,int payee ){
		CalFeeRequest calFeeReqDto = new CalFeeRequest();
		
		calFeeReqDto.setOrderId("1021010081530013380");
		calFeeReqDto.setOrderAmount(100000l);
		calFeeReqDto.setSubmitAcctCode("0");
		calFeeReqDto.setAmount(100000l);
		
		
		calFeeReqDto.setPaymentServicePkgCode(PkgCode); //枚举类由魏峰统一提供
		calFeeReqDto.setDealCode(PkgCode);                //枚举类由魏峰统一提供
		calFeeReqDto.setOrderCode(Integer.parseInt(getOrderCode(PkgCode)));               //枚举类由魏峰统一提供
		calFeeReqDto.setPayMethod(1);                 //枚举类由魏峰统一提供
		calFeeReqDto.setTerminalType(TerminalType.WEB.getValue());
		
		
		calFeeReqDto.setRequestDate(new Date());
		
		if(payer==1){
			calFeeReqDto.setPayerOrgType(OrgType.MEMBER.getValue());
			calFeeReqDto.setPayerAcctType(10);          //科目无需设置账户类型
//			calFeeReqDto.setPayer("10000000099");                  //科目无需设置payee
//			10000000114 -- 会员策略
			calFeeReqDto.setPayer("10000009582");                  //科目无需设置payee
			
			calFeeReqDto.setPayerMemberAcctCode("1000000958210");    //科目无需设置账户账号全称
			calFeeReqDto.setPayerFullMemberAcctCode("20010100100011000000958210");  //科目无需设置账户全称
			calFeeReqDto.setPayerServiceLevel(100);
		}else{
			calFeeReqDto.setPayerOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayerOrgCode(OrgCode.MOCK001.getValue());    
		}
		
		if(payee==1){
			calFeeReqDto.setPayeeOrgType(1);
			calFeeReqDto.setPayeeAcctType(10);
			calFeeReqDto.setPayee("10000000200");			
			calFeeReqDto.setPayeeMemberAcctCode("1000000020010");
			calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000020010");
		}else{
	           //魏峰定义
			calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayeeOrgCode(OrgCode.MOCK001.getValue());
			calFeeReqDto.setPayeeServiceLevel(-1);
		}
		return calFeeReqDto ;
	}
	
	
	
	
	@Test
	public void testCalculateFeeDetai101() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(101,1,3);
//		CalFeeReponse calFeeRespone = peService.calculateFeeDetail(calFeeReqDto);
//		CalFeeReponse calFeeRespone = peService.caculateFee(calFeeReqDto);
//		System.out.println("sdfsdfsdf="+calFeeRespone.getPayerFee());
	}
	
	@Test
	public void testCalculateFeeDetai121() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(121,1,3);
//		CalFeeReponse calFeeRespone = peService.calculateFeeDetail(calFeeReqDto);
//		CalFeeReponse calFeeRespone = peService.caculateFee(calFeeReqDto);
//		CalFeeReponse calFeeRespone = ;
//		System.out.println("sdfsdfsdf="+peService.caculateFee(calFeeReqDto).getPayerFee());
//		System.out.println("sdfsdfsdf="+peService.processPayment(calFeeReqDto).getPayerFee());
	}
	
	
	
	
	
	@Test
	public void testCalculateFeeDetai170() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(170,1,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai171() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(171,1,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai122() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(122,1,3);
		
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
		
	}
	
	@Test
	public void testCalculateFeeDetai123() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(123,3,3);
//		calFeeReqDto.setHasCaculatedPrice(true);
//		calFeeReqDto.setAmount(96000L);
//		calFeeReqDto.setPayerFee(4000L);
//		calFeeReqDto.setHasCaculatedPrice(true);
		calFeeReqDto.setAmount(-96000L);
		calFeeReqDto.setPayerFee(4000L);
		
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	
	
}