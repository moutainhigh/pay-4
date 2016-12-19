package com.pay.poss.personmanager.dao.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dao.UserLogDAO;
import com.pay.poss.personmanager.dto.UserLogDto;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })
public class UserLogDAOTest extends AbstractTestNGSpringContextTests{
	@Autowired
	UserLogDAO possUserLogDAO;
	
	@Test
	public void TestUserLogDAO(){
		Assert.assertNotNull(this.possUserLogDAO);
	}
	
	@Test
	public void testUserLogList(){
		UserLogDto userLogDto =new UserLogDto ();
		userLogDto.setPageEndRow(100);
		userLogDto.setPageStartRow(0);
		List<UserLogDto> dtos = this.possUserLogDAO.selectUserLogList(userLogDto);
		Assert.assertNotNull(dtos);
	}
}
