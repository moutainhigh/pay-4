package com.pay.pe.accumulated.flow.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.pe.accumulated.flow.dao.CumulatedFlowDao;
import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;
import com.pay.pe.accumulated.flow.service.CumulatedFlowService;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.accumulated.resources.service.AccumulatedResService;

public class CumulatedFlowServiceImpl implements CumulatedFlowService {

	private CumulatedFlowDao 		cumulatedFlowDao;
	private AccumulatedResService 	accumulatedResService;

	@Override
	public List<CumulatedFlowDto> queryFlowListByMonth(Integer paymentServiceCode,int month) {			
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("paymentServiceCode", paymentServiceCode);
		paramMap.put("month", month);
		return cumulatedFlowDao.queryFlowByMonth(paramMap);
	}
	
	@Override
	public Long selectAccumulatedOrderId() {
		return 8000000000000000000L+cumulatedFlowDao.selectAccumulatedOrderId();
	}
	
	@Override
	public boolean saveCumulatedFlowByMonthRnTx() {
		Map<String,Object> paramMap=new HashMap<String,Object>(3);
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		paramMap.put("endDate", cal.getTime());
		//edited by scott.ling
		cal.add(Calendar.MONTH, -1);		
		paramMap.put("beginDate", cal.getTime());		
		List<AccumulatedResourcesDto> list=accumulatedResService.queryAccumulatedResourcesList();
		 if(null!=list && list.size()>0){
			 System.out.println(list.size());
			 for(AccumulatedResourcesDto resourcesDto:list){
					paramMap.put("paymentServiceCode", resourcesDto.getPaymentServiceCode());
					 List<CumulatedFlowDto> flowList=cumulatedFlowDao.queryCumulatedFlowList(paramMap);
					 if(null!=flowList && flowList.size()>0){
						 for(CumulatedFlowDto flowDto:flowList){
							 flowDto.setDealCode(resourcesDto.getDealCode());
							 flowDto.setOrderType(resourcesDto.getOrderType());
							 flowDto.setType(1);
							 flowDto.setMonth(cal.get(Calendar.MONTH));
							 cumulatedFlowDao.create(flowDto);
						 }
					 }
				 }
		 }
		return true;
	}
	
	
	public void setCumulatedFlowDao(CumulatedFlowDao cumulatedFlowDao) {
		this.cumulatedFlowDao = cumulatedFlowDao;
	}

	public void setAccumulatedResService(AccumulatedResService accumulatedResService) {
		this.accumulatedResService = accumulatedResService;
	}




}
