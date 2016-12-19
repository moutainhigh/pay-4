package com.pay.poss.personmanager.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dao.IndividualDAO;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })
public class IndividualDAOTest extends AbstractTestNGSpringContextTests {
	@Autowired
	IndividualDAO possIndividualDAO;

	@Test
	public void testIndividualDao() {
		Assert.assertNotNull(this.possIndividualDAO);
	}
	
	@Test
	public void testSelectIndividual(){
		this.possIndividualDAO.selectIndividualDtoByMemberCode(10000000351L);
	}
}
