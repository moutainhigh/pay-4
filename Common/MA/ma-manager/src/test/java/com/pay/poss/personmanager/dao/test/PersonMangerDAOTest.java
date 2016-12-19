package com.pay.poss.personmanager.dao.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dao.PersonMangerDAO;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;

@ContextConfiguration(locations = { "classpath*:context/*.xml" ,"classpath*:config/spring/**/*.xml"})
public class PersonMangerDAOTest extends AbstractTestNGSpringContextTests {
	@Autowired
	PersonMangerDAO postPersonManagerDao;

	@Test
	public void testPersonMangerDao() {
		Assert.assertNotNull(this.postPersonManagerDao);
	}
	
	@Test
	public void testQuery(){
		PersonMemberSearchDto memberSearchDto =new PersonMemberSearchDto();
		memberSearchDto.setPageStartRow(0);
		memberSearchDto.setPageEndRow(10);
		List<PersonMemberSearchDto> dtos = this.postPersonManagerDao.queryPersonList(memberSearchDto);
		Assert.assertNotNull(dtos);
	}
	
	@Test
	public void testCount(){
		PersonMemberSearchDto memberSearchDto =new PersonMemberSearchDto();
		this.postPersonManagerDao.countPersonMember(memberSearchDto);
	}

}
