/**
 *  File: MessageSendServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.pay.app.AbstractTestNG;
import com.pay.app.service.sms.SmsService;
/**
 * 短信通道TEST
 */
public class SmsServiceTest extends AbstractTestNG{
	
	@Resource(name="app-smsService")
	private SmsService smsService;

	@Override
	public void testDeleteById() {
	}

	@Override
	public void testFindById() {
		
	}

	@Override
	public void testLoadAll() {
		
	}

	@Override
	//@Test
	public void testSave() {
		String memberCode = "001";
		String origin = "app";
		String userId = "001";
		List<String> mobiles = new ArrayList<String>();
		mobiles.add("15921726702");
		System.out.println(smsService.sendSms(memberCode, origin, userId, mobiles,2));
	}

	@Override
//	@Test
	public void testUpdate() {
	}
}
