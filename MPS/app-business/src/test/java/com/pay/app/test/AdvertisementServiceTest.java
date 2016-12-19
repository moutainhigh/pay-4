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

import javax.annotation.Resource;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.pay.app.AbstractTestNG;
import com.pay.app.dto.AdvertisementDTO;
import com.pay.app.service.announcement.AdvertisementService;

public class AdvertisementServiceTest extends AbstractTestNG {

	//@Resource(name = "app-advertisementService")
	private AdvertisementService advertisementService;

	//@Test
	public void testSave() {

		AdvertisementDTO dto = new AdvertisementDTO();
		dto.setAdvtype(1);
		dto.setAvailable(1);
		dto.setCode("001");
		dto.setEndTime(new Timestamp(System.currentTimeMillis()));
		dto.setStartTime(new Timestamp(System.currentTimeMillis()));
		dto.setParameters("test");
		dto.setRemark("testtest");
		dto.setSort(0);
		dto.setTargets(1);
		dto.setTitle("this is a test record.");

	//	dto = (AdvertisementDTO) advertisementService.save(dto);
		//System.out.println(dto.getId());
	}

	// TODO
	//@Test(groups = { "announcement" })
	public void testUpdate() {
		// System.out.println("advertisementService = " + advertisementService);

		AdvertisementDTO dto = new AdvertisementDTO();
		dto.setAdvtype(1);
		dto.setAvailable(1);
		dto.setCode("001");
		dto.setEndTime(new Timestamp(System.currentTimeMillis()));
		dto.setStartTime(new Timestamp(System.currentTimeMillis()));
		dto.setParameters("TTT");
		dto.setRemark("KKKKKK");
		dto.setSort(0);
		dto.setTargets(1);
		dto.setTitle("YYYYYY");
		dto.setId(21L);

		//boolean result = advertisementService.update(dto);

		//Assert.assertTrue(result);
	}

	// @Test(groups = { "announcement" })
	public void testDeleteById() {
		System.out.println("multiple");
		 //advertisementService.deleteById(22L);
	}

	@Test(groups = { "announcement" })
	public void testFindById() {

		//AdvertisementDTO dto = (AdvertisementDTO) advertisementService
			//	.findById(22L);

		//Assert.assertNotNull(dto);
	}

	 @Test
	public void testLoadAll() {

		 //advertisementService.loadAll();
	}
}