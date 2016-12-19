package com.pay.pricingstrategy.service;

import java.math.BigDecimal;

public interface CumulatedFlowService {


	
	
	/**
	* @Title: queryAmountByMonth
	* @Description: 根据支付服务，账户，月份得到累计金额
	* @param @param paymentServiceCode
	* @param @param month
	* @param @param acctCode
	* @param @return    设定文件
	* @return BigDecimal    返回类型
	* @throws
	*/ 
	public BigDecimal queryAmountByMonth(Integer paymentServiceCode,int month,String acctCode);
	

	
}
