package com.pay.poss.personmanager.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.personmanager.dao.BankAcctDAO;
import com.pay.poss.personmanager.dto.BankAcctDto;

@ContextConfiguration(locations = { "classpath*:context/*.xml", "classpath*:config/spring/**/*.xml" })
public class BankAcctDAOTest extends AbstractTestNGSpringContextTests {
	@Autowired
	BankAcctDAO possBankAcctDAO;
	
	@Test
	public void testBankAcctDAOTest(){
		Assert.assertNotNull(this.possBankAcctDAO);		
	}
	
	@Test
	public void testBankAcctList(){
		BankAcctDto bankAcctDto=new BankAcctDto();
		bankAcctDto.setMemberCode(10000000015L);
		this.possBankAcctDAO.selectBankAcctDtoList(bankAcctDto);
	}
	
}
