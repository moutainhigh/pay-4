package com;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.PayMethod;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class PeServiceTest extends AbstractTestNGSpringContextTests {

	@Resource
	private PEService peService;

	@Test
	public void testprocessPayment_111() {

		PaymentReqDto paymentReqDto = new PaymentReqDto();

		paymentReqDto.setAmount(100000L);
		paymentReqDto.setOrderCode(110);
		paymentReqDto.setDealCode(111);
		paymentReqDto.setOrderAmount(100000L);
		paymentReqDto.setOrderId("105" + System.currentTimeMillis());

		paymentReqDto.setPayerOrgType(OrgType.MEMBER.getValue() );
		// paymentReqDto.setPayerOrgCode("010310002");
		paymentReqDto.setPayer("10000000337");
		paymentReqDto.setPayerMemberAcctCode("1000000033710");
		paymentReqDto.setPayerFullMemberAcctCode("20010200100011000000033710");

		// paymentReqDto.setPayee(payee);
		paymentReqDto.setPayeeOrgType(OrgType.MEMBER.getValue() );
		paymentReqDto.setPayee("10000000036");
		paymentReqDto.setPayeeMemberAcctCode("1000000003610");
		paymentReqDto.setPayeeFullMemberAcctCode("20010100100011000000003610");

		paymentReqDto.setPayMethod(PayMethod.DIRECTACCOUNT.getValue());
		paymentReqDto.setRequestDate(new Date());

		PaymentResponseDto paymentResponseDto = peService
				.processPayment(paymentReqDto);

		List<PaymentDetailDto> paymentDetails = paymentResponseDto
				.getPaymentDetails();

		System.out.println("记账规则生成" + paymentDetails.size() + "条分录：");
		for (PaymentDetailDto detail : paymentDetails) {

			System.out.println("acctCode:" + detail.getAcctcode() + ",value:"
					+ detail.getValue() + ",crdr:" + detail.getCrdr());
		}
	}
	
	//@Test
	public void testprocessPayment_116() {

		PaymentReqDto paymentReqDto = new PaymentReqDto();

		paymentReqDto.setAmount(100000L);
		paymentReqDto.setOrderCode(110);
		paymentReqDto.setDealCode(111);
		paymentReqDto.setOrderAmount(100000L);
		paymentReqDto.setOrderId("105" + System.currentTimeMillis());

		paymentReqDto.setPayerOrgType(OrgType.MEMBER.getValue() );
		// paymentReqDto.setPayerOrgCode("010310002");
		paymentReqDto.setPayer("10000000337");
		paymentReqDto.setPayerMemberAcctCode("1000000033710");
		paymentReqDto.setPayerFullMemberAcctCode("20010200100011000000033710");

		// paymentReqDto.setPayee(payee);
		paymentReqDto.setPayeeOrgType(OrgType.MEMBER.getValue() );
		paymentReqDto.setPayee("20000000171");
		paymentReqDto.setPayeeMemberAcctCode("2000000017110");
		paymentReqDto.setPayeeFullMemberAcctCode("20010100100012000000017110");

		paymentReqDto.setPayMethod(PayMethod.DIRECTACCOUNT.getValue());
		paymentReqDto.setRequestDate(new Date());

		PaymentResponseDto paymentResponseDto = peService
				.processPayment(paymentReqDto);

		List<PaymentDetailDto> paymentDetails = paymentResponseDto
				.getPaymentDetails();

		System.out.println("记账规则生成" + paymentDetails.size() + "条分录：");
		for (PaymentDetailDto detail : paymentDetails) {

			System.out.println("acctCode:" + detail.getAcctcode() + ",value:"
					+ detail.getValue() + ",crdr:" + detail.getCrdr());
		}
	}
}