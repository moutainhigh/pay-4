/**
 *  File: BanklimitServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.test;

import javax.annotation.Resource;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.app.dto.BanklimitDTO;
import com.pay.app.service.limitconfig.BanklimitService;

/**
 * 
 */
public class BanklimitServiceTest extends AbstractTestNG {

	@Resource(name = "app-banklimitService")
	private BanklimitService banklimitService;

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

	//@Override
	@Test
	public void testSave() {

		BanklimitDTO dto = new BanklimitDTO();
		dto.setBankCode("cmb");
		dto.setBankType(1);
		dto.setCardType(1);
		dto.setDayLimit(1000L);
		dto.setDescription("ccccc");
		dto.setSingleLimit(1000L);
		dto.setStatus(1);
		banklimitService.create(dto);
		System.out.println(dto.getId());
	}

	@Override
	//@Test
	public void testUpdate() {
		BanklimitDTO dto = new BanklimitDTO();
		dto.setBankCode("ddddd");
		dto.setBankType(1);
		dto.setCardType(1);
		dto.setDayLimit(1000L);
		dto.setDescription("ccccc");
		dto.setSingleLimit(1000L);
		dto.setStatus(1);
		banklimitService.update(dto);
	}

}