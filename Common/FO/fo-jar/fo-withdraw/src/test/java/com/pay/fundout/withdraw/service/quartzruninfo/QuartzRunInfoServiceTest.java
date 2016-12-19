/**
 *  File: QuartzRunInfoServiceTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-13      Sean_yi      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.quartzruninfo;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.quartzruninfo.QuartzRunInfoDTO;
import com.pay.fundout.withdraw.service.quartzruninfo.QuartzRunInfoService;


@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:/context/spring/quartzruninfo/*.xml",
		"classpath*:/context/spring/base/*.xml",
		 })
public class QuartzRunInfoServiceTest  extends AbstractTestNGSpringContextTests {
	
	@Resource(name="fundout_withdraw_quartzRunInfoService")
	private QuartzRunInfoService quartzRunInfoService;
	
	public void setQuartzRunInfoService(QuartzRunInfoService quartzRunInfoService) {
		this.quartzRunInfoService = quartzRunInfoService;
	}
	
	
	public void saveQuartzInfo(){
		QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
		dto.setBusiType(1);
		dto.setFkId(1l);
		dto.setCreateDate(new Date());
		dto.setStatus(1);
		System.out.println(quartzRunInfoService.createQuartzRunInfo(dto));
	}
	
	public void updateQuartzInfo(){
		QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
		dto.setBusiType(2);
		dto.setSequenceId(104l);
		dto.setUpdateDate(new Date());
		quartzRunInfoService.updateQuartzRunInfo(dto);
	}
	
	@Test
	public void getQuartzRunInfoDTOByBussiType(){
		Long fkId =1l;
		int busiType = 2;
		QuartzRunInfoDTO dto = quartzRunInfoService.getQuartzRunInfoByBussiType(fkId, busiType);
		
		System.out.println(".............................." + dto);
	}
}
