package com.pay.fo.order.service.batchpayment.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;
import com.pay.inf.exception.AppException;

@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-batchpaymenttoacctreqdetail-dao.xml",
		"classpath:context/fo/context-fo-batchpaymenttoacctreqdetail-service.xml"})
public class BatchPaymentToAcctReqDetailServiceImplTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name = "fo-order-batchPaymentToAcctReqDetailService")
	private BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService;
	
	private Long requestSeq=2001106161706000010L;

  @Test
  public void createBatchPaymentToAcctReqDetailDTO() {
	  
	  BatchPaymentToAcctReqDetailDTO dto = new BatchPaymentToAcctReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setForeignOrderId("DEMO0001");
	  dto.setPayeeLoginName("13501972866");
	  dto.setPayeeName("彭炯");
	  dto.setRequestAmount("100");
	  dto.setRemark("测试");
	  
	  dto.setValidateStatus(1);
	  dto.setPaymentAmount(100000L);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  
	  batchPaymentToAcctReqDetailService.create(dto);
	  
    
  }

  @Test
  public void createListBatchPaymentToAcctReqDetailDTO() throws AppException {
    
	  List<BatchPaymentToAcctReqDetailDTO> details = new ArrayList<BatchPaymentToAcctReqDetailDTO>();
	  BatchPaymentToAcctReqDetailDTO dto = new BatchPaymentToAcctReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setForeignOrderId("DEMO0002");
	  dto.setPayeeLoginName("13501972866");
	  dto.setPayeeName("彭炯");
	  dto.setRequestAmount("100");
	  dto.setRemark("测试");
	  
	  dto.setValidateStatus(1);
	  dto.setPaymentAmount(100000L);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  details.add(dto);
	  dto = new BatchPaymentToAcctReqDetailDTO();
	  dto.setRequestSeq(requestSeq);
	  
	  dto.setForeignOrderId("DEMO0003");
	  dto.setPayeeLoginName("13501972866");
	  dto.setPayeeName("彭炯");
	  dto.setRequestAmount("100");
	  dto.setRemark("测试");
	  
	  
	  dto.setValidateStatus(1);
	  dto.setPaymentAmount(100000L);
	  dto.setOrderStatus(0);
	  dto.setCreateDate(new Date());
	  
	  details.add(dto);
	  
	  //TODO batchPaymentToAcctReqDetailService.create(details);
	  
  }
  
  private Long key;

  @Test
  public void getCreateOrderDetailList() {
	  List<BatchPaymentToAcctReqDetailDTO> details = batchPaymentToAcctReqDetailService.getCreateOrderDetailList(requestSeq, 0);
	  for (BatchPaymentToAcctReqDetailDTO detail : details) {
		key = detail.getDetailSeq();
		System.out.println("-----------------------"+detail.getDetailSeq()+"----------------------------");
		System.out.println("明细流水号:"+detail.getDetailSeq());
		System.out.println("创建日期:"+detail.getCreateDate());
	}
	  
  }
  @Test
  public void getDetail() {
	  BatchPaymentToAcctReqDetailDTO detail = batchPaymentToAcctReqDetailService.getDetail(key);
	  System.out.println("-----------------------getDetail:"+detail.getDetailSeq()+"----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
  }

  @Test
  public void getValidateDetailList() {
	  List<BatchPaymentToAcctReqDetailDTO> details = batchPaymentToAcctReqDetailService.getValidateDetailList(requestSeq, 1);
	  for (BatchPaymentToAcctReqDetailDTO detail : details) {
		System.out.println("-----------------------"+detail.getDetailSeq()+"----------------------------");
		System.out.println("明细流水号:"+detail.getDetailSeq());
		System.out.println("创建日期:"+detail.getCreateDate());
	}
  }

  @Test
  public void updateStatus() {
	  BatchPaymentToAcctReqDetailDTO detail = batchPaymentToAcctReqDetailService.getDetail(key);
	  System.out.println("-----------------------updateStatus old----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
	  System.out.println("是否已生成订单:"+detail.getOrderStatus());
	  
	  BatchPaymentToAcctReqDetailDTO upDetail = new BatchPaymentToAcctReqDetailDTO();
	  upDetail.setDetailSeq(key);
	  upDetail.setOrderStatus(1);
	  upDetail.setUpdateDate(new Date());
	  
	  if(!batchPaymentToAcctReqDetailService.updateStatus(upDetail, 0)){
		  System.out.println("updateStatus failed");
	  }
	  
	  detail = batchPaymentToAcctReqDetailService.getDetail(key);
	  System.out.println("-----------------------updateStatus new----------------------------");
	  System.out.println("明细流水号:"+detail.getDetailSeq());
	  System.out.println("创建日期:"+detail.getCreateDate());
	  System.out.println("是否已生成订单:"+detail.getOrderStatus());
	  
  }
}
