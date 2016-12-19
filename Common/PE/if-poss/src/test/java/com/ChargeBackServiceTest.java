package com;


import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.pay.pe.accumulated.chargebackentry.service.ChargeBackService;

@ContextConfiguration(locations={"classpath*:context/**/**/*.xml"})
public class ChargeBackServiceTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name="chargeBackService")
	ChargeBackService chargeBackService;
	
	
//	@Test
	public void testChargeBackService(){
		Assert.assertNotNull(chargeBackService);
	}
	
	@Test
	public void testdoUpdateBalance(){
		System.out.println(chargeBackService.doUpdateBalance(24963713001L,101));	
	}

}
