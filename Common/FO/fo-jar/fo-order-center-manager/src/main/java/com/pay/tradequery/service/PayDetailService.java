package com.pay.tradequery.service;

import java.util.Map;

import com.pay.inf.dao.Page;

/**
 * @author Sandy
 * @Date 2011-7-22
 * @Description 付款明细Service
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public interface PayDetailService {

	/**
	 * 查询单笔付款List
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String, Object>> queryPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize);

	/**
	 * 查询批量付款List
	 * 
	 * @param mapT
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String, Object>> queryBatchPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize);

	/**
	 * 查询单笔付款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Map<String, Object> querySinglePayDetail(Map<String, Object> map);
	
	/**
	 * 查询批量付款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Map<String, Object> querySinglePayBatchDetail(Map<String, Object> map);
	
	/**
	 * 来自于账户的收款记录
	 * 
	 * @param map
	 * @return Map
	 */
	public Page<Map<String, Object>> queryReceiptRecordFormAccList(
			Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询个人收支明细,包括网关交易和付款到账户和卡交易,收支双向
	 * 
	 * @param map
	 * @return Map
	 */
	public Page<Map<String, Object>> queryPersonTradeDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询个人提现明细
	 * 
	 * @param map
	 * @return Map
	 */
	public Page<Map<String, Object>> queryPersonWithdrawDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询个人交易明细 
	 * 
	 * @param Map
	 * @return Map
	 */
	public Map<String, Object> queryPersonSingleTradeDetail(Map<String, Object> map);
	
	/**
	 * BSP提现列表查询
	 * @param map
	 * @return
	 */
	public Page<Map<String, Object>> queryWithdrawSummaryFromBsp(Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * BSP提现详情查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryWithdrawDetailFromBsp(Map<String, Object> map);
	
	/**
	 * 资金调拨详情查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryFundAllotDetail(Map<String, Object> map);
	
	/**
	 * 提现列表查询
	 * @param map
	 * @return
	 */
	public Page<Map<String, Object>> queryWithdrawList(Map<String, Object> map, Integer pageNo, Integer pageSize);
	
	/**
	 * 提现详情查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryWithdrawDetail(Map<String, Object> map);
	
	
	/**
	 * 支付付款凭证
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryPayToBankCertificate(Map<String, Object> map); 
}
