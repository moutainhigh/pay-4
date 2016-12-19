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
import com.pay.base.service.member.MemberOperateService;
import com.pay.base.service.member.MemberService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(transactionManager = "base-transactionManager", defaultRollback = true)
@Transactional
public class MemberQueryServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Resource(name="base-memberService")
	private MemberService memberService;
	@Resource(name="base-memberOperateService")
	private MemberOperateService memberOperateService;
	
	@Resource(name="dataSourceAcc")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	//@Test
	public void testMatrixCardTransMgrService() {
		Assert.assertNotNull(memberService);
		Assert.assertNotNull(memberOperateService);
	}

	//@Test
	public void testqueryMemberInfoByMemberCodeNsTx() {
		Long memberCode = 10000000351L;
		MemberInfoDto memberInfoDto = this.memberService.queryMemberInfoByMemberCodeNsTx(memberCode);
		Assert.assertNotNull(memberInfoDto);
	}
	
	//@Test
	public void testdoUpdateMemberInfoRnTx() {
		MemberInfoDto memberInfoDto = new MemberInfoDto(); 
		memberInfoDto.setMemberCode(10000000351L);
		memberInfoDto.setAddr("222");
		memberInfoDto.setQq("2222");
		memberInfoDto.setCity("23222");
		ResultDto dd = this.memberService.doUpdateMemberInfoRnTx(memberInfoDto);
		Assert.assertNotNull(dd);
	}
	//@Test
	public void testValidateSecurMemberQuestionWidthMemberInfo(){
		ResultDto resultDto = this.memberService.validateSecurMemberQuestionWidthMemberInfo(10000000056L, 1, "pay");
		Assert.assertNotNull(resultDto);
		Assert.assertTrue(resultDto.isResultStatus());
	}
	
	@Test
	public void testqueryEPLastLoginTime(){
	    Long memberCode = 10000000351L;
	    Long operatorId=1L;
	    String lastTime= memberOperateService.queryEpLastLoginTime(memberCode, operatorId);
	    Assert.assertNotNull(lastTime);
	    System.out.println("=============="+lastTime);
	}
	
	@Test
    public void testqueryStatusByLoginTime(){
        String loginName = "13800138001";
 
        Integer s= memberService.checkLoginNameByRegister(loginName);
        Assert.assertNotNull(s);
        if(s==null)
            System.out.println("null");
        System.out.println("=============="+s);
    }

}
