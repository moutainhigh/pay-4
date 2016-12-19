package com.pay.app.test;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.service.user.SsoUserVistedService;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-8-20 下午05:33:45
 */
public class SsoUserVistedServiceTest extends AbstractTestNG{
	
	@Resource(name="app-ssoUserVistedService")
	private SsoUserVistedService ssoUserVistedService;
	
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
	//@Test
	public void testSave() {
		ssoUserVistedService.saveSsoUserVisted("1000");
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	public void testQuerySsoUserVistedByUserId() {
		System.out.println(ssoUserVistedService.querySsoUserVistedByUserId("123214"));
	}

}
