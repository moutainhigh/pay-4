package com.pay.fo.order.service.pay2acct.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderService;


@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-paytoacctorder-dao.xml",
		"classpath:context/fo/context-fo-orderinfo-dao.xml",
		"classpath:context/fo/context-fo-paytoacctorder-service.xml",
		"classpath:context/fo/context-fo-orderinfo-service.xml",
		"classpath:context/fo/context-fo-pay2acct-service.xml"})
public class Pay2AcctOrderServiceImplTest extends AbstractTestNGSpringContextTests{

	 @Resource(name = "fo-order-pay2AcctOrderService")
	  private Pay2AcctOrderService pay2AcctOrderService;
	  
	  @Resource(name = "fo-order-payToAcctOrderProcessService")
	  private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	  private Long key;
	  
  @Test
  public void createOrder() {
	  PayToAcctOrderDTO order = new PayToAcctOrderDTO();
	  order.setOrderType(OrderType.PAY2ACCT.getValue());
	  order.setOrderSmallType(OrderSmallType.COMMON_PAY2ACCT.getValue());
	  order.setOrderStatus(OrderStatus.INIT.getValue());
	  order.setOrderAmount(1L);
	  
	  order.setPayerName("彭炯");
	  order.setPayerMemberCode(10000000114L);
	  order.setPayerMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  order.setPayerAcctCode("20010200100011000000011410");
	  order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  order.setPayerLoginName("13501972866");
	  
	  order.setPayeeName("企业测");
	  order.setTradeType(TradeType.TO_BUSINESS.getValue());
	  order.setPayeeAcctCode("20010100100011000000000110");
	  order.setPayeeAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  order.setPayeeMemberCode(10000000001L);
	  order.setPayeeMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  order.setPayeeLoginName("pay@pay.com");
	  
	  order.setCreateDate(new Date());
	  pay2AcctOrderService.createOrder(order);
	  
	  key = order.getOrderId();
	  
	  System.out.println("Success:"+key);
  }

  @Test
  public void updateOrderStatus() {
	  PayToAcctOrderDTO order = payToAcctOrderProcessService.getOrder(key);
	  
	  if(order==null){
		  System.out.println("Not found PayToAcctOrderDTO instance[orderId:"+key+"].");
		  return;
	  }
	  System.out.println("-----------------------updateOrder old--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  
	  PayToAcctOrderDTO upOrder = new PayToAcctOrderDTO();
	  upOrder.setOrderId(key);
	  upOrder.setOrderStatus(OrderStatus.PROCESSING.getValue());
	  upOrder.setUpdateDate(new Date());
	  
	  if(!pay2AcctOrderService.updateOrderStatus(upOrder, order.getOrderStatus())){
		  System.out.println("updateOrderStatus failed.");
	  }
	  
	  order = payToAcctOrderProcessService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
  }
}
