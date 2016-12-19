package com.pay.fo.order.service.base.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.base.OrderInfoService;


@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml",
		"classpath:context/fo/context-fundout-base-dao.xml",
		"classpath:context/fo/context-fo-fundoutorder-dao.xml",
		"classpath:context/fo/context-fo-orderinfo-dao.xml",
		"classpath:context/fo/context-fo-fundoutorder-service.xml",
		"classpath:context/fo/context-fo-orderinfo-service.xml"})
public class OrderInfoServiceImplTest extends AbstractTestNGSpringContextTests{

  @Resource(name = "fo-order-fundoutOrderService")
  private FundoutOrderService fundoutOrderService;
  @Resource(name = "fo-order-orderInfoService")
  private OrderInfoService orderInfoService ;
  private Long key=2001106151132005449L;
	
  @Test
  public void createOrder() {
	  key = 2001106151132005449L;
	  FundoutOrderDTO foOrder = (FundoutOrderDTO) fundoutOrderService.getOrder(key);
	  OrderInfoDTO order = new OrderInfoDTO();
	  order.setOrderId(foOrder.getOrderId());
	  order.setOrderType(foOrder.getOrderType());
	  order.setOrderStatus(foOrder.getOrderStatus());
	  order.setTradeType(foOrder.getTradeType());
	  
	  order.setPayerLoginName(foOrder.getPayerLoginName());
	  order.setPayerMemberCode(foOrder.getPayerMemberCode());
	  order.setPayerName(foOrder.getPayerName());

	  order.setPayeeName(foOrder.getPayeeName());
	  
	  order.setOrderAmount(foOrder.getOrderAmount());
	  order.setIsPayerPayFee(foOrder.getIsPayerPayFee());
	  order.setFee(foOrder.getFee());
	  order.setRealoutAmount(foOrder.getRealoutAmount());
	  order.setRealpayAmount(foOrder.getRealpayAmount());
	  
	  order.setCreateDate(foOrder.getCreateDate());
	  order.setPriority(foOrder.getPriority());
	  
	  orderInfoService.createOrder(order);
	  
	  System.out.println("Success:"+key);
  }

  @Test
  public void getOrder() {
	  OrderInfoDTO order = (OrderInfoDTO) orderInfoService.getOrder(key);
	    if(order==null){
			  System.out.println("Not found OrderInfoDTO instance[orderId:"+key+"].");
			  return;
		  }
		  System.out.println("-----------------------getOrder--------------------");
		  System.out.println("orderId:"+order.getOrderId());
		  System.out.println("orderType:"+order.getOrderType());
		  System.out.println("orderStatus:"+order.getOrderStatus());
		  System.out.println("payerName:"+order.getPayerName());
		  System.out.println("payeeName:"+order.getPayeeName());
  }


  @Test
  public void updateOrder() {
	  OrderInfoDTO order = (OrderInfoDTO) orderInfoService.getOrder(key);
	  
	  if(order==null){
		  System.out.println("Not found OrderInfoDTO instance[orderId:"+key+"].");
		  return;
	  }
	  System.out.println("-----------------------updateOrder old--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("updateDate:"+order.getUpdateDate());
	  
	  OrderInfoDTO upOrder = new OrderInfoDTO();
	  upOrder.setOrderId(key);
	  upOrder.setUpdateDate(new Date());
	  
	  if(!orderInfoService.updateOrder(upOrder)){
		  System.out.println("updateOrder failed.");
	  }
	  
	  order = (OrderInfoDTO) orderInfoService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("updateDate:"+order.getUpdateDate());
  }

  @Test
  public void updateOrderStatus() {
	  OrderInfoDTO order = (OrderInfoDTO) orderInfoService.getOrder(key);
	  
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
	  System.out.println("updateDate:"+order.getUpdateDate());
	  
	  OrderInfoDTO upOrder = new OrderInfoDTO();
	  upOrder.setOrderId(key);
	  upOrder.setOrderStatus(OrderStatus.PROCESSING.getValue());
	  upOrder.setUpdateDate(new Date());
	  
	  if(!orderInfoService.updateOrderStatus(upOrder, order.getOrderStatus())){
		  System.out.println("updateOrderStatus failed.");
	  }
	  
	  order = (OrderInfoDTO) orderInfoService.getOrder(key);
	  System.out.println("-----------------------updateOrder new--------------------");
	  System.out.println("orderId:"+order.getOrderId());
	  System.out.println("orderType:"+order.getOrderType());
	  System.out.println("orderStatus:"+order.getOrderStatus());
	  System.out.println("payerName:"+order.getPayerName());
	  System.out.println("payeeName:"+order.getPayeeName());
	  System.out.println("updateDate:"+order.getUpdateDate());
  }
}
