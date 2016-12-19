package com.pay.app.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;

import com.pay.app.AbstractTestNG;
import com.pay.app.service.mail.MailService;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-26 下午03:49:50
 */

@ContextConfiguration(locations = {"classpath*:context/*.xml" })
public class MailServiceTest extends AbstractTestNG{

	@Resource
	private MailService payMailService;

	@Override
	//@Test
	public void testSave() {
		//payMailService.saveCheckCode(UUIDUtil.uuid(),"1","origin","test@.pay.com",null);
		List<String> recAddress=new ArrayList<String>(1);
		recAddress.add("jin_zeng@staff.pay.com");
		//payMailService.sendMail(null, recAddress, "testtesttesttest", "http://www.baidu.com", 4, "支付激活邮件");
	}

	@Override
	public void testDeleteById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testFindById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testLoadAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}

}
