/**
 *  <p>File: DepositQueryServiceFacade.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.service.gateway;

import java.util.List;

import com.pay.poss.dto.fi.DepositQueryDetalisDTO;
import com.pay.poss.dto.fi.DepositQueryParamDTO;

public interface DepositQueryServiceFacade {

	/**
	 * 查询充值信息--提供给fo调用的查询充值记录
	 * @author gavin_song修改
	 * @param depositQueryParamDTO
	 * @return List<DepositQueryResultDTO>
	 */
	List<DepositQueryDetalisDTO>  queryDeposits(
			DepositQueryParamDTO depositQueryParamDTO) throws Exception;
	
	/**
	 * 查询充值信息总记录数--提供给fo调用的查询充值记录
	 * @author gavin_song修改
	 * @param depositQueryParamDTO
	 * @return
	 * @throws DepositUnTxException
	 * @throws Exception
	 */
	Integer  queryDepositsCount(
			DepositQueryParamDTO depositQueryParamDTO) throws Exception;
}
