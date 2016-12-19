/** @Description 
 * @project 	poss-refund
 * @file 		RD4GateWayServiceMock.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.gateway.impl;

import com.pay.poss.dto.fi.DepositQueryParamDTO;
import com.pay.poss.service.gateway.RD4GateWayServiceApi;

/**
 * <p>
 * 对外调用gateway服务接口 Mock实现
 * </p>
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public class RD4GateWayServiceMock implements RD4GateWayServiceApi {

	/* (non-Javadoc)
	 * @see com.pay.poss.service.gateway.RD4GateWayServiceApi#queryDeposits(com.pay.gateway.dto.depositquery.DepositQueryParamDTO)
	 */
	

	/* (non-Javadoc)
	 * @see com.pay.poss.service.gateway.RD4GateWayServiceApi#queryDepositsCount(com.pay.gateway.dto.depositquery.DepositQueryParamDTO)
	 */
	@Override
	public Integer queryDepositsCount(DepositQueryParamDTO depositQueryParamDTO)
			throws Exception {
		return 8;
	}

}
