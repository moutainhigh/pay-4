package com.pay.resources;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;
import com.pay.pe.accumulated.flow.service.CumulatedFlowService;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.accumulated.resources.service.AccumulatedResService;

@ContextConfiguration(locations={"classpath*:context/**/*.xml"})
public class CumulatedFlowServiceTest extends AbstractTestNGSpringContextTests{
	@Resource(name="cumulatedFlowService")
	CumulatedFlowService cumulatedFlowService;
	
	@Test
	public void testAccumulatedResources(){
		Assert.assertNotNull(cumulatedFlowService);
	}
	
	@Test
	public void testQueryAccumulatedResourcesList(){
		
		
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.MONTH, -1);	
		System.out.println(cal.getTime());

		System.out.println(cal.get(Calendar.MONTH));
		List<CumulatedFlowDto> list=cumulatedFlowService.queryFlowListByMonth(71303, 1);
		if(null!=list && list.size()>0){
			for(CumulatedFlowDto dto:list){
				System.out.println(dto.getTotalAmount()+"   : "+dto.getPaymentServiceCode());
			}
		}
	}
	
//	@Test
	public void testsaveCumulatedFlowByMonth(){
		System.out.println(cumulatedFlowService.saveCumulatedFlowByMonthRnTx());
	}

}
