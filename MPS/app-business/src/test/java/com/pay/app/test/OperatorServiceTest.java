/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.test;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.model.TOperator;
import com.pay.app.service.operator.OperatorServiceFacade;
import com.pay.inf.exception.AppException;

/**
 * 操作员信息服务
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午07:56:37
 * 
 */
public class OperatorServiceTest extends AbstractTestNG {
	
	@Resource(name="app-tOperatorService")
	OperatorServiceFacade operatorServiceFacade;

	//@Test
	public void testOperatorService() {
		Assert.assertNotNull(this.operatorServiceFacade);
	}

	@Override
	public void testDeleteById() {
		// TODO Auto-generated method stub
		
	}

	//@Test
	public void testFindById() {
		TOperator operator = operatorServiceFacade.getOperatorById(7);
		Assert.assertNotNull(operator);
		System.out.println(operator.getMemberCode());
	}
	@Test
	public void testGetByIdentity() {
		TOperator operator = operatorServiceFacade.getByIdentity("TestNA");
		Assert.assertNotNull(operator);
		System.out.println(operator.getMemberCode());
	}
	//@Test
	public void testGetByMemberCode() {
		TOperator operator = operatorServiceFacade.getByIdentityMemCode("TestNA", 10000000020L);
		Assert.assertNotNull(operator);
		System.out.println(operator.getIdentity());
	}
	
	@Override
	public void testLoadAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSave() {
		// TODO Auto-generated method stub
		
	}

	//@Test
	public void testUpdate() {
		TOperator operator = new TOperator();
		operator.setOperatorId(7);
		operator.setMemberCode(10000000020L);
		operator.setDepart("TestTB");
		operator.setEmail("TestNB@nb.com");
		operator.setPassword("test");
		operator.setName("测试234");
		operator.setNote("测试234");
		operator.setStatus(1);
		operator.setCertType(1);
		operator.setCertCode("1234567890123456");
		operatorServiceFacade.updateOperator(operator);
		
	}
	
	//@Test
	public void testCreateOperator() {
		TOperator operator = new TOperator();
		operator.setMemberCode(10000000020L);
		operator.setDepart("Test");
		operator.setIdentity("TestNB");
		operator.setEmail("TestNB@nb.com");
		operator.setPassword("test");
		operator.setName("CBA");
		operator.setNote("测试");
		operator.setStatus(1);
		operator.setCertType(1);
		operator.setCertCode("1234567890123456");
		try {
            operatorServiceFacade.createOperator(operator);
        } catch (AppException e) {
            logger.error("", e);
        }
		System.out.println("成功");
	}


}
