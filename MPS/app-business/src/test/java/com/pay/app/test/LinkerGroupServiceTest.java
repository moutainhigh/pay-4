/**
 *  File: LinkerGroupServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.base.dto.LinkerGroupDTO;
import com.pay.base.service.linker.LinkerGroupService;

/**
 * 
 */
public class LinkerGroupServiceTest extends AbstractTestNG{
	
	@Resource(name="base-linkerGroupService")
	private LinkerGroupService linkerGroupService;

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
		LinkerGroupDTO dto = new LinkerGroupDTO();
		dto.setGroupName("testName");
		linkerGroupService.create(dto);
		Assert.assertNotNull(dto.getId());
	}

	@Override
	//@Test
	public void testUpdate() {
		LinkerGroupDTO dto = new LinkerGroupDTO();
		dto.setGroupName("TTTTTTT");
		linkerGroupService.update(dto);
	}

}