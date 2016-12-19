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
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//
//
//public class FrozenAmountServiceTest extends AbstractTransactionalTestNGSpringContextTests{
//	
//	@Resource(name = "acc-frozenAmountService")
//	FrozenAmountService frozenAmountService;
//	
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	
//	@Test
//	public void testFrozenAmountService(){
//		Assert.assertNotNull(this.frozenAmountService);
//
//	}
//	
//	@Test
//	public void testdoFrozenAmountRnTx(){
//		try {
//	        this.frozenAmountService.doFrozenAmountRnTx(this.initCalFeeReponseDto(), new Integer(33));
//        }
//        catch (MaAcctBalanceException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//        }
//	}
//	
//	@Test
//	public void testdoUnFrozenAmountRnTx(){
//		try {
//	        this.frozenAmountService.doUnFrozenAmountRnTx(initCalFeeReponseDto(),"2010100900000000040", "2010100900000000039", 501, 1000L, 33);
//        }
//        catch (MaAcctBalanceException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//        }
//	}
//	
//	private CalFeeReponseDto initCalFeeReponseDto(){
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//		
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		
////		calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("2181010010008");
////		calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("12028"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("2181010010008");
////		calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); // 科目无需设置账户账号全称
//		//付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		 // 科目无需设置账户类型 验证会员
////		calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
////		calFeeRequestDto.setPayerOrgCode("990310001");
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId("2010100900000000039");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setPayerFullMemberAcctCode("2181010010008");
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
//		payCalFeeDetailDto.setAcctcode("20010200100011000000502410");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId("2010100900000000036");
//		payCalFeeDetailDto.setMaBlanceBy(2);
//		payCalFeeDetailDto.setValue(1000L);
//		payCalFeeDetailDto.setEntrycode(1);
//		
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		//借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("2181010010008");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId("2010100900000000036");
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(1000L);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//		
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//		
//		return calFeeReponseDto;
//	}
//}
