///**
// * 
// */
//package com.pay.acc.service.perf;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import junit.framework.TestCase;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.testng.annotations.Test;
//
//import com.pay.acc.service.account.AccountBalanceHandlerService;
//import com.pay.acc.service.account.dto.CalFeeDetailDto;
//import com.pay.acc.service.account.dto.CalFeeReponseDto;
//import com.pay.acc.service.account.dto.CalFeeRequestDto;
//import com.pay.acc.service.account.exception.MaAcctBalanceException;
//import com.pay.pe.helper.OrgType;
//
///**
// * @author Administrator
// * 
// */
//public class AccountBalancePerfTest extends TestCase {
//
//	// private AccountBalanceHandlerService accountBalanceHandlerService;
//	private static AccountBalanceHandlerService accountBalanceHandlerService;
//
//	static {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"classpath*:context/*.xml");
//		accountBalanceHandlerService = (AccountBalanceHandlerService) context
//				.getBean("acc-accountBalanceHandlerService");
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	// @BeforeClass
//	// public static void setUpBeforeClass() throws Exception {
//	//
//	// }
//	// @Before
//	// public void setUp() throws Exception {
//	// ApplicationContext context =new
//	// ClassPathXmlApplicationContext("classpath*:context/*.xml");
//	// this.accountBalanceHandlerService=(AccountBalanceHandlerService)context.getBean("acc-accountBalanceHandlerService");
//	// }
//	// @Test
//	// public void testAccountBalanceHandlerServiceNotNull(){
//	// Assert.assertNotNull(this.accountBalanceHandlerService);
//	// }
//	/**
//	 * 对更新余额进行并发测试 2000111288787780762
//	 * 
//	 * @throws MaAcctBalanceException
//	 */
//	@Test
//	public void testDoUpdateAcctBalanceRntxWithPerf1()
//			throws MaAcctBalanceException {
//		accountBalanceHandlerService.doUpdateAcctBalanceRntx(
//				this.initCalFeeReponseDto1(), 2);
//
//	}
//
//	// @Test
//	// public void testDoUpdateAcctBalanceRntxWithPerf2() throws
//	// MaAcctBalanceException{
//	// accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto2(),
//	// 2);
//	//
//	//
//	// }
//	// @Test
//	// public void testDoUpdateAcctBalanceRntxWithPerf3() throws
//	// MaAcctBalanceException{
//	// accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto3(),
//	// 2);
//	//
//	//
//	// }
//	// @Test
//	// public void testDoUpdateAcctBalanceRntxWithPerf4() throws
//	// MaAcctBalanceException{
//	// accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto4(),
//	// 2);
//	//
//	//
//	// }
//	// @Test
//	// public void testDoUpdateAcctBalanceRntxWithPerf5() throws
//	// MaAcctBalanceException{
//	// accountBalanceHandlerService.doUpdateAcctBalanceRntx(this.initCalFeeReponseDto5(),
//	// 2);
//	//
//	//
//	// }
//	private CalFeeReponseDto initCalFeeReponseDto1() {
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		String serialNo = "20001" + new Date().getTime();
//		// calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//		// calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
//		// calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); //
//		// 科目无需设置账户账号全称
//		// 付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		// 科目无需设置账户类型 验证会员
//		// calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//		// calFeeRequestDto.setPayerOrgCode("990310001");
//
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(100l);
//		calFeeRequestDto.setOrderId(serialNo);
//		calFeeRequestDto.setOrderAmount(100l);
//		calFeeRequestDto
//				.setPayerFullMemberAcctCode("20010000000011000000011010");
//		// 收款方设置
//		// 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//
//		// payee 收款
//		// calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		// payer 付款
//
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//
//		// 贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(serialNo);
//		payCalFeeDetailDto.setMaBlanceBy(1);
//		payCalFeeDetailDto.setValue(10l);
//		payCalFeeDetailDto.setEntrycode(1);
//
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		// 借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010200100011000000012510");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(serialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(10l);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//
//		return calFeeReponseDto;
//	}
//
//	private CalFeeReponseDto initCalFeeReponseDto2() {
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		String serialNo = "20001" + new Date().getTime();
//		// calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//		// calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
//		// calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); //
//		// 科目无需设置账户账号全称
//		// 付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		// 科目无需设置账户类型 验证会员
//		// calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//		// calFeeRequestDto.setPayerOrgCode("990310001");
//
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId(serialNo);
//		calFeeRequestDto.setOrderAmount(1000l);
//		calFeeRequestDto
//				.setPayerFullMemberAcctCode("20010000000011000000011010");
//		// 收款方设置
//		// 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//
//		// payee 收款
//		// calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		// payer 付款
//
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//
//		// 贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(serialNo);
//		payCalFeeDetailDto.setMaBlanceBy(2);
//		payCalFeeDetailDto.setValue(100l);
//		payCalFeeDetailDto.setEntrycode(1);
//
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		// 借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010100200051000000000923");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(serialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(100l);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//
//		return calFeeReponseDto;
//	}
//
//	private CalFeeReponseDto initCalFeeReponseDto3() {
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		String serialNo = "20001" + new Date().getTime();
//		// calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//		// calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
//		// calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); //
//		// 科目无需设置账户账号全称
//		// 付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		// 科目无需设置账户类型 验证会员
//		// calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//		// calFeeRequestDto.setPayerOrgCode("990310001");
//
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId(serialNo);
//		calFeeRequestDto.setOrderAmount(1000l);
//		calFeeRequestDto
//				.setPayerFullMemberAcctCode("20010000000011000000011010");
//		// 收款方设置
//		// 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//
//		// payee 收款
//		// calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		// payer 付款
//
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//
//		// 贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(serialNo);
//		payCalFeeDetailDto.setMaBlanceBy(2);
//		payCalFeeDetailDto.setValue(100l);
//		payCalFeeDetailDto.setEntrycode(1);
//
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		// 借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010100200041000000000922");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(serialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(100l);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//
//		return calFeeReponseDto;
//	}
//
//	private CalFeeReponseDto initCalFeeReponseDto4() {
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		String serialNo = "20001" + new Date().getTime();
//		// calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//		// calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
//		// calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); //
//		// 科目无需设置账户账号全称
//		// 付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		// 科目无需设置账户类型 验证会员
//		// calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//		// calFeeRequestDto.setPayerOrgCode("990310001");
//
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId(serialNo);
//		calFeeRequestDto.setOrderAmount(1000l);
//		calFeeRequestDto
//				.setPayerFullMemberAcctCode("20010000000011000000011010");
//		// 收款方设置
//		// 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//
//		// payee 收款
//		// calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		// payer 付款
//
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//
//		// 贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(serialNo);
//		payCalFeeDetailDto.setMaBlanceBy(2);
//		payCalFeeDetailDto.setValue(100l);
//		payCalFeeDetailDto.setEntrycode(1);
//
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		// 借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010100200031000000000921");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(serialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(100l);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//
//		return calFeeReponseDto;
//	}
//
//	private CalFeeReponseDto initCalFeeReponseDto5() {
//		CalFeeReponseDto calFeeReponseDto = new CalFeeReponseDto();
//		calFeeReponseDto.setPayeeFee(1l);
//		calFeeReponseDto.setPayerFee(2l);
//
//		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
//		String serialNo = "20001" + new Date().getTime();
//		// calFeeRequestDto.setOrderId("1001245640000655");
//		calFeeRequestDto.setOrderAmount(1000L);
//		calFeeRequestDto.setSubmitAcctCode("1000000010910");
//		// calFeeReqDto.setAmount(100L);
//		calFeeRequestDto.setPaymentServicePkgCode("501"); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setDealCode(501); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setOrderCode(500); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setPayMethod(1); // 枚举类由魏峰统一提供
//		calFeeRequestDto.setTerminalType(1);
//		calFeeRequestDto.setRequestDate(new Date());
//		calFeeRequestDto.setPayer("10000000126");
//		// calFeeRequestDto.setPayerMemberAcctCode("1000000012610"); //
//		// 科目无需设置账户账号全称
//		// 付款方设置
//		calFeeRequestDto.setPayerAcctType(10);
//		// 科目无需设置账户类型 验证会员
//		// calFeeRequestDto.setPayerOrgType(String.valueOf(ORGTYPE.BANK.getValue()));
//		// calFeeRequestDto.setPayerOrgCode("990310001");
//
//		calFeeRequestDto.setPayerOrgType(OrgType.MEMBER.getValue());
//		calFeeRequestDto.setAmount(1000l);
//		calFeeRequestDto.setOrderId(serialNo);
//		calFeeRequestDto.setOrderAmount(1000l);
//		calFeeRequestDto
//				.setPayerFullMemberAcctCode("20010000000011000000011010");
//		// 收款方设置
//		// 科目无需设置账户类型 验证会员
//		calFeeRequestDto.setPayeeOrgType(OrgType.BANK.getValue());
//		calFeeRequestDto.setPayeeOrgCode("990310001");
//
//		// payee 收款
//		// calFeeRequestDto.setPayeeFullMemberAcctCode("20010200100011000000012510");
//		// payer 付款
//
//		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
//
//		// 贷
//		CalFeeDetailDto payCalFeeDetailDto = new CalFeeDetailDto();
//		payCalFeeDetailDto.setAcctcode("20010000000011000000011010");
//		payCalFeeDetailDto.setCrdr(2);
//		payCalFeeDetailDto.setCreatedate(new Date());
//		payCalFeeDetailDto.setDealId(serialNo);
//		payCalFeeDetailDto.setMaBlanceBy(2);
//		payCalFeeDetailDto.setValue(100l);
//		payCalFeeDetailDto.setEntrycode(1);
//
//		calFeeDetailDtos.add(payCalFeeDetailDto);
//		// 借
//		CalFeeDetailDto revCalFeeDetailDto = new CalFeeDetailDto();
//		revCalFeeDetailDto.setAcctcode("20010100100011000000000910");
//		revCalFeeDetailDto.setCrdr(1);
//		revCalFeeDetailDto.setCreatedate(new Date());
//		revCalFeeDetailDto.setDealId(serialNo);
//		revCalFeeDetailDto.setMaBlanceBy(1);
//		revCalFeeDetailDto.setValue(100l);
//		revCalFeeDetailDto.setEntrycode(2);
//		calFeeDetailDtos.add(revCalFeeDetailDto);
//
//		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
//		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
//
//		return calFeeReponseDto;
//	}
//
//}
