/**
 *  File: UserLogServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.dto.UserLogDTO;
import com.pay.base.service.user.UserLogService;

/**
 * 
 */
public class UserLogServiceTest extends AbstractTestNG {

	@Resource(name = "base-userLogService")
	private UserLogService userLogService;

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
//		UserLogDTO dto = new UserLogDTO();
//		dto.setLoginDate(new Timestamp(System.currentTimeMillis()));
//		dto.setLoginIp("192.168.12.23");
//		dto.setLogType(1);
		
		UserLogDTO userLogDTO = new UserLogDTO();
		userLogDTO.setMemberCode(Long.valueOf("10000000053"));
		userLogDTO.setActionUrl("TestUrl");
		userLogDTO.setLogType(new Integer(1));
		userLogDTO.setLoginDate(new Timestamp(new Date().getTime()));
		userLogDTO.setLoginIp("192.168.12.23");
		userLogDTO.setLoginName("pay@pay.com");
		userLogDTO.setName("pay");
		// TODO 获取浏览器信息
		// 浏览器设置默认值 2(ie7)
		userLogDTO.setBrowserVer("2");
		userLogService.create(userLogDTO);
		System.out.println(userLogDTO.getOperatorId());
	}

	@Override

	public void testUpdate() {
		UserLogDTO dto = new UserLogDTO();
		dto.setLoginDate(new Timestamp(System.currentTimeMillis()));
		dto.setLoginIp("192.168.12.23");
		dto.setLogType(1);
		System.out.println(dto.getLogId());
		userLogService.update(dto);
	}

}
