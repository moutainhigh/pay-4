package com.pay.pe.accumulated.flow.service;

import java.util.List;

import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;

public interface CumulatedFlowService {


	

	/**
	* @Title: queryFlowByMonth
	* @Description: 根据支付服务，月份得到累计金额
	* @param @param paymentServiceCode
	* @param @param month
	* @param @return    设定文件
	* @return List<CumulatedFlowDto>    返回类型
	* @throws
	*/ 
	public List<CumulatedFlowDto> queryFlowListByMonth(Integer paymentServiceCode,int month);
	
	
	/**
	* @Title: saveCumulatedFlowByMonthRnTx
	* @Description: 插入当月的交易流量
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	*/ 
	public boolean saveCumulatedFlowByMonthRnTx();
	
	
	public Long selectAccumulatedOrderId();
	
}
