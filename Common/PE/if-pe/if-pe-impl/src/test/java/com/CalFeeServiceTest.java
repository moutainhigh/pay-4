package com;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class CalFeeServiceTest extends AbstractTestNGSpringContextTests {
	// AbstractTransactionalTestNGSpringContextTests {

	// @Resource(name = "infDataSource")
	// public void setDataSource(DataSource dataSource) {
	// super.setDataSource(dataSource);
	// }

	@Resource(name = "peService")
	private PEService peService;

//	PEObject getPEObj(String memberCode, Integer orderId, Integer dealCode) {
//		PEObject peo = new PEObject(memberCode, orderId, 10, 1, "0008",
//				dealCode, 200, 1);
//		return peo;
//	}
//
//	PEObject getPEObj1(String memberCode, Integer orderId, Integer dealCode) {
//		PEObject peo = new PEObject(memberCode, orderId, 10, 1, "0007",
//				dealCode, 180, 1);
//		return peo;
//	}
//
//	PEObject getPEObj2(String memberCode, Integer orderId, Integer dealCode) {
//		PEObject peo = new PEObject(memberCode, orderId, 10, 1,
//				DealCode.WithdrawPayFrist.getValue(), dealCode,
//				OrderCode.Fo_Withdraw.getValue(), 1);
//		return peo;
//	}

	public CalFeeRequest generateCaculateRequest(String acctCode,
			PEObject peObj, String amount) {
		String acctountId = peObj.getMemberCode() + peObj.getAccountType();
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(peObj.getOrderId());
		calFeeRequest.setAmount(Long.parseLong(amount) * 10);
		calFeeRequest.setOrderCode(peObj.getOrderCode());
		calFeeRequest.setDealCode(peObj.getDealCode());
		calFeeRequest.setPaymentServicePkgCode(peObj.getDealCode());
		calFeeRequest.setOrderAmount(Long.parseLong(amount) * 10);
		calFeeRequest.setPayeeOrgCode(peObj.getPayerOrgCode());
		calFeeRequest.setPayeeOrgType(OrgType.BANK.getValue());
		calFeeRequest.setPayer(peObj.getMemberCode()); // 科目无需设置payee
		calFeeRequest.setPayerAcctType(peObj.getAccountType());
		calFeeRequest.setPayerOrgType(peObj.getPayerOrgType());
		calFeeRequest.setPayerMemberAcctCode(acctountId); // 科目无需设置账户账号全称
		calFeeRequest.setPayMethod(peObj.getPayMethod());
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setPayerFullMemberAcctCode(acctCode);
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	public PaymentReqDto generateCaculateRequest1(String acctCode,
			PEObject peObj, String amount) {
		// 先调算费
		PaymentReqDto calFeeRequest = new PaymentReqDto();
		calFeeRequest.setOrderId(peObj.getOrderId());
		calFeeRequest.setAmount(Long.parseLong(amount));
		calFeeRequest.setOrderCode(peObj.getOrderCode());
		calFeeRequest.setDealCode(peObj.getDealCode());
		// calFeeRequest.setPaymentServicePkgCode(peObj.getDealCode());
		calFeeRequest.setOrderAmount(Long.parseLong(amount));

		String acctountId = peObj.getMemberCode() + peObj.getAccountType();

		calFeeRequest.setPayeeOrgCode(peObj.getPayerOrgCode());
		calFeeRequest.setPayeeOrgType(OrgType.BANK.getValue());

		calFeeRequest.setPayeeMemberAcctCode("1151010010002");
		calFeeRequest.setPayeeFullMemberAcctCode("1151010010002");

		calFeeRequest.setPayer(peObj.getMemberCode()); // 科目无需设置payee
		calFeeRequest.setPayerAcctType(peObj.getAccountType());
		calFeeRequest.setPayerOrgType(OrgType.MEMBER.getValue());
		calFeeRequest.setPayerMemberAcctCode(acctountId); // 科目无需设置账户账号全称
		calFeeRequest.setPayMethod(peObj.getPayMethod());

		// calFeeRequest.setPayerFee(Long.parseLong("2000"));
		// calFeeRequest.setPriceStrategyCode(Long.parseLong("66107"));
		// calFeeRequest.setHasCaculatedPrice(true);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setPayerFullMemberAcctCode(acctCode);
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;

	}

	@Test
	public void testCalculate() throws Exception {

		// 手机充值 20000000109
//		PaymentReqDto calFeeRequest = generateCaculateRequest1(
//				"20010200100011000000010910",
//				getPEObj2("10000000109", "2001012171703016814", DealCode.WithdrawPayFrist.getValue()), "100000");

		// 实名认证
		// CalFeeRequest calFeeRequest =
		// generateCaculateRequest("20010200100011000000010910",
		// getPEObj1("10000000109", "2001012171703016807", "181"),"100");
		// 冻结金额
		// CalFeeRequest calFeeRequest =
		// generateCaculateRequest("20010200100011000000010910",
		// getPEObj("10000000109", "2001012171703016807", "201"),"100");
		// PaymentResponseDto calFeeRespone =
		// peService.processPayment(calFeeRequest);// PE计费接口
		//
		PaymentResponseDto calFeeRespone = peService.caculateFee(null);// PE计费
		//
		// calFeeRespone.getPayeeFee()
		// System.out.println(calFeeRespone.getCalFeeDetails().size());

		// List<CalFeeDetail> detail=calFeeRespone.getCalFeeDetails();
		// for(CalFeeDetail l:detail){
		// System.out.println(l.getDealType()+"   :  "+l.getValue()+"     voucheerCode:  "+l.getVouchercode());
		// }
		// System.out.println(calFeeRespone.getPriceStrategyCode());
		// 保存分录
		// boolean flag=peService.accountingCalFeeReponse(calFeeRespone);
		// System.out.println(flag);

	}

	// alFeeRequestDTO buildCalFeeRequestDTO(HandlerParam param) {
	// CalFeeRequestDTO dto = new CalFeeRequestDTO();
	// MassPaytobankOrderDTO order =
	// (MassPaytobankOrderDTO)param.getBaseOrderDto();
	// // 订单编号 140
	// dto.setOrderCode(140);
	// if(order.getIsPayerPayFee().intValue()==1){
	// //　交易类型 付款到银行 141
	// dto.setDealCode(141);
	// // 支付服务代码
	// dto.setPaymentServicePkgCode("141");
	// }else{
	// //　交易类型 付款到银行 142
	// dto.setDealCode(142);
	// // 支付服务代码
	// dto.setPaymentServicePkgCode("142");
	// }
	//
	// //标识为已算费
	// dto.setHasCaculatedPrice(true);
	// //订单号
	// dto.setOrderId(String.valueOf(order.getMassOrderSeq()));
	// //订单金额 拒绝时输入金额剩以(-1)
	// dto.setAmount(order.getRealOutAmount());
	// dto.setPayerFee(order.getTotalFee());
	// dto.setOrderAmount(order.getTotalAmount());
	//
	//
	// dto.setPayerServiceLevel(-1);
	// dto.setPayerCurrencyCode("CNY");
	// //取值为1,2,3，分别表示直接到账，加密到账，担保到账
	// dto.setPayMethod(1);
	// //支付方式，分为WEB(1), WAP(2), API(3), POS(4)
	// dto.setTerminalType(TERMINALTYPE.WEB.getValue());
	// //记账时间,按业务订单发起记账的时间,一般为业务订单的更新时间
	// dto.setRequestDate(order.getCreateDate());
	// /*
	// * 付款方是会员账户
	// */
	//
	// String payer =new
	// StringBuffer().append(order.getPayerMemberCode()).append(order.getPayerAcctType()).toString();
	// //付款方会员号
	// dto.setPayer(String.valueOf(order.getPayerMemberCode()));
	// dto.setSubmitAcctCode(payer);
	// //付款方账号类型,账户类型
	// dto.setPayerAcctType(String.valueOf(order.getPayerAcctType()));
	// //付款方记账的账号，构成为member_code+账户类型
	// dto.setPayerMemberAcctCode(payer);
	// //付款方记账的账号，构成为科目+member_code+账户类型，对应为t_acct表中的acct_code
	// dto.setPayerFullMemberAcctCode(order.getPayerAcctCode());
	// //付款方机构类型代码，MEMBER(1), BANK(3), KQ(4)
	// dto.setPayerOrgType(1);
	// dto.setPayerServiceLevel(super.buildCalFeeServiceLevel(dto,
	// order.getPayerMemberCode()));
	// /*
	// * 收款方是科目
	// */
	// //收款方的机构代码 科目号：2181010010003?
	// dto.setPayeeOrgCode("010010003");
	// //收款方机构类型代码，MEMBER(1), BANK(3),
	// KQ(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
	// dto.setPayeeOrgType("3");
	// dto.setPayeeServiceLevel(-1);
	// dto.setPayeeCurrencyCode("CNY");
	//
	// return dto;
	// }

	// @Test
	public void testCalculateFeeDetail() throws Exception {
		PaymentReqDto calFeeReqDto = new PaymentReqDto();
		// begin 1.FI的记账银行卡在线充值调用
		// calFeeReqDto.setOrderId("1001245640000654");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("301"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(301); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(300); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010000000011000000010910"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000010910");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010000000011000000010910");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// // calFeeReqDto.setPayeeCurrencyCode(1); //代表人民币
		// end 1.FI的记账银行卡在线充值调用
		// begin 2.消费产生，买家点击支付成功
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("310"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010000000011000000010910"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000010910");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010000000011000000010910");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 2.消费产生，买家点击支付成功
		// begin 2.1 消费产生，买家点击支付成功
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("311"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(311); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012710"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012710"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayerAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012710");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(String.valueOf(ORGTYPE.KQ.getValue()));
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 2.1 同时款项由买家账户转至中间账户
		// begin 3.交易完成，由买家点击交易完成，或者系统根据交易截止时间自动发起
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("312"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(312); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010000000011000000010910"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000010910");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010000000011000000010910");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 3.交易完成，由买家点击交易完成，或者系统根据交易截止时间自动发起
		// //begin 4.消费产生，买家由会员账户支付成功
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("313"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(313); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012710"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012710"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012710");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(String.valueOf(ORGTYPE.KQ.getValue()));
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 4.消费产生，买家由会员账户支付成功

		// begin 5.交易完成，由买家点击交易完成，或者系统根据交易截止时间自动发起
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("314"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(314); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(310); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012710"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012710");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012710");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee(""); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 5.交易完成，由买家点击交易完成，或者系统根据交易截止时间自动发起
		// begin 6.网关银行卡支付退手续费
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("321"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(321); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(320); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012610"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0102"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 6.网关银行卡支付退手续费
		// begin 7.由买家发起充退指令 网关退款第一步
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("321"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(321); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(320); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012610"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayerAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0102"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 7.由买家发起充退指令 网关退款第一步
		// begin 7.1 由买家发起充退指令 网关退款退银行卡第二步（退款成功确认）
		calFeeReqDto.setOrderId("1001245640000655");
		calFeeReqDto.setOrderAmount(100000L);
		calFeeReqDto.setSubmitAcctCode("1000000010910");
		calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("322"); //枚举类由魏峰统一提供
		calFeeReqDto.setDealCode(322); // 枚举类由魏峰统一提供
		calFeeReqDto.setOrderCode(320); // 枚举类由魏峰统一提供
		calFeeReqDto.setPayMethod(1); // 枚举类由魏峰统一提供
		calFeeReqDto.setTerminalType(TerminalType.WEB.getValue());
		calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012610"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayerAcctType(10); //科目无需设置账户类型
		calFeeReqDto.setPayerOrgType(OrgType.BANK.getValue());
		calFeeReqDto.setPayerOrgCode("0001"); // 魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		calFeeReqDto.setPayeeOrgCode("0001"); // 会员账户无需设置orgCode
		calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue()); // 会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		calFeeReqDto.setPayeeServiceLevel(-1); // 无需定义
		// end 7.由买家发起充退指令 网关退款退银行卡第二步（退款成功确认）
		// begin 8.买家发起充退指令退款不成功
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("323"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(323); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(320); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012610"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0102"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// end 8.买家发起充退指令退款不成功

		// //begin 9.网关账户支付退手续费
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("324"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(324); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(320); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// calFeeReqDto.setPayerMemberAcctCode("1000000012610"); //科目无需设置账户账号全称
		// calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.MEMBER.getValue());
		// // calFeeReqDto.setPayerOrgCode("0102"); //魏峰定义
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// // calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.MEMBER.getValue());
		// //会员账户无需orgType
		// calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// calFeeReqDto.setPayeeAcctType(10);
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 9.网关账户支付退手续费

		// //begin 10.自动生成待退款订单（银行卡）
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("331"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(331); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(330); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012610");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 10.自动生成待退款订单（银行卡）

		// //begin 11.退款申请成功
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("341"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(341); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(340); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012610");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 11.退款申请成功

		// //begin 12.退款处理成功(银行卡)
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("342"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(342); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(340); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012610");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 12.退款处理成功(银行卡)

		// //begin 13.网关对账完成以后自动核销系统网关应收账款
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("391"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(391); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(390); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012610");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 13.网关对账完成以后自动核销系统网关应收账款

		// //begin 14.收到银行入账后核销银行网关
		// calFeeReqDto.setOrderId("1001245640000655");
		// calFeeReqDto.setOrderAmount(100000L);
		// calFeeReqDto.setSubmitAcctCode("1000000010910");
		// calFeeReqDto.setAmount(100000L);
		// calFeeReqDto.setPaymentServicePkgCode("392"); //枚举类由魏峰统一提供
		// calFeeReqDto.setDealCode(392); //枚举类由魏峰统一提供
		// calFeeReqDto.setOrderCode(390); //枚举类由魏峰统一提供
		// calFeeReqDto.setPayMethod(1); //枚举类由魏峰统一提供
		// calFeeReqDto.setTerminalType(TERMINALTYPE.WEB.getValue());
		// calFeeReqDto.setRequestDate(new Date());
		// // calFeeReqDto.setPayer("20010200100011000000012610"); //科目无需设置payee
		// // calFeeReqDto.setPayerMemberAcctCode("1000000012610");
		// //科目无需设置账户账号全称
		// // calFeeReqDto.setPayeeAcctType(10); //科目无需设置账户类型
		// calFeeReqDto.setPayerOrgType(ORGTYPE.BANK.getValue());
		// calFeeReqDto.setPayerOrgCode("0001"); //魏峰定义
		// //
		// calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000012610");
		// //科目无需设置账户全称
		// calFeeReqDto.setPayerServiceLevel(-1);
		// // calFeeReqDto.setPayerCurrencyCode(1); //代表人民币
		// // calFeeReqDto.setPayee("20010200100011000000012710"); //收款方为科目无需设置
		// calFeeReqDto.setPayeeOrgCode("0001"); //会员账户无需设置orgCode
		// calFeeReqDto.setPayeeOrgType(ORGTYPE.BANK.getValue());
		// //会员账户无需orgType
		// // calFeeReqDto.setPayeeMemberAcctCode("1000000012710");
		// // calFeeReqDto.setPayeeAcctType(10);
		// //
		// calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000012710");
		// calFeeReqDto.setPayeeServiceLevel(-1); //无需定义
		// //end 14.收到银行入账后核销银行网关

		PaymentResponseDto calFeeRespone = peService
				.processPayment(calFeeReqDto);
		System.out.println(calFeeRespone);
	}

	// @Test
	// public void testCalculateFeeDetail() throws Exception {
	// Integer orderCode =100;
	// Integer dealCode=101;
	// Integer payMethod= 1;
	//
	//
	//
	// String prefix = "2001000000001" ;// 个人会员所属科目
	//
	// String payer = "1111111111" ;
	// String payerAcctCode = "111111111101";// 20100201
	// String payerFull = prefix+payerAcctCode ;
	// String payerOrgType = 1;
	//
	// String payee = "1111111112" ;
	// String payeeAcctCode = "111111111201";// 20100201
	// String payeeOrgType = 1;
	// String payeeFull = prefix+payeeAcctCode ;
	//
	//
	// CalFeeRequest calFeeRequest = new CalFeeRequest();
	// calFeeRequest.setOrderCode(orderCode);
	// calFeeRequest.setDealCode(dealCode);
	// calFeeRequest.setPayMethod(payMethod);
	// calFeeRequest.setAmount(100*1000L);
	// calFeeRequest.setRequestDate(new Date());
	//
	// calFeeRequest.setOrderId("orderid11111111111");
	// calFeeRequest.setOrderAmount(100*1000L);;
	//
	//
	// calFeeRequest.setPayer(payer);
	// calFeeRequest.setPayerMemberAcctCode(payerAcctCode);
	// calFeeRequest.setPayerFullMemberAcctCode(payerFull);
	// calFeeRequest.setPayerOrgType(payerOrgType);
	//
	//
	// // calFeeRequest.setPayee(payee);
	// // calFeeRequest.setPayeeMemberAcctCode(payeeAcctCode);
	// // calFeeRequest.setPayeeFullMemberAcctCode(payeeFull);
	// // calFeeRequest.setPayeeOrgType(payeeOrgType);
	//
	// //提现是对银行
	// calFeeRequest.setPayeeOrgType("3");
	// calFeeRequest.setPayeeOrgCode("000000001");
	//
	//
	// CalFeeReponse calFeeReponse =
	// peService.calculateFeeDetail(calFeeRequest);
	//
	//
	//
	// System.out.println("--- "+calFeeReponse.isHasCaculatedPrice());
	// System.out.println("--- "+calFeeReponse.getPayeeFee());
	// System.out.println("--- "+calFeeReponse.getPayerFee());
	//
	//
	// List<CalFeeDetail> details= calFeeReponse.getCalFeeDetails();
	// for(CalFeeDetail detail:details){
	// System.out.println(detail.getAcctcode()+"   "+detail.getValue()+"    "+detail.getCrdr());
	// }
	//
	//
	//
	//
	// //记账
	// peService.accountingCalFeeReponse(calFeeReponse);
	//
	//
	// }
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
	// @Test
	// public void testCalFeeService() throws Exception {
	// // Integer orderCode = 100;
	// Integer orderCode = 200;
	// Integer dealCode=101;
	// Integer payMethod= 1;
	// CalFeeRequest calFeeRequest = new CalFeeRequest();
	// calFeeRequest.setOrderCode(orderCode);
	// calFeeRequest.setDealCode(dealCode);
	// calFeeRequest.setPayMethod(payMethod);
	// calFeeRequest.setAmount(10L);
	// calFeeRequest.setRequestDate(new Date());
	// CalFeeReponse calFeeReponse = peService.caculateFee(calFeeRequest);
	// System.out.println("--- "+calFeeReponse.isHasCaculatedPrice());
	// System.out.println("--- "+calFeeReponse.getPayeeFee());
	// System.out.println("--- "+calFeeReponse.getPayerFee());
	//
	// }
	//
	//
	//
	//
	//
	// // @Test
	// // public void testCalFeeService2() throws Exception {
	// // Integer orderCode = 100;
	// // Integer dealCode=101;
	// // Integer payMethod= 1;
	// //
	// // CalFeeRequest calFeeRequest = new CalFeeRequest();
	// // calFeeRequest.setOrderCode(orderCode);
	// // calFeeRequest.setDealCode(dealCode);
	// // calFeeRequest.setPayMethod(payMethod);
	// // calFeeRequest.setAmount(10L);
	// // calFeeRequest.setRequestDate(new Date());
	// // CalFeeReponse calFeeReponse =
	// peService.calculateFeeDetail(calFeeRequest);
	// // System.out.println("--- "+calFeeReponse.isHasCaculatedPrice());
	// // System.out.println("--- "+calFeeReponse.getPayeeFee());
	// // System.out.println("--- "+calFeeReponse.getPayerFee());
	// //
	// // }

}