/**
 *  <p>File: ExternalFacadeDao.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.dto.fi.IncomeDetailParaDTO;
import com.pay.poss.dto.fi.PayOnlineDetailDTO;
import com.pay.poss.dto.fi.QueryDepositDetalisDTO;
import com.pay.poss.dto.fi.RefundOnlineDTO;

public interface ExternalFacadeDao {

	/**
	 * 查询充值流水明细
	 * 
	 * @param modelParamMap
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public List<QueryDepositDetalisDTO> queryDepositDetails(
			Map<String, Object> modelParamMap, int pageSize, int pageNo)
			throws Exception;

	/**
	 * 
	 * @param modelParamMap
	 * @return
	 * @throws Exception
	 */
	public Integer queryDepositDetailsCount(Map<String, Object> modelParamMap)
			throws Exception;

	public Map<String, String> queryAllOrgName();

	public List<PayOnlineDetailDTO> findByTradeOrderInfoId(String fiOrderId);

	public List<RefundOnlineDTO> findByRefundOrderInfoId(
			final String refundOrderInfoId);
	
	/**
	 * 根据入参查询收款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Page<Map<String,Object>> queryIncomeDetailList(
			IncomeDetailParaDTO incomeDetailParaDTO, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询单笔收款明细
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Map<String,Object> querySingleIncomeDetail(Map<String,Object> map);
}
