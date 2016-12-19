package com.pay.fo.order.service.withdraw.impl;

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
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.withdraw.WithdrawOrderService;
@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-fundoutorder-dao.xml",
		"classpath:context/fo/context-fo-orderinfo-dao.xml",
		"classpath:context/fo/context-fo-fundoutorder-service.xml",
		"classpath:context/fo/context-fo-orderinfo-service.xml",
		"classpath:context/fo/context-fo-withdraw-service.xml"})
public class WithdrawOrderServiceImplTest  extends AbstractTestNGSpringContextTests{
  @Resource(name = "fo-order-withdrawOrderService")
  private WithdrawOrderService withdrawOrderService;
  
  @Resource(name = "fo-order-fundoutOrderProcessService")
  private FundoutOrderProcessService fundoutOrderProcessService;
  
  private Long key;
  
  @Test
  public void createOrder(){
	  FundoutOrderDTO order = new FundoutOrderDTO();
	  order.setOrderType(OrderType.WITHDRAW.getValue());
	  order.setOrderSmallType(OrderSmallType.COMMON_WITHDRAW.getValue());
	  order.setOrderStatus(OrderStatus.INIT.getValue());
	  order.setOrderAmount(1L);
	  order.setIsPayerPayFee(1);
	  order.setFee(0L);
	  order.setRealoutAmount(1L);
	  order.setRealpayAmount(1L);
	  
	  order.setPayerName("彭炯");
	  order.setPayerMemberCode(10000000114L);
	  order.setPayerMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  order.setPayerAcctCode("20010200100011000000011410");
	  order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  order.setPayerLoginName("13501972866");
	  
	  order.setPayeeName("彭炯");
	  order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());//提现时，根据会员类型来绑定
	  order.setPayeeBankCode("10001001");
	  order.setPayeeBankName("中国工商银行");
	  order.setPayeeBankAcctType(1);
	  order.setPayeeBankAcctCode("11111111");
	  
	  order.setCreateDate(new Date());
	  order.setFundoutBankCode("10001001");
	  order.setFundoutMode(1);
	  withdrawOrderService.createOrder(order);
	  key = order.getOrderId();
	  
	  System.out.println("Success:"+key);
  }


  @Test
  public void updateOrderStatus(){
	  FundoutOrderDTO order = fundoutOrderProcessService.getOrder(key);
	  
	  if(order==null){
		  System.out.println("Not found FundOrderDTO instance[orderId:"+key+"].");
		  return;
	  }
	  System.out.println("-----------------------updateOrder old--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("fundoutModel:"+order.getFundoutMode());
	  
	  FundoutOrderDTO upOrder = new FundoutOrderDTO();
	  upOrder.setOrderId(key);
	  upOrder.setOrderStatus(OrderStatus.PROCESSING.getValue());
	  upOrder.setUpdateDate(new Date());
	  
	  if(!withdrawOrderService.updateOrderStatus(upOrder, order.getOrderStatus())){
		  System.out.println("updateOrderStatus failed.");
	  }
	  
	  order = fundoutOrderProcessService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("fundoutModel:"+order.getFundoutMode());
  }
}
