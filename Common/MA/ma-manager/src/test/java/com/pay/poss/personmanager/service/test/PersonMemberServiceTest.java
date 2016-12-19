package com.pay.poss.personmanager.service.test;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.inf.dao.Page;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.service.PersonMemberService;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })
public class PersonMemberServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	PersonMemberService postPersonManagerService;

	@Test
	public void testpostPersonManagerService() {
		Assert.assertNotNull(this.postPersonManagerService);
	}

	@Test
	public void testselectPersonMemberList() {
		PersonMemberSearchDto memberSearchDto = new PersonMemberSearchDto();
//		memberSearchDto.setMemberCode(10000000341L);
//		memberSearchDto.setIdcStatus(1L);
		Date date=new Date();
		memberSearchDto.setRegisterEndDate(date);
//		memberSearchDto.setRegisterStartDate(date);
		Page<PersonMemberSearchDto> page = new Page<PersonMemberSearchDto>();
		List<PersonMemberSearchDto> list = this.postPersonManagerService.selectPersonMemberList(memberSearchDto, page);
		if (null != list && list.size() > 0)
			for (PersonMemberSearchDto dto : list) {
				System.out.println(dto.getIdcStatusName() + "  " + dto.getStatusName() + "  " + dto.getAcctStatusName());
			}
	}

}
