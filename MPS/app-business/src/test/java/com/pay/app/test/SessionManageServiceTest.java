/**
 *  File: SessionManageServiceTest.java
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
import com.pay.app.dto.SessionManageDTO;
import com.pay.app.service.user.SessionManageService;

/**
 * 
 */
public class SessionManageServiceTest extends AbstractTestNG{
	
	@Resource(name="app-sessionManageService")
	private SessionManageService sessionManageService;

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
		SessionManageDTO dto = new SessionManageDTO();
		dto.setAuthenticated(1);
		dto.setLastVisit(new Timestamp(System.currentTimeMillis()));
		dto.setRid(111L);
		dto.setUserName("aaaaa");
		System.out.println(dto.getId());
		
		sessionManageService.create(dto);
	}

	@Override
//	@Test
	public void testUpdate() {
		SessionManageDTO dto = new SessionManageDTO();
		dto.setAuthenticated(1);
		dto.setLastVisit(new Timestamp(System.currentTimeMillis()));
		dto.setRid(111L);
		dto.setUserName("aaaaffa");
		System.out.println(dto.getId());
		
		sessionManageService.update(dto);
	}

}