/**
 *  File: SubscribeServiceTest.java
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
import com.pay.app.dto.SubscribeDTO;
import com.pay.app.service.messagebox.SubscribeService;

/**
 * 
 */
public class SubscribeServiceTest extends AbstractTestNG{
	
	@Resource(name="app-subscribeService")
	private SubscribeService subscribeService;

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
		SubscribeDTO dto = new SubscribeDTO();
		dto.setContext("aaaaa");
		dto.setCreationDate(new Timestamp(System.currentTimeMillis()));
		dto.setNoticeMode(1);
		dto.setStatus(1);
		dto.setType(2);
		System.out.println(dto.getId());
		subscribeService.create(dto);
	}

	@Override
//	@Test
	public void testUpdate() {
		SubscribeDTO dto = new SubscribeDTO();
		dto.setContext("aaaaa");
		dto.setCreationDate(new Timestamp(System.currentTimeMillis()));
		dto.setNoticeMode(1);
		dto.setStatus(1);
		dto.setType(2);
		System.out.println(dto.getId());
		subscribeService.update(dto);
	}

}