package com.pay.pricingstrategy.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.pricingstrategy.dao.CumulatedFlowDao;
import com.pay.pricingstrategy.dto.CumulatedFlowDto;
import com.pay.pricingstrategy.service.CumulatedFlowService;



public class CumulatedFlowServiceImpl implements CumulatedFlowService {

	private CumulatedFlowDao 		cumulatedFlowDao;

	@Override
	public BigDecimal queryAmountByMonth(Integer paymentServiceCode,int month,String acctCode) {			
		Map<String,Object> paramMap=new HashMap<String,Object>(3);
		paramMap.put("paymentServiceCode", paymentServiceCode);
		paramMap.put("month", month);
		paramMap.put("acctCode", acctCode);
		List<CumulatedFlowDto> flowDtoList=cumulatedFlowDao.queryCumulatedFlowList(paramMap);
		if(null!=flowDtoList && flowDtoList.size()>0){
			CumulatedFlowDto flowDto=flowDtoList.get(0);
			if(null!=flowDto && null!=flowDto.getTotalAmount()){
				return flowDto.getTotalAmount();
			}
		}
		return BigDecimal.ZERO;
	}
	
	
	
	
	public void setCumulatedFlowDao(CumulatedFlowDao cumulatedFlowDao) {
		this.cumulatedFlowDao = cumulatedFlowDao;
	}




}
