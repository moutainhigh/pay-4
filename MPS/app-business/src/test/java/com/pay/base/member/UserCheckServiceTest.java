package com.pay.base.member;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.usercheck.UserCheckService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(transactionManager = "app-transactionManager", defaultRollback = true)
@Transactional
public class UserCheckServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Resource(name="base-userCheckService")
	private UserCheckService userCheckService;

	@Resource(name="dataSourceAcc")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	@Test
	public void testMatrixCardTransMgrService() {
		Assert.assertNotNull(userCheckService);
	}

	//@Test
	public void testqueryUserRelation(){
		String userId = "1234563333";
		boolean me = this.userCheckService.queryUserRelation(userId);
		Assert.assertEquals(me, false);
	}
	
	//@Test
	public void testupdateUserRelation() {
		Long memberCode = 10000000056L;
		String userid = "1234563333";
		Integer dd = this.userCheckService.updateUserRelation(memberCode,userid);
		System.out.println("dd==================="+dd);
	}
	//@Test
	public void testValidateSecurMemberQuestionWidthMemberInfo(){
		Long memberCode = 10000000056L;
		boolean f = this.userCheckService.validateMemberRelation(memberCode);
		Assert.assertTrue(f);
	}
	
	//@Test
	public void testupdateUserRelationNew(){
		Long memberCode = 10000000056L;
		String userid = "12345622222";
		ResultDto f = this.userCheckService.updateUserRelationNew(memberCode,userid);
	}
	
	@Test
	public void testcheckUser(){
		String memberCode = "15900964610";
		String userid = "qwerqwer";
		Long f = this.userCheckService.checkUser(memberCode,userid);
		System.out.println("memberCode=========================="+f);
	}
	

}
