package com.pay.pe.accumulated.chargeback.service;

import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;

public interface ChargeBackService {
	
	/**
	* @Title: doAccumulatedChargeBack
	* @Description: 累计扣费
	* @param @param dto
	* @param @param acctCode
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	*/ 
	public boolean doAccumulatedChargeBack(AccumulatedResourcesDto dto,String acctCode,String orderId)throws Exception;
	
	
	public int getChargeBackmethod();
}
