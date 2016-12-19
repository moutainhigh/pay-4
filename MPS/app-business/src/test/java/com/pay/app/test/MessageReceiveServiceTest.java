/**
 *  File: MessageReceiveServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.service.messagebox.MessageReceiveService;

/**
 * 
 */
public class MessageReceiveServiceTest extends AbstractTestNG{

	@Resource(name="app-messageReceiveService")
	private MessageReceiveService messageReceiveService;
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
		
/*		MessageReceiveDTO dto = new MessageReceiveDTO();
		dto.setId(new Long(123));
		//dto.setRecvId("bb");
		dto.setReadFlag(1);
		dto.setStatus(1);
		dto.setReadTime(new Timestamp(System.currentTimeMillis()));
		dto.setTitle("aa");
		dto.setMessageContextId(new Long(123));
		dto.setUserId("memercode");
		dto.setSendTime(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(dto.getId());
		messageReceiveService.insertReceive(dto);
		dto = (MessageReceiveDTO) messageReceiveService.save(dto);
		System.out.println(dto.getId());*/
	}

	@Override
	//@Test
	public void testUpdate() {
		MessageReceiveDTO dto = new MessageReceiveDTO();
//		dto.setId("2cf4c15c-4247-4eba-9673-5d647e1cb041");
		dto.setReadFlag(1);
		dto.setReadTime(new Timestamp(System.currentTimeMillis()));
		dto.setUserId("memercode");
		dto.setStatus(1);
		
		System.out.println(dto.getId());
		
		messageReceiveService.update(dto);
	}
	
	//@Test
	public void testDel(){
		MessageReceiveDTO dto = new MessageReceiveDTO();
		dto.setId(new Long(15));
		dto.setUserId("memercode");
		dto.setReadFlag(1);
		dto.setStatus(1);
		dto.setReadTime(new Timestamp(System.currentTimeMillis()));
		dto.setTitle("aa");
		dto.setMessageContextId(new Long(19));
		dto.setSendTime(new Timestamp(System.currentTimeMillis()));
		messageReceiveService.delMessageReceive(dto);
	}
	
}