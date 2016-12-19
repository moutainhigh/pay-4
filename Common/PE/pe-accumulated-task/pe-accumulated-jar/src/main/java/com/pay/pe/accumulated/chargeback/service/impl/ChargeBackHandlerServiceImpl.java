package com.pay.pe.accumulated.chargeback.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.accumulated.chargeback.service.ChargeBackHandlerService;
import com.pay.pe.accumulated.chargeback.service.ChargeBackService;
import com.pay.pe.accumulated.chargeback.service.TokenOnFactory;
import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;
import com.pay.pe.accumulated.flow.service.CumulatedFlowService;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.accumulated.resources.service.AccumulatedResService;

public class ChargeBackHandlerServiceImpl implements ChargeBackHandlerService {
	
	private AccumulatedResService 	accumulatedResService;
	private CumulatedFlowService 	cumulatedFlowService;
	private TokenOnFactory			tokenOnFactory;

	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void doChargeBackProcess() {
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);		
		List<AccumulatedResourcesDto> resourceList=accumulatedResService.queryAccumulatedResourcesList();
		for(AccumulatedResourcesDto resourcesDto:resourceList){
			ChargeBackService chargeBackService=tokenOnFactory.getChargeBackService(resourcesDto.getTakeOn());
			
			List<CumulatedFlowDto> list=cumulatedFlowService.queryFlowListByMonth(resourcesDto.getPaymentServiceCode(), cal.get(Calendar.MONTH));
			if(null!=list && list.size()>0){
				for(CumulatedFlowDto dto:list){
					try{						
						chargeBackService.doAccumulatedChargeBack(resourcesDto, dto.getAcctCode(), ""+cumulatedFlowService.selectAccumulatedOrderId());
					}catch(Exception e){
						log.error(e);
					}
				}
			}
		}	
	}
	
	public void setAccumulatedResService(AccumulatedResService accumulatedResService) {
		this.accumulatedResService = accumulatedResService;
	}
	public void setCumulatedFlowService(CumulatedFlowService cumulatedFlowService) {
		this.cumulatedFlowService = cumulatedFlowService;
	}
	public void setTokenOnFactory(TokenOnFactory tokenOnFactory) {
		this.tokenOnFactory = tokenOnFactory;
	}



}
