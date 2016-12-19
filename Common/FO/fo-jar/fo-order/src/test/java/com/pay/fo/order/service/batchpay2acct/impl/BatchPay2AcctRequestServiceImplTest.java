package com.pay.fo.order.service.batchpay2acct.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.inf.exception.AppException;
@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath*:context/**/*.xml"
//		"classpath:context/fo/context-fundout-base-dao.xml",
//		"classpath:context/fo/context-fo-batchpaymentorder-dao.xml",
//		"classpath:context/fo/context-fo-batchpaymentorder-service.xml",
//		"classpath:context/fo/context-fo-batchpay2bank-service.xml"
		}
)
public class BatchPay2AcctRequestServiceImplTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name="fo-order-batchPay2AcctRequestService")
	BatchPay2AcctRequestService batchPay2AcctRequestService;
	
	@Resource(name="fo-order-batchPaymentReqBaseInfoService")
	BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	
	private BatchPaymentReqBaseInfoDTO reqInfo;
	
	private String businessBatchNo;
	
  @Test
  public void parseRequestInfo() throws IOException {
    byte[] file = FileUtils.readFileToByteArray(new File("D:\\pay\\tmp\\MasspayTemplate_error.xls"));
    
    reqInfo = batchPay2AcctRequestService.parseRequestInfo(file, 3000);
    System.out.println("businessBatchNo:"+reqInfo.getBusinessBatchNo());
    System.out.println("requestCount:"+reqInfo.getRequestCount());
    System.out.println("requestAmount:"+reqInfo.getRequestAmount());
    System.out.println("validCount:"+reqInfo.getValidCount());
    System.out.println("validAmount:"+reqInfo.getValidAmount());
    
    businessBatchNo = reqInfo.getBusinessBatchNo();
    
  }
  
  
  @Test(dependsOnMethods={"parseRequestInfo"})
  public void validateRequestInfo() {
	  
	  reqInfo.setPayerName("企业测");
	  reqInfo.setPayerMemberCode(10000000001L);
	  reqInfo.setPayerMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  reqInfo.setPayerAcctCode("20010100100011000000000110");
	  reqInfo.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  reqInfo.setPayerLoginName("pay@pay.com");
	  reqInfo.setCreator("admin");
	  reqInfo.setCreateDate(new Date());
	  reqInfo.setIsPayerPayFee(1);
	  
	  
	  reqInfo.setRequestType(OrderType.BATCHPAY2ACCT.getValue());
	  reqInfo.setRequestSrc("TESTNG");
	  
	  batchPay2AcctRequestService.validateRequestInfo(reqInfo);

	  System.out.println("businessBatchNo:"+reqInfo.getBusinessBatchNo());
      System.out.println("requestCount:"+reqInfo.getRequestCount());
      System.out.println("requestAmount:"+reqInfo.getRequestAmount());
      System.out.println("validCount:"+reqInfo.getValidCount());
      System.out.println("validAmount:"+reqInfo.getValidAmount());
	  
  }
  


  @Test(dependsOnMethods={"validateRequestInfo"}) 
  public void saveRequestInfoRnTx() throws AppException {
	  
	  reqInfo.setStatus(0);
	  batchPay2AcctRequestService.saveRequestInfoRnTx(reqInfo);
  }
  
  
  @Test(dependsOnMethods={"saveRequestInfoRnTx"}) 
  public void confirmRequestInfoRdTx() throws AppException {
	  
	  BatchPaymentReqBaseInfoDTO dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(reqInfo.getRequestSeq());
	  
	  reqInfo.setStatus(1);
	  System.out.println("old businessBatchNo:"+dto.getBusinessBatchNo());
	  reqInfo.setBusinessBatchNo(businessBatchNo);
	  batchPay2AcctRequestService.confirmRequestInfoRdTx(reqInfo);
	  
	  dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(reqInfo.getRequestSeq());
	  System.out.println("new businessBatchNo:"+dto.getBusinessBatchNo());
  }
  
  @Test(dependsOnMethods={"confirmRequestInfoRdTx"})
  public void auditPassRequestRdTx() throws AppException {
	  batchPay2AcctRequestService.auditPassRequestRdTx(reqInfo.getRequestSeq(), "admin", "测试通过");
  }

//  @Test
//  public void auditRejectRequestRdTx() {
//    throw new RuntimeException("Test not implemented");
//  }






}
