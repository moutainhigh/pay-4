package com.pay.resources;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.pe.accumulated.chargeback.service.ChargeBackHandlerService;
@ContextConfiguration(locations={"classpath*:context/**/*.xml"})

public class ChargeBackHandlerServiceTest extends AbstractTestNGSpringContextTests{

	@Resource(name="chargeBackHandlerService")
	private ChargeBackHandlerService chargeBackHandlerService;
	
	@Test
	public void testChargeBackHandlerService(){
		Assert.assertNotNull(chargeBackHandlerService);
	}
	
	@Test
	public void testdoChargeBackProcess(){
		chargeBackHandlerService.doChargeBackProcess();
	}
}
