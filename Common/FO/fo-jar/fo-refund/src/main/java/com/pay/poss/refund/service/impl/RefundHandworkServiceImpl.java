package com.pay.poss.refund.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.BatchInfo;
import com.pay.poss.refund.model.RefundBatchInfoDTO;
import com.pay.poss.refund.model.RefundHandworkBatchDTO;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundProcessResultDTO;
import com.pay.poss.refund.schedule.StartTask;
import com.pay.poss.refund.service.RefundHandworkService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 手工生成批次文件
 * 
 * @Description
 * @project poss-refund
 * @file RefundHandworkServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-19 Volcano.Wu Create
 */
public class RefundHandworkServiceImpl extends BaseServiceImpl implements
		RefundHandworkService {

	private BaseDAO daoService;
	private String queueName;
	private NotifyFacadeService notifyFacadeService;
	private OrderCallBackService orderCallBackService; // 充退请求处理记账完成后回调服务
	private AccountingService accountingServiceSucc; // 充退请求处理成功记账服务
	private AccountingService accountingServiceFail; // 充退请求处理失败记账服务

	public void setOrderCallBackService(OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setAccountingServiceSucc(AccountingService accountingServiceSucc) {
		this.accountingServiceSucc = accountingServiceSucc;
	}

	public void setAccountingServiceFail(AccountingService accountingServiceFail) {
		this.accountingServiceFail = accountingServiceFail;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	/**
	 * @param queueName
	 *            the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	// 条件查询分页
	public Page<RefundHandworkBatchDTO> search(
			Page<RefundHandworkBatchDTO> page, Map<String, Object> params) {
		return daoService.findByQuery(
				"refund.batch.selectRefundHandworkBatchDTO", page, params);
	}

	// 生成批次文件,返回结果
	public Map<String, Object> handworkBatch(List<String> workorders) {
		Map<String, Object> result = new HashMap<String, Object>();
		String batchNum = StartTask.getInstance().manualBuildBatch(workorders);
		RefundBatchInfoDTO refundBatchInfoDTO = (RefundBatchInfoDTO) daoService
				.findObjectByCriteria(
						"refund.batch.calcMasterInfoByBatchNumWithoutBank",
						batchNum);
		BatchInfo batchInfo = (BatchInfo) daoService.findObjectByCriteria(
				"refund.batch.queryBatchInfoByBatchNum", batchNum);
		result.put("batchNum", batchNum);
		result.put("batchName", batchInfo != null ? batchInfo.getBatchName()
				: "");
		result.put(
				"totalAmount",
				refundBatchInfoDTO != null ? refundBatchInfoDTO
						.getTotalAmount() : "");
		result.put("totalCount",
				refundBatchInfoDTO != null ? refundBatchInfoDTO.getTotalCount()
						: "");
		result.put("updatetime", batchInfo != null ? batchInfo.getUpdatetime()
				: "");
		return result;
	}

	// 成功
	public Boolean processresultSuccess(String idsStr) {
		Boolean result = false;
		String[] ids = idsStr.split(":");
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = null;
		for (String id : ids) {
			params = new HashMap<String, Object>();
			params.put("detailKy", id);
			params.put("status", RefundConstants.REFUND_HANDLE_STATUS_102); // 审核成功
			paramList.add(params);
		}
		int count = daoService.updateBatch("refund.batch.updateRefundOrderD",
				paramList);
		result = (0 != count) ? true : false;
		return result;
	}

	// 失败
	public Boolean processresultFailure(String idsStr) {
		Boolean result = false;
		String[] parsStr = idsStr.split(":");
		String[] pars = null;
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = null;

		for (String str : parsStr) {
			pars = str.split("=");
			params = new HashMap<String, Object>();
			params.put("detailKy", pars[0]);
			params.put("errorTip", pars.length > 1 ? pars[1] : null);
			params.put("status", RefundConstants.REFUND_HANDLE_STATUS_103); // 审核失败
			paramList.add(params);
		}
		int count = daoService.updateBatch("refund.batch.updateRefundOrderD",
				paramList);
		result = (0 != count) ? true : false;
		return result;
	}

	// 查询充退批次结果List
	public Page<RefundProcessResultDTO> processresultList(
			Page<RefundProcessResultDTO> page, Map<String, Object> params) {
		return daoService.findByQuery(
				"refund.batch.selectRefundProcessResultDTO", page, params);
	}

	// 统计充退批次结果
	public Map<String, Object> queryProcessResultSumInfo(
			Map<String, Object> params) {
		return (Map<String, Object>) daoService.findObjectByCriteria(
				"refund.batch.selectRefundProcessResultSum", params);
	}

	// 复核手工处理充退数据List
	public Page<RefundProcessResultDTO> checkprocessresultList(
			Page<RefundProcessResultDTO> page, Map<String, Object> params) {
		return daoService.findByQuery(
				"refund.batch.selectRefundProcessResultDTO", page, params);
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.RefundHandworkService#backRefundInfo(java
	 * .util.Map)
	 */
	@Override
	public Map<String, Object> backRefundInfo(Map<String, Object> params) {
		params.put("handleType", "back");
		params.put("status", RefundConstants.REFUND_HANDLE_STATUS_101);
		daoService.update("refund.batch.updateRefundOrderDStatus", params);
		params.put("resultMsg", "处理成功!");
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.refund.service.RefundHandworkService#confirmRefundInfo(java
	 * .util.Map)
	 */
	@Override
	public Map<String, Object> confirmRefundInfo(Map<String, Object> params) {
		// 查询充退信息
		List<RefundOrderM> orderList = daoService.findByQuery(
				"refund.batch.queryRefundDetailInfo", params);

		List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> paramMap = null;
		// 记账
		for (RefundOrderM mDto : orderList) {
			mDto.setOverStatus("0");
			mDto.setHandler(StringUtil.null2String(params.get("userId")));
			String jsonStr = JSonUtil.toJSonString(mDto);
			
			//修改为同步
			this.processAccounting(mDto);
			
			//notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,queueName));
			paramMap = new HashMap<String, Object>();
			paramMap.put("detailKy", mDto.getDetailKy());
			paramMap.put("bankCode", mDto.getBankCode());
			paramMap.put("batchNum", mDto.getBatchNum());
			paramMap.put("status", RefundConstants.FILE_STATUS_HAND_DISPOSE);
			paramsList.add(paramMap);
		}

		// 更新批次文件状态
		daoService.updateBatch("refund.batch.updateBatchFileInfoStatus",
				paramsList);

		params.put("resultMsg", "处理成功!");
		return params;
	}
	
	/**
	 * @param mDto
	 */
	private void processAccounting(RefundOrderM mDto) throws PossException {
		HandlerParam param = new HandlerParam();
		param.setBaseOrderDto(mDto);
		param.setOrderStatus(mDto.getStatus());// 订单状态
		
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(mDto.getApplyAmount());
		accountingDto.setOrderAmount(mDto.getApplyAmount());
		accountingDto.setBankCode(mDto.getBankCode());
		//accountingDto.setBusinessType(PayForEnum.INCOME_BACK.getCode());
		accountingDto.setOrderId(mDto.getDetailKy());
		accountingDto.setPayee(mDto.getMemberCode());
		accountingDto.setBankCode(mDto.getBankCode());
		param.setAccountingDto(accountingDto);
		if (WithdrawOrderStatus.SUCCESS.getValue() == mDto.getStatus().intValue()) {
			param.setWithdrawBusinessType(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_SUCC_PERSON.getBusinessType());//
			orderCallBackService.processOrderRdTx(param, accountingServiceSucc);
		} else if (WithdrawOrderStatus.FAIL.getValue() == mDto.getStatus().intValue()) {
			param.setWithdrawBusinessType(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON.getBusinessType());//
			orderCallBackService.processOrderRdTx(param, accountingServiceFail);
		}
	}
}
