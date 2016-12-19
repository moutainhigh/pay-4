package com.pay.poss.refund.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.refund.model.RefundHandworkBatchDTO;
import com.pay.poss.refund.model.RefundProcessResultDTO;

/**
 * 手工生成批次文件
 * 
 * @Description
 * @project poss-refund
 * @file RefundHandworkService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-19 Volcano.Wu Create
 */
public interface RefundHandworkService {
	
	/**
	 * 查询充退数据
	 * @param page
	 * @param params
	 * @return Page<RefundHandworkBatchDTO>
	 */
	Page<RefundHandworkBatchDTO> search(Page<RefundHandworkBatchDTO> page, Map<String, Object> params);
	
	/**
	 * 生成批次文件
	 * @param workorders
	 * @return Map<String,Object>
	 */
	Map<String,Object> handworkBatch(List<String> workorders);
	
	/**
	 * 查询批次充退数据
	 * @param page
	 * @param params
	 * @return
	 */
	Page<RefundProcessResultDTO> processresultList(Page<RefundProcessResultDTO> page, Map<String, Object> params);
	
	/**
	 * 查询手工充退处理数据
	 * @param page
	 * @param params
	 * @return
	 */
	Page<RefundProcessResultDTO> checkprocessresultList(Page<RefundProcessResultDTO> page, Map<String, Object> params);
	
	/**
	 * 充退人工审核成功处理
	 * @param idsStr
	 * @return
	 */
	Boolean processresultSuccess(String idsStr);
	
	/**
	 * 充退人工审核失败处理
	 * @param idsStr
	 * @return
	 */
	Boolean processresultFailure(String idsStr);
	
	/**
	 * 统计批次充退信息
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String,Object> queryProcessResultSumInfo(Map<String, Object> params);
	
	/**
	 * 充退结果确认
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String,Object> confirmRefundInfo(Map<String,Object> params);
	
	/**
	 * 充退结果退回
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String,Object> backRefundInfo(Map<String,Object> params);
}
