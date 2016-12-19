/** @Description 
 * @project 	poss-refund
 * @file 		RD4GateWayServiceJar.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.gateway.impl;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.dto.fi.DepositQueryParamDTO;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.gateway.RD4GateWayServiceApi;

/**
 * <p>
 * 对外调用gateway服务接口 jar包依赖实现
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public class RD4GateWayServiceJar extends AbstractExternalAdapter implements
		RD4GateWayServiceApi {

	//private DepositQueryServiceFacade depositQueryServiceFacade;
	//private DepositOrderService depositQueryServiceFacade;

//	public List<DepositOrderQueryDetailDTO> queryDeposits(
//			DepositQueryParamDTO depositQueryParamDTO) throws PossUntxException {
//
//		List<DepositOrderQueryDetailDTO> depositQueryDetalisList = null;
//		try {
//			depositQueryDetalisList = this.depositQueryServiceFacade
//					.queryDeposits(depositQueryParamDTO);
//			log.debug(printLog(this, 2) + "depositQueryDetalisList :"
//					+ printObjToString(depositQueryDetalisList));
//		} catch (Exception e) {
//			log.error(printLog(this, 3) + "queryDeposits:" + e.getMessage());
//			throw new PossUntxException(printLog(this, 3) + "queryDeposits:"
//					+ e.getMessage(), ExceptionCodeEnum.ILLEGAL_PARAMETER);
//		}
//		return depositQueryDetalisList;
//	}

	@Override
	public Integer queryDepositsCount(DepositQueryParamDTO depositQueryParamDTO)
			throws Exception {

		Integer count = 0;
		try {
//			count = this.depositQueryServiceFacade
//					.queryDepositsCount(depositQueryParamDTO);
			log.debug(printLog(this, 2) + "count :" + printObjToString(count));
		} catch (Exception e) {
			log.error(printLog(this, 3) + "queryDepositsCount:"
					+ e.getMessage());
			throw new PossUntxException(printLog(this, 3)
					+ "queryDepositsCount:" + e.getMessage(),
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		return count;
	}

//	public void setDepositQueryServiceFacade(
//			DepositOrderService depositQueryServiceFacade) {
//		this.depositQueryServiceFacade = depositQueryServiceFacade;
//	}

}
