/** @Description 
 * @project 	poss-refund
 * @file 		RefundManageService.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		sunsea.li		Create 
 */
package com.pay.poss.refund.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.refund.model.RechargeRecordDto;
import com.pay.poss.refund.model.RefundDetailInfoDTO;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.WebQueryRefundDTO;

/**
 * <p>
 * 充退管理服务接口类
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-8-25
 * @see
 */
public interface RefundManageService {

	/**
	 * 查询充值列表信息
	 * 
	 * @param resultPage
	 *            分页相关参数
	 * @param webQueryRefundDTO
	 *            查询条件
	 * @return
	 */
	public Map<String, Object> queryRechargeInfo(
			Page<RechargeRecordDto> resultPage,
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 充退批量申请处理(需要事务处理)
	 * 
	 * @param mDto
	 * @return
	 */
	public Map<String, Object> handerRefundApplyRdTx(RefundOrderM mDto,
			WebQueryRefundDTO dto) throws PossException;

	/**
	 * 充退批量申请(处理前台发起的商户退款)
	 * 
	 * @param mDto
	 * @return
	 */
	public void handerApplyResponseFromFiRdTx(RefundOrderM mDto,
			RefundOrderD dDto) throws PossException;

	/**
	 * 查询充退列表信息
	 * 
	 * @param resultPage
	 *            分页相关参数
	 * @param webQueryRefundDTO
	 *            查询条件
	 * @return
	 */
	public Map<String, Object> queryRefundInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 查询充退明细信息
	 * 
	 * @param webQueryRefundDTO
	 *            查询条件
	 * @return
	 */
	public Map<String, Object> queryRefundInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 充退单笔审核处理
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return
	 */
	public Map<String, Object> handerRefundAuditSingle(
			Map<String, Object> params);

	/**
	 * 充退批量审核处理
	 * @param channelOrders 
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return
	 */
	public Map<String, Object> handerRefundAuditBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO, List<Map> channelOrders);

	/**
	 * 查询充退审核信息
	 * 
	 * @param resultPage
	 * @param webQueryRefundDTO
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryRefundReAuditInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 处理充退复核
	 * @param channelOrders 
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return Map<String,Object>
	 */
	public Map<String, Object> handeRefundReAuditBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO, List<Map> channelOrders);

	/**
	 * 查询充退审核详细信息
	 * 
	 * @param webQueryRefundDTO
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryRefundReAuditInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 查询充退复核信息
	 * 
	 * @param resultPage
	 * @param webQueryRefundDTO
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryRefundLiquidateInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 处理充退清结算
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return Map<String,Object>
	 */
	public Map<String, Object> handeRefundLiquidateBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 查询充退复核详细信息
	 * 
	 * @param webQueryRefundDTO
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryRefundLiquidateInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 充退单笔 复核处理
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return
	 */
	public Map<String, Object> handerRefundReAuditSingle(
			Map<String, Object> params);

	/**
	 * 充退单笔 清算处理
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return
	 */
	public Map<String, Object> handerRefundLiquidateSingle(
			Map<String, Object> params);

	/**
	 * 查询审核信息
	 * 
	 * @param webQueryRefundDTO
	 * @return List<RefundWorkOrderAndM>
	 */
	public List<RefundDetailInfoDTO> queryAuditInfo(
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 查询复核信息
	 * 
	 * @param webQueryRefundDTO
	 * @return List<RefundWorkOrderAndM>
	 */
	public List<RefundDetailInfoDTO> queryReAuditInfo(
			WebQueryRefundDTO webQueryRefundDTO);

	/**
	 * 更新充退工单 订单回调订单状态更新接口
	 * 
	 * @param refundOrderM
	 *            充退工单
	 */
	public boolean updateRefundOrder(RefundOrderM order);

	/**
	 * 提供定时更新主表订单状态接口（供定时任务调用） liwei
	 * 
	 * @return
	 */
	public void updateOrderMStatusTask();
	/**
	 * 通过退款订单号查询网关订单号
	 * ***/
	public RefundOrderM queryTradeOrderNo(String refundOrderNo);
	
	/**
	 * 充退自动任务处理
	 * @param channelOrders 
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return
	 */
	public Map<String, Object> handeAutoRefundTaskBatch(WebQueryRefundDTO webQueryRefundDTO);

}
