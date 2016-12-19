package com.pay.fo.order.service.batchpay2bank.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.inf.exception.AppException;

@ContextConfiguration(locations = { "classpath*:config/**/*.xml",
		"classpath*:context/**/*.xml" })
public class BatchPay2BankRequestServiceImplTest extends
		AbstractTestNGSpringContextTests {

	@Resource(name = "fo-order-batchPay2BankRequestService")
	BatchPay2BankRequestService batchPay2BankRequestService;

	@Resource(name = "fo-order-batchPaymentReqBaseInfoService")
	BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;

	private BatchPaymentReqBaseInfoDTO reqInfo;

	private String businessBatchNo;

	@Test(invocationCount=1)
	public void parseRequestInfo() throws IOException {
		byte[] file = FileUtils.readFileToByteArray(new File(
				"D:\\xls\\BatchPay2BankTemplate0.xls"));

		long beginTime = System.currentTimeMillis();
		System.out.println(beginTime);
		BatchPaymentReqBaseInfoDTO reqInfo = new BatchPaymentReqBaseInfoDTO();
		
		//装配付款方基本信息
		reqInfo.setPayerName("马超湖");
		reqInfo.setPayerMemberType(2);
		reqInfo.setPayerAcctType(10);
		reqInfo.setPayerLoginName("pay@pay.com");
		reqInfo.setCreateDate(new Date());
		reqInfo.setIsPayerPayFee(1);
		
		reqInfo.setRequestType(OrderType.BATCHPAY2BANK.getValue());
		reqInfo.setRequestSrc("内部批付到银行");
		//batchPay2BankRequestService.validateRequestInfo(reqInfo);
		//设置初始状态
		reqInfo.setStatus(0);
		
		
		
		
		reqInfo.setBusinessBatchNo("2010010115");
		reqInfo.setPayerMemberCode(10000000001L);
		batchPay2BankRequestService.parseRequestInfo(file,reqInfo);

		long endTime = System.currentTimeMillis();

		System.out.println("process time:" + (endTime - beginTime));
		
		System.out.println("businessBatchNo:" + reqInfo.getBusinessBatchNo());
		System.out.println("requestCount:" + reqInfo.getRequestCount());
		System.out.println("requestAmount:" + reqInfo.getRequestAmount());
		System.out.println("validCount:" + reqInfo.getValidCount());
		System.out.println("validAmount:" + reqInfo.getValidAmount());
		System.out.println("errorMsg:"+reqInfo.getErrorMsg());
		
		List<RequestDetail> details = reqInfo.getRequestDetails();
		for(int i=0;i<details.size();i++){
			BatchPaymentToBankReqDetailDTO detail = (BatchPaymentToBankReqDetailDTO)details.get(i);
			System.out.println(detail.getErrorMsg());
		}
		businessBatchNo = reqInfo.getBusinessBatchNo();

	}

	// @Test(dependsOnMethods = { "parseRequestInfo" })
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

		reqInfo.setRequestType(OrderType.BATCHPAY2BANK.getValue());
		reqInfo.setRequestSrc("TESTNG");

		batchPay2BankRequestService.validateRequestInfo(reqInfo);

		System.out.println("businessBatchNo:" + reqInfo.getBusinessBatchNo());
		System.out.println("requestCount:" + reqInfo.getRequestCount());
		System.out.println("requestAmount:" + reqInfo.getRequestAmount());
		System.out.println("validCount:" + reqInfo.getValidCount());
		System.out.println("validAmount:" + reqInfo.getValidAmount());

	}

	// @Test(dependsOnMethods = { "validateRequestInfo" })
	public void saveRequestInfoRnTx() throws AppException {

		reqInfo.setStatus(0);
		reqInfo.setBusinessBatchNo(businessBatchNo + "_");
		batchPay2BankRequestService.saveRequestInfoRnTx(reqInfo);
	}

	// @Test(dependsOnMethods = { "saveRequestInfoRnTx" })
	public void confirmRequestInfoRdTx() throws AppException {
		BatchPaymentReqBaseInfoDTO dto = batchPaymentReqBaseInfoService
				.getBatchPaymentReqBaseInfo(reqInfo.getRequestSeq());

		reqInfo.setStatus(1);
		System.out.println("old businessBatchNo:" + dto.getBusinessBatchNo());
		reqInfo.setBusinessBatchNo(businessBatchNo);
		batchPay2BankRequestService.confirmRequestInfoRdTx(reqInfo);

		dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(reqInfo
				.getRequestSeq());
		System.out.println("new businessBatchNo:" + dto.getBusinessBatchNo());
	}

	// @Test(dependsOnMethods = { "confirmRequestInfoRdTx" })
	public void auditPassRequestRdTx() throws AppException {
		batchPay2BankRequestService.auditPassRequestRdTx(reqInfo
				.getRequestSeq(), "admin", "测试通过");
	}

	// @Test
	// public void auditRejectRequestRdTx() {
	// throw new RuntimeException("Test not implemented");
	// }

}
