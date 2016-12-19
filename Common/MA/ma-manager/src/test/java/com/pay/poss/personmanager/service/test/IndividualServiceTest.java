package com.pay.poss.personmanager.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.service.IndividualService;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })

public class IndividualServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired
	IndividualService postIndividualService;
	
	@Test
	public void testIndividualService(){
		Assert.assertNotNull(this.postIndividualService);
	}
	
	@Test
	public void testqueryIndividualByMemberCode(){
		Long memberCode=10000000090L;
		this.postIndividualService.queryIndividualByMemberCode(memberCode);
	}
	
}
