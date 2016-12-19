package com.pay.fundout.bsp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fundout.bsp.service.FundAdjustmentService;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.workorder.WorkOrderDto;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.fundout.withdraw.service.workorder.WorkorderService;

@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/*.xml", 
		"classpath*:context/**/*.xml"})
public class FundAdjustmentServiceImplTest extends AbstractTestNGSpringContextTests{

	@Resource(name = "fo-bsp-fundAdjustmentService")
	private FundAdjustmentService fundAdjustmentService;
	
	@Resource(name = "fundout-workorderService")
	private WorkorderService workorderService;
	@Resource(name = "fundout-withdraw-pay2AcctService")
	private Pay2AcctService pay2AcctService;
	
	private long orderId;
	
  @Test
  public void createOrder() {
    Pay2AcctOrder order = new Pay2AcctOrder();
    
    order.setCreateDate(new Date());
    order.setAmount(1L);
    
    order.setPayerMember(10000000114L);
    order.setPayerCode("13501972866");
    order.setPayerMemType(MemberTypeEnum.INDIVIDUL.getCode());
    order.setPayerAcctCode("20010200100011000000011410");
    order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
    order.setPayerName("彭炯");
    
    order.setPayeeMember(10000000001L);
    order.setPayeeCode("hanpay@pay.com");
    order.setPayeeMemType(MemberTypeEnum.INDIVIDUL.getCode());
    order.setPayeeAcctCode("20010100100011000000000110");
    order.setPayeeAcctType(AcctTypeEnum.BASIC_CNY.getCode());
    order.setPayeeName("企业测");
    
    
    
    WorkOrderDto workOrder = new WorkOrderDto();
    workOrder.setCreateDate(new Date());
    workOrder.setCreateMembercode(10000000001L);
    workOrder.setCreateOperator("admin");
    workOrder.setExternalInfo("BSPTEST001");
    workOrder.setStatus(0);
    
    fundAdjustmentService.createOrder(order, workOrder);
	orderId = order.getSequenceId();
	System.out.println("资金调账申请成功");;
    
  }
  
  @Test(dependsOnMethods={"createOrder"})
  public void auditOrder() {
	  
	  
	  WorkOrderDto dto = workorderService.findByOrderSeq(orderId);
	  if(dto==null){
		  System.out.println("无效的请求");
	  }
	  
	  if(dto.getStatus()!=0){
		  System.out.println("请求已被处理");
	  }
	  
	  Pay2AcctOrder order = pay2AcctService.findById(orderId);
	  if(order==null){
		  System.out.println("无效的请求");
	  }
	  
	  if(order.getStatus()!=101){
		  System.out.println("请求已被处理");
	  }
	  
	  order.setStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
	  order.setUpdateDate(new Date());

	  WorkOrderDto workOrder = new WorkOrderDto();
	  workOrder.setAuditMembercode(10000000001L);
	  workOrder.setAuditOperator("admin");
	  workOrder.setAuditRemark("同意");
	  workOrder.setStatus(1);
	  workOrder.setSequenceId(dto.getSequenceId());
	  
	  fundAdjustmentService.auditOrder(order, Pay2AcctService.FUNDADJUSTMENT_ORDER_PASS, workOrder);
	  
  }
  
//  public static void main(String[] args){
//	  System.out.println(("923456789012345678"+1).hashCode());
//	  System.out.println(Integer.toHexString(-998152710));
//	  System.out.println(Integer.toHexString(998152710));
//	  
//	  for(int i = 0 ; i < 100 ;i++){
//		  	System.out.println(Integer.toHexString(("123456789012345678"+i).hashCode()));
//	  }
//  }
}
