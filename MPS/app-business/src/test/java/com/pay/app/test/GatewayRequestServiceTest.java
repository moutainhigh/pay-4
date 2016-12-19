/**
 *  File: GatewayRequestServiceTest.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-20   Terry Ma    Create
 *
 */
package com.pay.app.test;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.common.helper.OrderStatus;
import com.pay.app.dto.gateway.DealCountDto;
import com.pay.app.dto.gateway.DealRequestDTO;
import com.pay.app.service.gateway.GatewayRequestService;

/**
 * 
 */
public class GatewayRequestServiceTest extends AbstractTestNG {

	@Resource(name = "app-gatewayRequestService")
	private GatewayRequestService gatewayRequestService;

	@Override
	public void testDeleteById() {
		// TODO Auto-generated method stub

	}

	@Override
	// @Test
	public void testFindById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testLoadAll() {
		// TODO Auto-generated method stub

	}

	@Override
	// @Test
	public void testSave() {

		DealRequestDTO gatewayRequest = new DealRequestDTO();
		gatewayRequest
				.setCreationTime(new Timestamp(System.currentTimeMillis()));
		gatewayRequest.setPartner("APP");
		gatewayRequest.setSerialNo("");
		gatewayRequest.setStatus(OrderStatus.INIT.getValue());
		gatewayRequest.setOrderCode(101);
		// gatewayRequestService.save(gatewayRequest);
		System.out.println(gatewayRequest.getId());
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testFindByMemberCode() {

		String memberCode = "1000000012";
		DealCountDto result = gatewayRequestService
				.queryDealCountByMemberCodeAndType(memberCode, 723);

		Assert.assertNotNull(result);

		System.out.println(result.getTotalAmount());
		System.out.println(result.getTotalCount());
	}
}