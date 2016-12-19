package com.pay.app.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.dto.MemberInfoDto;
import com.pay.base.service.member.MemberService;
import com.pay.inf.exception.AppException;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(defaultRollback=false,transactionManager="base-transactionManager")
public class MemberServiceTest extends  AbstractTransactionalTestNGSpringContextTests {

    
    @Override
    public void setDataSource(@Qualifier("dataSourceAcc") DataSource ds){
        super.setDataSource(ds);
    }
    
    @Resource(name="base-memberService")
    private MemberService memberService;
    private int t=0;
    
    @Test
    public void testMemberService() {
        Assert.assertNotNull(this.memberService);
    }
    
    @Test
    public void testRegister(){
        MemberInfoDto memberInfoDto=new MemberInfoDto();
        memberInfoDto.setLoginName("jin_"+t+"@test.com");
        memberInfoDto.setRealName("test"+t);
        memberInfoDto.setPassword("testtest");
        memberInfoDto.setRegType(2);
        memberInfoDto.setSecurityQuestion(1);
        memberInfoDto.setSecurityAnswer("test");
        try {
            memberService.doIndividualMemberRegisterRdTx(memberInfoDto);
        } catch (AppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
