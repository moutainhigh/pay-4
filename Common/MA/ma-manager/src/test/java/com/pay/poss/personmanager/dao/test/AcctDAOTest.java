package com.pay.poss.personmanager.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dao.AcctDAO;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })
public class AcctDAOTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	AcctDAO possAcctDAO;
	
	@Test
	public void testAcctDAOTest(){
		Assert.assertNotNull(this.possAcctDAO);
	}
	
	@Test
	public void testAcctDAOByMemberCode(){
		Long memberCode=10000000056L;
		this.possAcctDAO.selectAcctByMemberCode(memberCode);
	}

}
