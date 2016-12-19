package com;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgType;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;


@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class OnlyCalFeeServiceTest extends AbstractTestNGSpringContextTests{

	
	@Resource(name = "peService")
	private PEService peService;
	
	private  String getOrderCode(int pkgCode){
		int a = pkgCode ;
		int b = a/10 ;
		int c = b*10 ;
		return c+"" ;
	}
	
	
	/**
	 * 测试费用简介版本
	 * @param dealCode
	 * @param isMember
	 * @return
	 */
	public  PaymentReqDto generateCaculateRequest(Integer dealCode,boolean isMember ) {
		PaymentReqDto calFeeReqDto=new PaymentReqDto();
		
		calFeeReqDto.setAmount(10000l); //设置金额
		calFeeReqDto.setOrderCode(Integer.parseInt(getOrderCode(dealCode)));//订单号
		calFeeReqDto.setDealCode(dealCode);									//
		calFeeReqDto.setPayMethod(Integer.parseInt("1"));
		
		if(isMember){
			calFeeReqDto.setPayerOrgType(OrgType.MEMBER.getValue());//会员需要设置
			calFeeReqDto.setPayerAcctType(10);          			//科目无需设置账户类型
			calFeeReqDto.setPayer("10000000005");                   //科目无需设置payee
			calFeeReqDto.setRequestDate(new Date());
		}else{
			calFeeReqDto.setPayerOrgType(OrgType.INNER.getValue());
		}
		return calFeeReqDto;
	}

	@Test
	public void testCalculate() throws Exception{
		
		PaymentReqDto calFeeRequest = generateCaculateRequest(111,true);
		PaymentResponseDto calFeeRespone = peService.caculateFee(calFeeRequest);// PE计费
		
//		System.out.println("================  result ===================");
//		System.out.println("--- "+calFeeRespone.isHasCaculatedPrice());
		System.out.println("---payeeFee = "+calFeeRespone.getPayeeFee());
		System.out.println("---payerFee = "+calFeeRespone.getPayerFee());
		
	
	}

}