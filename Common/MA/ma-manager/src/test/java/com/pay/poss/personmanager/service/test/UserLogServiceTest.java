package com.pay.poss.personmanager.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dto.UserLogDto;
import com.pay.poss.personmanager.service.UserLogService;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:context/**/*.xml","classpath*:config/spring/**/*.xml" })
public class UserLogServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired
	UserLogService postUserLogService;
	
	@Test
	public void testUserLogService(){
		Assert.assertNotNull(this.postUserLogService);		
	}
	
	@Test
	public void testUserLogList(){
		UserLogDto userLogDto=new UserLogDto();
		userLogDto.setMemberCode(10000000053L);
		this.postUserLogService.queryUserLogByMemberCode(userLogDto);
	}
}
