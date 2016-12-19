/**
 *  File: MessageContextServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import javax.annotation.Resource;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.app.dto.MessageContextDTO;
import com.pay.app.service.messagebox.MessageContextService;

/**
 * 
 */
public class MessageContextServiceTest extends AbstractTestNG {

	@Resource(name = "app-messageContextService")
	private MessageContextService messageContextService;

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
		MessageContextDTO dto = new MessageContextDTO();
		dto.setContext("ddddd");
		
		messageContextService.messageContext(dto);
		System.out.println(dto.getId());
	}

	@Override
//	@Test
	public void testUpdate() {
		MessageContextDTO dto = new MessageContextDTO();
		dto.setContext("tttttttttttttt");
		System.out.println(dto.getId());
		messageContextService.update(dto);

	}

}