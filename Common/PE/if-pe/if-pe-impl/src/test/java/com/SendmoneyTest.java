package com;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.dao.PaymentOrderUtil;
import com.pay.pe.service.PEService;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class SendmoneyTest extends
		AbstractTransactionalTestNGSpringContextTests {

	@Resource
	private PEService peService;

	@Resource
	private PaymentOrderUtil paymentOrderUtil;

	@Test
	public void testPEService() throws Exception {
	
//		String payerAcctCode = "1001148904001";//20100201
//		int payerOrgType = 1;
//		String payeeAcctCode = "1001152154801";//20100201
//		int payeeOrgType = 1;
//		int orderCode = OrderCode.SendMoney.getValue();//950
//		
//		PaymentOrderDto paymentOrderDto = new PaymentOrderDto();
//		paymentOrderDto.setOrderType(orderCode);//必填
//		paymentOrderDto.setPayMethod(PAYMETHOD.DIRECTACCOUNT.getValue());//必填
//		paymentOrderDto.setOrderAmount(1000L);//必填
//		
//		paymentOrderDto.setMemo("");
//		
//		
//		paymentOrderDto.setOrderId(paymentOrderUtil.generateOrderId(orderCode));
//		paymentOrderDto.setOrderTime(new Timestamp(System.currentTimeMillis()));
//		
//		
//		paymentOrderDto.setSubmitAcctCode(payerAcctCode);
//
//		paymentOrderDto.setPayerAcctCode(payerAcctCode);//会员帐户号
//		paymentOrderDto.setPayerOrgType(payerOrgType);
//		
//		paymentOrderDto.setPayeeAcctCode(payeeAcctCode);//会员帐户号		
//		paymentOrderDto.setPayeeOrgType(payeeOrgType);
//		
//
//		
//		paymentOrderDto.setProductName("付款");
//		paymentOrderDto.setProductNum(1);
//		
//		paymentOrderDto.setVersion("1");
//		
//		// paymentOrderDto.setPayeeIdentity(idcontent);
//		
//		
//		DealDto dealDto = new DealDto();
//		dealDto.setOrder(paymentOrderDto);//必填
//		
//		//如果已经计费,可以设置  也可以不要设置,重新算
//		dealDto.setHasCaculatedPrice(true);
////		dealDto.setPayerFee(payerFee);
//		dealDto.setPayeeFee(5L);
//		
//		dealDto.setOrderTime(paymentOrderDto.getOrderTime());
//		dealDto.setDealAmount(paymentOrderDto.getOrderAmount());//必填
//		dealDto.setDealBeginDate(new Timestamp(System.currentTimeMillis()));
//		dealDto.setDealStatus(DealStatus.created.getValue());
//		dealDto.setDealType(DealType.AcctPay.getValue());
//		
//		int dealcode = 706;
//		dealDto.setDealCode(dealcode);//必填
//		
////		dealDto.setPaymentServicePkgCode("272");// 可以由 dealcode ,order.orderCode ,order.payMethod  得到
//		
//		
//		dealDto.setPayeeFullMemberAccCode("20100201" + payeeAcctCode);//必填
//		dealDto.setPayeeAcctType(paymentOrderDto.getPayeeAcctType());//必填
//		dealDto.setPayeeAcctCode(paymentOrderDto.getPayeeAcctCode());	//必填	
//		dealDto.setPayeeOrgCode(paymentOrderDto.getPayeeOrgCode());//必填
//		dealDto.setPayeeOrgType(paymentOrderDto.getPayeeOrgType());//必填
//				
//		dealDto.setPayerFullMemberAccCode("20100201" + payerAcctCode);////必填   acct表中的acct_code
//		dealDto.setPayerAcctType(paymentOrderDto.getPayerAcctType());//必填
//		dealDto.setPayerAcctCode(paymentOrderDto.getPayerAcctCode());//会员帐户号
//        dealDto.setPayerOrgCode(paymentOrderDto.getPayerOrgCode());//必填
//        dealDto.setPayerOrgType(paymentOrderDto.getPayerOrgType());//必填
//	       
//		dealDto.getOrder().setOrderStatus(COMMONORDERSTATUS.DealSuccess.getValue());
//
////		this.peService.processOrder(dealDto);
	}

}