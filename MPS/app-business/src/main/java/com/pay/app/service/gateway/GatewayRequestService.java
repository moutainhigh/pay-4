/**
 *  File: GatewayRequestService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-19   Terry Ma    Create
 *
 */
package com.pay.app.service.gateway;

import com.pay.app.dto.gateway.DealCountDto;
import com.pay.app.dto.gateway.DealRequestDTO;

/**
 * 网关请求服务.
 */
public interface GatewayRequestService {

	/**
	 * 根据网关交易号查询网关请求日志.
	 * 
	 * @param serialNo
	 * @return
	 */
	DealRequestDTO queryDealRequestBySerialNo(final String serialNo);

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	DealCountDto queryDealCountByMemberCodeAndType(final String memberCode,
			final Integer orderCode);
}
