
package com.pay.fo.order.service.base.impl;

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
import com.pay.fo.order.service.base.FundoutOrderService;


@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-fundoutorder-dao.xml",
		"classpath:context/fo/context-fo-orderinfo-dao.xml",
		"classpath:context/fo/context-fo-orderinfo-service.xml",
		"classpath:context/fo/context-fo-fundoutorder-service.xml",
		"classpath*:context/**/*.xml"})
public class FundoutOrderServiceImplTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "fo-order-fundoutOrderService")
	private FundoutOrderService fundoutOrderService;
	
	private Long key;
	
  @Test
  public void createOrder() {
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
	  order.setPayerMemberCode(10000000001L);
	  order.setPayerMemberType(MemberTypeEnum.INDIVIDUL.getCode());
	  order.setPayerAcctCode("20010200100011000000011410");
	  order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
	  order.setPayerLoginName("13501972866");
	  
	  order.setPayeeName("测试");
	  order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());
	  order.setPayeeBankCode("10001001");
	  order.setPayeeBankName("中国工商银行");
	  order.setPayeeBankAcctType(1);
	  order.setPayeeBankAcctCode("11111111");
	  
	  order.setCreateDate(new Date());
	  order.setFundoutBankCode("10001001");
	  order.setFundoutMode(1);
	  
	  key = fundoutOrderService.createOrder(order);
	  
	  System.out.println("Success:"+key);
	  
  }

  //@Test
  public void getOrder() {
    FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
    if(order==null){
		  System.out.println("Not found FundOrderDTO instance[orderId:"+key+"].");
		  return;
	  }
	  System.out.println("-----------------------getOrder--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
  }

  //@Test
  public void updateOrder() {
	  FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
	  
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
	  upOrder.setFundoutMode(2);
	  upOrder.setUpdateDate(new Date());
	  
	  if(!fundoutOrderService.updateOrder(upOrder)){
		  System.out.println("updateOrder failed.");
	  }
	  
	  order = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("fundoutModel:"+order.getFundoutMode());
	  
  }

  //@Test
  public void updateOrderStatus() {
	  FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
	  
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
	  
	  if(!fundoutOrderService.updateOrderStatus(upOrder, order.getOrderStatus())){
		  System.out.println("updateOrderStatus failed.");
	  }
	  
	  order = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("fundoutModel:"+order.getFundoutMode());
	  
  }
}
