/**
 *  File: AnnouncementServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-14   terry_ma     Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.dto.AnnouncementDTO;
import com.pay.app.service.announcement.AnnouncementService;

public class AnnouncementServiceTest extends AbstractTestNG {

	@Resource(name = "app-announcementService")
	private AnnouncementService announcementService;

	 @Test(groups = { "announcement" })
	public void testSave() {

		System.out.println("testSave");

		AnnouncementDTO dto = new AnnouncementDTO();
		dto.setAuthor("demo5");
		dto.setDisplaySort(5);
		dto.setEndTime(new Timestamp(System.currentTimeMillis()));
		dto.setMessage("好啊！好啊！好啊！好啊！好啊！");
		dto.setStartTime(new Timestamp(System.currentTimeMillis()));
		dto.setSubject("pay5");

		dto = (AnnouncementDTO) announcementService.create(dto);
		System.out.println("primary key:" + dto.getId());
		Assert.assertNotNull(dto.getId());
	}

//	 @Test(groups = { "announcement" })
	public void testUpdate() {
		System.out.println("announcementService = " + announcementService);

		AnnouncementDTO dto = new AnnouncementDTO();
		dto.setAuthor("terry_ma");
		dto.setDisplaySort(1);
		dto.setEndTime(new Timestamp(System.currentTimeMillis()));
		dto.setMessage("mandy");
		dto.setStartTime(new Timestamp(System.currentTimeMillis()));
		dto.setSubject("mandy");
		dto.setId(1L);

		announcementService.update(dto);

	}

	// @Test(threadPoolSize = 10, invocationCount = 5, timeOut = 1000, groups =
	// { "multiple" })
	 //@Test(groups = { "announcement" })
	public void testDeleteById() {
		System.out.println("multiple");
//		announcementService.deleteById("4152230b-9555-458a-a000-c0d2088eb461");
	}

	 @Test(groups = { "announcement" })
	public void testFindById() {

		AnnouncementDTO dto = (AnnouncementDTO) announcementService
				.findById(1L);

		System.out.println(dto.getId()
				+ "primary key%%$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Assert.assertNotNull(dto);
	}
	
	@SuppressWarnings("unchecked")
	//@Test
	public void testLoadAll() {

	}
}
