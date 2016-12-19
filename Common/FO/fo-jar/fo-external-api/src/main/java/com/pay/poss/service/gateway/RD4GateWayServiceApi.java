/** @Description 
 * @project 	poss-refund
 * @file 		RD4GateWayServiceApi.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.gateway;

import java.util.List;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.dto.fi.DepositQueryParamDTO;

/**
 * <p>
 * 对外调用gateway服务接口
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public interface RD4GateWayServiceApi {
	/**
	 * 查询充值信息
	 * 
	 * @param depositQueryParamDTO
	 * @return List<DepositQueryDetalisDTO>
	 */
	//List<DepositOrderQueryDetailDTO> queryDeposits(DepositQueryParamDTO depositQueryParamDTO) throws PossUntxException;

	/**
	 * 查询充值信息总记录数
	 * 
	 * @param depositQueryParamDTO
	 * @return
	 * @throws Exception
	 */
	Integer queryDepositsCount(DepositQueryParamDTO depositQueryParamDTO)
			throws Exception;
}
