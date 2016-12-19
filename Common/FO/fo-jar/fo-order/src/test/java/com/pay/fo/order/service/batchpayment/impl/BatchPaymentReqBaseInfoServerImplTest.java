package com.pay.fo.order.service.batchpayment.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-batchpaymentreqbaseinfo-dao.xml",
		"classpath:context/fo/context-fo-batchpaymentreqbaseinfo-service.xml"})
public class BatchPaymentReqBaseInfoServerImplTest  extends AbstractTestNGSpringContextTests{

	
	@Resource(name = "fo-order-batchPaymentReqBaseInfoService")
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	      

  private Long key;
  
  private String businessBatchNo="TEST_PAY2ACCT_001";
	
  @Test
  public void create() {
	  BatchPaymentReqBaseInfoDTO dto = new BatchPaymentReqBaseInfoDTO();
	  dto.setRequestType(OrderType.BATCHPAY2ACCT.getValue());
	  dto.setBusinessBatchNo(businessBatchNo);
	  
	  dto.setPayerName("企业测");
	  dto.setPayerMemberCode(10000000001L);
	  dto.setPayerMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  dto.setPayerAcctCode("20010100100011000000000110");
	  dto.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  dto.setPayerLoginName("pay@pay.com");
	  dto.setCreator("admin");
	  dto.setCreateDate(new Date());
	  
	  dto.setStatus(0);
	  
	  dto.setRequestAmount(1L);
	  dto.setRequestCount(1);
	  dto.setValidAmount(1L);
	  dto.setValidCount(1);
	  dto.setIsPayerPayFee(1);
	  dto.setFee(0L);
	  dto.setRealpayAmount(1L);
	  dto.setRealoutAmount(1L);

	  dto.setRequestSrc("TESTNG");
	  
	  key = batchPaymentReqBaseInfoService.create(dto);
	  
	  System.out.println("Success:"+key);
  }


  @Test
  public void getBatchPaymentReqBaseInfoLongIntegerString() {
	  
	  BatchPaymentReqBaseInfoDTO dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(10000000001L,OrderType.BATCHPAY2ACCT.getValue(),businessBatchNo);
	  if(dto==null){
		  System.out.println("Not found BatchPaymentReqBaseInfoDTO instance[businessBatchNo:"+businessBatchNo+"].");
		  return;
	  }
	  
	  System.out.println("-----------------------getBatchPaymentReqBaseInfoLongIntegerString------------------------");
	  System.out.println("requestId:"+dto.getRequestSeq());
	  System.out.println("requestType:"+dto.getRequestType());
	  System.out.println("status:"+dto.getStatus());
	  System.out.println("payerName:"+dto.getPayerName());
	  System.out.println("businessBatchNo:"+dto.getBusinessBatchNo());
  }

  @Test
  public void update() {
	  BatchPaymentReqBaseInfoDTO dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(key);
	  if(dto==null){
		  System.out.println("Not found BatchPaymentReqBaseInfoDTO instance[requestSeq:"+key+"].");
		  return;
	  }
	  
	  System.out.println("-----------------------update old------------------------");
	  System.out.println("requestId:"+dto.getRequestSeq());
	  System.out.println("requestType:"+dto.getRequestType());
	  System.out.println("status:"+dto.getStatus());
	  System.out.println("payerName:"+dto.getPayerName());
	  System.out.println("businessBatchNo:"+dto.getBusinessBatchNo());
	  
	  BatchPaymentReqBaseInfoDTO upDto = new BatchPaymentReqBaseInfoDTO();
	  upDto.setRequestSeq(key);
	  upDto.setBusinessBatchNo(String.valueOf(key));
	  upDto.setUpdateDate(new Date());
	  
	  if(!batchPaymentReqBaseInfoService.update(upDto)){
		  System.out.println("update failed.");
		  return;
	  }
	  dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(key);
	  System.out.println("-----------------------update new------------------------");
	  System.out.println("requestId:"+dto.getRequestSeq());
	  System.out.println("requestType:"+dto.getRequestType());
	  System.out.println("status:"+dto.getStatus());
	  System.out.println("payerName:"+dto.getPayerName());
	  System.out.println("businessBatchNo:"+dto.getBusinessBatchNo());
	  
  }

  @Test
  public void updateStatus() {
	  BatchPaymentReqBaseInfoDTO dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(key);
	  if(dto==null){
		  System.out.println("Not found BatchPaymentReqBaseInfoDTO instance[requestSeq:"+key+"].");
		  return;
	  }
	  
	  System.out.println("-----------------------update old------------------------");
	  System.out.println("requestId:"+dto.getRequestSeq());
	  System.out.println("requestType:"+dto.getRequestType());
	  System.out.println("status:"+dto.getStatus());
	  System.out.println("payerName:"+dto.getPayerName());
	  System.out.println("businessBatchNo:"+dto.getBusinessBatchNo());
	  
	  BatchPaymentReqBaseInfoDTO upDto = new BatchPaymentReqBaseInfoDTO();
	  upDto.setRequestSeq(key);
	  upDto.setStatus(1);
	  upDto.setUpdateDate(new Date());
	  
	  if(!batchPaymentReqBaseInfoService.updateStatus(upDto,0)){
		  System.out.println("updateStatus failed.");
		  return;
	  }
	  dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(key);
	  System.out.println("-----------------------update new------------------------");
	  System.out.println("requestId:"+dto.getRequestSeq());
	  System.out.println("requestType:"+dto.getRequestType());
	  System.out.println("status:"+dto.getStatus());
	  System.out.println("payerName:"+dto.getPayerName());
	  System.out.println("businessBatchNo:"+dto.getBusinessBatchNo());
  }
}
