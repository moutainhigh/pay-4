package com.pay.resources;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.accumulated.resources.service.AccumulatedResService;

@ContextConfiguration(locations={"classpath*:context/**/*.xml"})
public class AccumulatedResourcesTest extends AbstractTestNGSpringContextTests{
	@Resource(name="accumulatedResService")
	AccumulatedResService accumulatedResService;
	
	@Test
	public void testAccumulatedResources(){
		Assert.assertNotNull(accumulatedResService);
	}
	
	@Test
	public void testQueryAccumulatedResourcesList(){
		List<AccumulatedResourcesDto> list=accumulatedResService.queryAccumulatedResourcesList();
		if(null!=list && list.size()>0){
			for(AccumulatedResourcesDto dto:list){
				System.out.println("  paymentServiceCode : "+dto.getPaymentServiceCode());
			}
		}
	}

}
