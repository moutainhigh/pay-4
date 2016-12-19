/**
 *  File: MessageSendServiceTest.java
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
import com.pay.app.dto.MessageContextDTO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.dto.MessageSendDTO;
import com.pay.app.service.messagebox.MessageSendService;

/**
 * 
 */
public class MessageSendServiceTest extends AbstractTestNG{
	
	@Resource(name="app-messageSendService")
	private MessageSendService messageSendService;

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

		for(int i=0;i<6;i++){
			MessageContextDTO mcd=new MessageContextDTO();
			
			mcd.setId(new Long(1789));
			mcd.setContext("测试内容"+i);
			
			MessageSendDTO dto = new MessageSendDTO();
			
			dto.setSendId("1000000081");//自己的membercode
			dto.setSendTime(new Timestamp(System.currentTimeMillis()));//时间
			dto.setStatus(1);//状态
			dto.setTitle("让你删"+i);//标题
			dto.setType(2);//类型
			dto.setMessageContextId(mcd.getId());//关联的内容ID
			dto.setToUserId("1000000012");//收信人的membercode
			
			MessageReceiveDTO mrd =  new MessageReceiveDTO();
			mrd.setId(Long.valueOf(123));
			mrd.setReadFlag(0);
			mrd.setStatus(1);
			mrd.setUserId("1000000012");
			mrd.setReadTime(new Timestamp(System.currentTimeMillis()));
			
			mrd.setSendTime(dto.getSendTime());
			mrd.setMessageContextId(mcd.getId());
			mrd.setTitle(dto.getTitle());
			
			messageSendService.sendMessage(dto,mcd,mrd);
		}
	}

	@Override
//	@Test
	public void testUpdate() {
		MessageSendDTO dto = new MessageSendDTO();
		dto.setSendId("aaa");
		dto.setSendTime(new Timestamp(System.currentTimeMillis()));
		dto.setStatus(1);
		dto.setTitle("aadfdfdafdsa");
		dto.setType(2);
		dto.setMessageContextId(new Long(123));
		messageSendService.update(dto);
	}

}
