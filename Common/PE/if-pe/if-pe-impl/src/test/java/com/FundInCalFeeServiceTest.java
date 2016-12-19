package com;

import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgCode;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class FundInCalFeeServiceTest extends
AbstractTestNGSpringContextTests {


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
			calFeeReqDto.setPayer("10000000005");                  //科目无需设置payee
			
			calFeeReqDto.setPayerMemberAcctCode("1000000000510");    //科目无需设置账户账号全称
			calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000000510");  //科目无需设置账户全称
		}else{
			calFeeReqDto.setPayerOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayerOrgCode(OrgCode.MOCK001.getValue());    
		}
		
		if(payee==1){
			calFeeReqDto.setPayeeOrgType(1);
			calFeeReqDto.setPayeeAcctType(10);
			calFeeReqDto.setPayee("10000005006");			
			calFeeReqDto.setPayeeMemberAcctCode("1000000500610");
			calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000500610");
		}else{
	           //魏峰定义
			calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayeeOrgCode(OrgCode.MOCK001.getValue());
		}
		return calFeeReqDto ;
	}
	
	@Test
	public void testCalculateFeeDetai301() throws Exception {
		CalFeeRequest calFeeReqDto = initCalFeeRequest(301,3,1);
//		CalFeeReponse calFeeReponse = peService.processPayment(calFeeReqDto);
//		peService.accountingCalFeeReponse(calFeeReponse);
	}
	
	@Test
	public void testCalculateFeeDetai302() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(302,3,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai310() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(310,3,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai311() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(311,1,4);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai312() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(312,4,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai313() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(313,1,4);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai314() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(314,3,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai315() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(315,1,3);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai320() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(320,1,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai321() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(321,1,3);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai322() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(322,3,3);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai323() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(323,1,3);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
	@Test
	public void testCalculateFeeDetai324() throws Exception {
//		CalFeeRequest calFeeReqDto = initCalFeeRequest(324,1,1);
//		CalFeeReponse calFeeRespone = peService.processPayment(calFeeReqDto);
	}
	
}