/**
 *  File: LinkerServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;
import javax.annotation.Resource;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.service.linker.LinkerService;

/**
 * 
 */
public class LinkerServiceTest extends AbstractTestNG {

	@Resource(name = "base-linkerService")
	private LinkerService linkerService;

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
		LinkerDTO dto = new LinkerDTO();
		dto.setDescription("aaa");
		dto.setGroupId(11);
		dto.setJoinDate(new Timestamp(System.currentTimeMillis()));
		dto.setLinkerId(11L);
		dto.setLinkerName("aaaa");
		dto.setRemark("aaa");
		dto.setStatus(1);
		dto.setMemberCode("1010");
		
		linkerService.create(dto);
		System.out.println(dto.getId());
		Assert.assertNotNull(dto.getId());
	}

	@Override
//	@Test
	public void testUpdate() {
		LinkerDTO dto = new LinkerDTO();
		dto.setDescription("aaa");
		dto.setGroupId(11);
		dto.setJoinDate(new Timestamp(System.currentTimeMillis()));
		dto.setLinkerId(11L);
		dto.setLinkerName("adddddaaa");
		dto.setRemark("aadffffffa");
		dto.setStatus(111);
		System.out.println(dto.getId());
		linkerService.update(dto);
	}

}