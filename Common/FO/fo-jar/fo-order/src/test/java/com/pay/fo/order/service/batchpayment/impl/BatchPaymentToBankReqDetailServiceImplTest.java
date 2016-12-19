package com.pay.fo.order.service.batchpayment.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;
import com.pay.inf.exception.AppException;

@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-batchpaymenttobankreqdetail-dao.xml",
		"classpath:context/fo/context-fo-batchpaymenttobankreqdetail-service.xml"})
public class BatchPaymentToBankReqDetailServiceImplTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name = "fo-order-batchPaymentToBankReqDetailService")
	private BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService;
	
	private Long requestSeq=2001106161706000010L;

  @Test
  public void createBatchPaymentToBankReqDetailDTO() {
	  
	  BatchPaymentToBankReqDetailDTO dto = new BatchPaymentToBankReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setPayeeBankProvinceName("上海市");
	  dto.setPayeeBankProvince("2900");
	  dto.setPayeeBankCityName("上海市");
	  dto.setPayeeBankCity("2900");
	  dto.setPayeeBankName("招商银行");
	  dto.setPayeeBankCode("10006001");
	  dto.setPayeeOpeningBankName("招商银行上海市源深路支行");
	  dto.setPayeeName("支付结算有限公司");
	  dto.setPayeeBankAcctCode("888888888888888888");
	  dto.setRequestAmount("1.23");
	  dto.setPaymentAmount(1230L);
	  dto.setForeignOrderId("DEMO0001");
	  dto.setRemark("DEMO");
	  
	  dto.setValidStatus(1);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  
	  dto.setIsPayerPayFee(1);
	  dto.setFee(0L);
	  dto.setRealpayAmount(1230L);
	  dto.setRealoutAmount(1230L);
	  
	  batchPaymentToBankReqDetailService.create(dto);
	  
    
  }

  @Test
  public void createListBatchPaymentToBankReqDetailDTO() throws AppException {
    
	  List<RequestDetail> details = new ArrayList<RequestDetail>();
	  BatchPaymentToBankReqDetailDTO dto = new BatchPaymentToBankReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setPayeeBankProvinceName("上海市");
	  dto.setPayeeBankProvince("2900");
	  dto.setPayeeBankCityName("上海市");
	  dto.setPayeeBankCity("2900");
	  dto.setPayeeBankName("招商银行");
	  dto.setPayeeBankCode("10006001");
	  dto.setPayeeOpeningBankName("招商银行上海市源深路支行");
	  dto.setPayeeName("支付结算有限公司");
	  dto.setPayeeBankAcctCode("888888888888888888");
	  dto.setRequestAmount("1.23");
	  dto.setPaymentAmount(1230L);
	  dto.setForeignOrderId("DEMO0002");
	  dto.setRemark("DEMO");
	  
	  dto.setValidStatus(1);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  
	  dto.setIsPayerPayFee(1);
	  dto.setFee(0L);
	  dto.setRealpayAmount(1230L);
	  dto.setRealoutAmount(1230L);	  
	  
	  details.add(dto);
	  
	  dto = new BatchPaymentToBankReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setPayeeBankProvinceName("上海市");
	  dto.setPayeeBankProvince("2900");
	  dto.setPayeeBankCityName("上海市");
	  dto.setPayeeBankCity("2900");
	  dto.setPayeeBankName("招商银行");
	  dto.setPayeeBankCode("10006001");
	  dto.setPayeeOpeningBankName("招商银行上海市源深路支行");
	  dto.setPayeeName("支付结算有限公司");
	  dto.setPayeeBankAcctCode("888888888888888888");
	  dto.setRequestAmount("1.23");
	  dto.setPaymentAmount(1230L);
	  dto.setForeignOrderId("DEMO0003");
	  dto.setRemark("DEMO");
	  
	  dto.setValidStatus(1);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  
	  dto.setIsPayerPayFee(1);
	  dto.setFee(0L);
	  dto.setRealpayAmount(1230L);
	  dto.setRealoutAmount(1230L);	  
	  
	  details.add(dto);
	  
	  //TODO batchPaymentToBankReqDetailService.create(details,reqInfo);
	  
  }
  
  private Long key;

  @Test
  public void getCreateOrderDetailList() {
	  List<BatchPaymentToBankReqDetailDTO> details = batchPaymentToBankReqDetailService.getCreateOrderDetailList(requestSeq, 0);
	  for (BatchPaymentToBankReqDetailDTO detail : details) {
		key = detail.getDetailSeq();
		System.out.println("-----------------------"+detail.getDetailSeq()+"----------------------------");
		System.out.println("明细流水号:"+detail.getDetailSeq());
		System.out.println("创建日期:"+detail.getCreateDate());
	}
	  
  }
  @Test
  public void getDetail() {
	  BatchPaymentToBankReqDetailDTO detail = batchPaymentToBankReqDetailService.getDetail(key);
	  System.out.println("-----------------------getDetail:"+detail.getDetailSeq()+"----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
  }

  @Test
  public void getValidateDetailList() {
	  List<BatchPaymentToBankReqDetailDTO> details = batchPaymentToBankReqDetailService.getValidateDetailList(requestSeq, 1);
	  for (BatchPaymentToBankReqDetailDTO detail : details) {
		System.out.println("-----------------------"+detail.getDetailSeq()+"----------------------------");
		System.out.println("明细流水号:"+detail.getDetailSeq());
		System.out.println("创建日期:"+detail.getCreateDate());
	}
  }

  @Test
  public void updateStatus() {
	  BatchPaymentToBankReqDetailDTO detail = batchPaymentToBankReqDetailService.getDetail(key);
	  System.out.println("-----------------------updateStatus old----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
	  System.out.println("是否已生成订单:"+detail.getOrderStatus());
	  
	  BatchPaymentToBankReqDetailDTO upDetail = new BatchPaymentToBankReqDetailDTO();
	  upDetail.setDetailSeq(key);
	  upDetail.setOrderStatus(1);
	  upDetail.setUpdateDate(new Date());
	  
	  if(!batchPaymentToBankReqDetailService.updateStatus(upDetail, 0)){
		  System.out.println("updateStatus failed");
	  }
	  
	  detail = batchPaymentToBankReqDetailService.getDetail(key);
	  System.out.println("-----------------------updateStatus new----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
	  System.out.println("是否已生成订单:"+detail.getOrderStatus());
	  
  }
}
