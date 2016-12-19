/**
 *  File: GatewayRequestServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-19   Terry Ma    Create
 *
 */
package com.pay.app.service.gateway.impl;

import java.util.Map;

import com.pay.app.dao.gateway.GatewayRequestDAO;
import com.pay.app.dto.gateway.DealCountDto;
import com.pay.app.dto.gateway.DealRequestDTO;
import com.pay.app.model.DealRequest;
import com.pay.app.service.gateway.GatewayRequestService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class GatewayRequestServiceImpl implements GatewayRequestService {

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	/**
	 * 根据网关交易号查询网关请求日志.
	 * 
	 * @param serialNo
	 * @return
	 */
	public DealRequestDTO queryDealRequestBySerialNo(final String serialNo) {

		GatewayRequestDAO gatewayRequestDAO = (GatewayRequestDAO) mainDao;
		DealRequest dealRequest = gatewayRequestDAO
				.queryDealRequestBySerialNo(serialNo);
		return BeanConvertUtil.convert(DealRequestDTO.class, dealRequest);
	}

	@Override
	public DealCountDto queryDealCountByMemberCodeAndType(String memberCode,
			Integer orderCode) {

		GatewayRequestDAO gatewayRequestDAO = (GatewayRequestDAO) mainDao;
		Map result = gatewayRequestDAO.queryDealCountByMemberCodeAndType(
				memberCode, orderCode);

		if (null != result && !result.isEmpty()) {
			DealCountDto dealCountDto = new DealCountDto();
			dealCountDto.setTotalCount(Integer.parseInt(String.valueOf(result
					.get("TOTALCOUNT"))));
			dealCountDto.setTotalAmount(Long.valueOf(String.valueOf(result
					.get("TOTALAMOUNT"))));
			return dealCountDto;
		} else {
			return null;
		}
	}
}
