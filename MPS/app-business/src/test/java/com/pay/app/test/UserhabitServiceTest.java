/**
 *  File: UserhabitServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.dto.UserhabitDTO;
import com.pay.app.service.user.UserhabitService;

/**
 * 
 */
public class UserhabitServiceTest extends AbstractTestNG{
	
	@Resource(name="app-userhabitService")
	private UserhabitService userhabitService;

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
	@Test
	public void testSave() {
/*		UserhabitDTO dto = new UserhabitDTO();
		dto.setBankCode("aaaa");
		dto.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		dto.setPayway(11);
		dto.setRemark("aaaaaaaaaa");
		dto.setMemberCode("111101111");
		userhabitService.save(dto);
		System.out.println(dto.getId());*/
	}

	@Override
	public void testUpdate() {
/*		UserhabitDTO dto = new UserhabitDTO();
		dto.setBankCode("aaaa");
		dto.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		dto.setPayway(11);
		dto.setRemark("aaaaaaRRRRRaaaa");
		System.out.println(dto.getId());
		userhabitService.update(dto);*/
	}

}