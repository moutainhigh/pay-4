///**
// * 
// */
//package com.pay.acc.service.account;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
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
//import com.pay.acc.service.account.dto.CalFeeDetailDto;
//import com.pay.acc.service.account.dto.CalFeeReponseDto;
//import com.pay.acc.service.account.dto.CalFeeRequestDto;
//import com.pay.acc.service.account.exception.MaAcctBalanceException;
//import com.pay.pe.helper.OrgType;
//import com.pay.pe.service.CalFeeDetail;
//import com.pay.pe.service.CalFeeReponse;
//import com.pay.util.BeanConvertUtil;
//
///**
// * @author Administrator
// *
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountBalanceHandlerServiceTest  extends AbstractTransactionalTestNGSpringContextTests{
//	@Resource(name = "acc-accountBalanceHandlerService")
//	private AccountBalanceHandlerService accountBalanceHandlerService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testAccountBalanceHandlerService(){
//		Assert.assertNotNull(this.accountBalanceHandlerService);
//	}
//	
//	@Test
//	public void testDoUpdateAcctBalanceRntx(){
//		try {
////			CalFeeServiceTest test=new CalFeeServiceTest();
////			CalFeeReponse calFeeRespone =test.getCalFeeReponse();
////			CalFeeReponseDto dto=this.setCalFeeReponseDto(calFeeRespone);
//		    Integer result=	this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto(), 2);
//
////		    Integer result=	this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto(), 2);
//		    Assert.assertNotNull(result);
//		    Assert.assertEquals(result.intValue(), 1);
//		} catch (MaAcctBalanceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public CalFeeReponseDto setCalFeeReponseDto(CalFeeReponse calFeeRespone) {
//		CalFeeReponseDto calFeeReponseDto =   BeanConvertUtil.convert(CalFeeReponseDto.class, calFeeRespone);
//		CalFeeRequestDto calFeeRequestDto = BeanConvertUtil.convert(CalFeeRequestDto.class, calFeeRespone.getCalFeeRequest());			
//		List<CalFeeDetailDto> calDetailList=new ArrayList<CalFeeDetailDto>();
//		for(CalFeeDetail calFeeDetail:calFeeRespone.getCalFeeDetails()){
//			CalFeeDetailDto calFeeDetailDtos= new CalFeeDetailDto();
//			calFeeDetailDtos.setAcctcode(calFeeDetail.getAcctcode());
//			calFeeDetailDtos.setCrdr(calFeeDetail.getCrdr());
//			calFeeDetailDtos.setCreatedate(calFeeDetail.getCreatedate());
//			calFeeDetailDtos.setCurrencyCode(calFeeDetail.getCurrencyCode());
//			calFeeDetailDtos.setDealId(calFeeDetail.getDealId());
//			calFeeDetailDtos.setEntrycode(calFeeDetail.getEntrycode());
//			calFeeDetailDtos.setExchangeRate(calFeeDetail.getExchangeRate());
//			calFeeDetailDtos.setMaBlanceBy(calFeeDetail.getMaBlanceBy());
//			calFeeDetailDtos.setPaymentServiceId(calFeeDetail.getPaymentServiceId());
//			calFeeDetailDtos.setStatus(calFeeDetail.getStatus());
//			calFeeDetailDtos.setText(calFeeDetail.getText());
//			calFeeDetailDtos.setTransactiondate(calFeeDetail.getTransactiondate());
//			calFeeDetailDtos.setValue(calFeeDetail.getValue());
//			calFeeDetailDtos.setVouchercode(calFeeDetail.getVouchercode());
//			calDetailList.add(calFeeDetailDtos);
//		}
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//		calFeeReponseDto.setCalFeeDetailDtos(calDetailList);			
//		return calFeeReponseDto;
//	}
//	
////	@Test(expectedExceptions=MaAcctBalanceException.class)
//	public void testDoUpdateAcctBalanceRntxWithException() throws MaAcctBalanceException{
//		 Integer result=this.accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto(), 2);
//		 Assert.assertNull(result);
//		
//	}
//	
//	
//	
//	private CalFeeReponseDto initCalFeeReponseDto(){
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
////		calFeeReponseDto.setPayeeFee(0l);
//		calFeeReponseDto.setPayerFee(5l);
//		String seialNo ="2010100900000000022";
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		
////		calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
////		calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
////		calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); // 科目无需设置账户账号全称
//		//付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		 // 科目无需设置账户类型 验证会员
////		calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
////		calFeeRequestDto.setPayerOrgCode("990310001");
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId(seialNo);  
//		calFeeRequestDto.setOrderAmount(1000l);
//		calFeeRequestDto.setPayerFullMemberAcctCode("20010000000011000000011010");
//		//收款方设置
//		 // 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//	
//		//payee 收款
////		calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		//payer 付款
//		
//		
//		
//		
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//		
//		//贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010200100011000000037710");
//		payCalFeeDetailDto.setCrdr(1);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(seialNo);
//		payCalFeeDetailDto.setMaBlanceBy(1);
//		payCalFeeDetailDto.setValue(1000l);
//		payCalFeeDetailDto.setEntrycode(1);
//		payCalFeeDetailDto.setStatus(1);
//		payCalFeeDetailDto.setDealType(1);
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		//借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010200100011000000037810");
//		revCalFeeDetailDto.setCrdr(2);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(seialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(1000l);
//		revCalFeeDetailDto.setEntrycode(2);
//		revCalFeeDetailDto.setStatus(1);
//		revCalFeeDetailDto.setDealType(1);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		
//		///费用
//		CalFeeDetailDto payCalFeeDetailDto1 = new CalFeeDetailDto();
//		payCalFeeDetailDto1.setAcctcode("2181018010001");
//		payCalFeeDetailDto1.setCrdr(1);
//		payCalFeeDetailDto1.setCreatedate(new Date());
//		payCalFeeDetailDto1.setDealId(seialNo);
//		payCalFeeDetailDto1.setMaBlanceBy(2);
//		payCalFeeDetailDto1.setValue(5l);
//		payCalFeeDetailDto1.setEntrycode(1);
//		payCalFeeDetailDto1.setStatus(1);
//		payCalFeeDetailDto1.setDealType(2);
//		calFeeDetailDtos.add(payCalFeeDetailDto1);
//		//借
//		CalFeeDetailDto revCalFeeDetailDto1 = new CalFeeDetailDto();
//		revCalFeeDetailDto1.setAcctcode("1151010010002");
//		revCalFeeDetailDto1.setCrdr(2);
//		revCalFeeDetailDto1.setCreatedate(new Date());
//		revCalFeeDetailDto1.setDealId(seialNo);
//		revCalFeeDetailDto1.setMaBlanceBy(1);
//		revCalFeeDetailDto1.setValue(5l);
//		revCalFeeDetailDto1.setEntrycode(2);
//		revCalFeeDetailDto1.setStatus(1);
//		revCalFeeDetailDto1.setDealType(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto1);
//		
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//		calFeeReponseDto.setVoucherCode(123423432423l);
//		return calFeeReponseDto;
//	}
////	private CalFeeReponseDto initCalFeeReponseDto(){
////		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
////		calFeeReponseDto.setPayeeFee(1l);
////		calFeeReponseDto.setPayerFee(2l);
////		
////		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
////		
//////		calFeeRequestDto.setOrderId("1001245640000655");
////		calFeeRequestDto.setOrderAmount(1000L);
////		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//////		calFeeReqDto.setAmount(100L);
////		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
////		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
////		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
////		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
////		calFeeRequestDto.setTerminalType(TERMINALTYPE.WEB.getValue());
////		calFeeRequestDto.setRequestDate(new Date());
////		calFeeRequestDto.setPayer("10000000126");
//////		calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); // 科目无需设置账户账号全称
////		//付款方设置
////		calFeeRequestDto.setPayerAcctType("10");
////		// 科目无需设置账户类型 验证会员
//////		calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//////		calFeeRequestDto.setPayerOrgCode("990310001");
////		calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.MEMBER.getValue()));
////		calFeeRequestDto.setAmount(1000l);
////		calFeeRequestDto.setOrderId("2010100900000000043");  
////		calFeeRequestDto.setOrderAmount(1000l);
////		calFeeRequestDto.setPayerFullMemberAcctCode("20010000000011000000011010");
////		//收款方设置
////		// 科目无需设置账户类型 验证会员
////		calFeeRequestDto.setPayeeOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
////		calFeeRequestDto.setPayeeOrgCode("990310001");
////		
////		//payee 收款
//////		calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
////		//payer 付款
////		
////		
////		
////		
////		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
////		
////		//贷
////		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
////		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
////		payCalFeeDetailDto.setCrdr(2l);
////		payCalFeeDetailDto.setCreatedate(new Date());
////		payCalFeeDetailDto.setDealId("2010100900000000043");
////		payCalFeeDetailDto.setMaBlanceBy(2);
////		payCalFeeDetailDto.setValue(100l);
////		payCalFeeDetailDto.setEntrycode(1);
////		
////		calFeeDetailDtos.add(payCalFeeDetailDto);
////		//借
////		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
////		revCalFeeDetailDto.setAcctcode("20010200100011000000012510");
////		revCalFeeDetailDto.setCrdr(1l);
////		revCalFeeDetailDto.setCreatedate(new Date());
////		revCalFeeDetailDto.setDealId("2010100900000000043");
////		revCalFeeDetailDto.setMaBlanceBy(1);
////		revCalFeeDetailDto.setValue(100l);
////		revCalFeeDetailDto.setEntrycode(2);
////		calFeeDetailDtos.add(revCalFeeDetailDto);
////		
////		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
////		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
////		
////		return calFeeReponseDto;
////	}
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
