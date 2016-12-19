/** @Description 
 * @project 	poss-withdraw
 * @file 		WdReconcileResultServiceImpl.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-23		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.service.result.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.fo.order.common.OrderType;
import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dao.result.WdReconcileResultDao;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawRcResultSummaryDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawReconcileResultDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.result.WithdrawRcResultSummary;
import com.pay.fundout.withdraw.model.result.WithdrawReconcileResult;
import com.pay.fundout.withdraw.service.accountmdp.BatchSendAccountFacadeService;
import com.pay.fundout.withdraw.service.fileservice.HandBatchFileService;
import com.pay.fundout.withdraw.service.result.WdReconcileResultService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingEntryDetailDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;

/**
 * <p>
 * 对账结果文件Service
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-23
 * @see
 */
public class WdReconcileResultServiceImpl implements WdReconcileResultService {

	private BatchSendAccountFacadeService batchSendAccountFacadeService;
	private WithdrawAuditWorkOrderDao withdrawWorkDao;

	private transient Map<String, AccountingService> accountingServiceMap;
	private AccountQueryService accountQueryService;

	public void setAccountingServiceMap(Map<String, AccountingService> accountingServiceMap) {
		this.accountingServiceMap = accountingServiceMap;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	private HandBatchFileService handBatchFileService;

	public void setHandBatchFileService(HandBatchFileService handBatchFileService) {
		this.handBatchFileService = handBatchFileService;
	}

	public void setBatchSendAccountFacadeService(final BatchSendAccountFacadeService params) {
		this.batchSendAccountFacadeService = params;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	private QueryBatchFileDao queryBatchFileDao;

	public void setQueryBatchFileDao(QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}

	private WdReconcileResultDao wdReconcileResultDao;

	public void setWdReconcileResultDao(final WdReconcileResultDao wdReconcileResultDao) {
		this.wdReconcileResultDao = wdReconcileResultDao;
	}

	@Override
	public WithdrawRcResultSummaryDTO queryRcResultSummaryInfo(Map<String, Object> param) {
		WithdrawRcResultSummary withdrawRcResultSummary = wdReconcileResultDao.queryWdRcResultSummary(param);
		WithdrawRcResultSummaryDTO withdrawRcResultSummaryDTO = new WithdrawRcResultSummaryDTO();
		if (withdrawRcResultSummary != null) {
			BeanUtils.copyProperties(withdrawRcResultSummary, withdrawRcResultSummaryDTO);
		}
		return withdrawRcResultSummaryDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<WithdrawReconcileResultDTO> queryRcResultListInfo(Page<WithdrawReconcileResultDTO> page, Map<String, Object> param) {

		Page<WithdrawReconcileResult> pageService = new Page<WithdrawReconcileResult>();

		PageUtils.setServicePage(pageService, page);

		pageService = wdReconcileResultDao.queryWdRcResult(pageService, param);

		WithdrawReconcileResultDTO withdrawReconcileResultDTO = new WithdrawReconcileResultDTO();

		page.setResult((List<WithdrawReconcileResultDTO>) PageUtils.changePageList(pageService.getResult(), withdrawReconcileResultDTO, null));

		PageUtils.setServicePage(page, pageService);

		return page;
	}

	/**
	 * 退回导入
	 * 
	 * @param params
	 * @throws PossException
	 */
	public void refuseImportRdTx(Map<String, String> params) throws PossException {

		try {
			params.put("category", "2");

			wdReconcileResultDao.deleteWdImportFileRecord(params);
			wdReconcileResultDao.deleteWdImportFileResult(params);
			wdReconcileResultDao.deleteWdImportFile(params);
			// 更新批次文件信息表为已下载
			BatchFileInfo batchFileInfoParam = new BatchFileInfo();
			batchFileInfoParam.setBatchNum(params.get("batchNum"));
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("bankCodeRule", params.get("bankCode") + "_" + params.get("tradeType"));
			String flag = (String) queryBatchFileDao.findObjectByCriteria("fundoutBatch.fundout-batch-flow-query", info);
			if (flag == null || "0".equals(flag) || "2".equals(flag)) {
				batchFileInfoParam.setBatchFileStatus(3L);
			} else {
				batchFileInfoParam.setBatchFileStatus(8L);
			}
			batchFileInfoParam.setFileKy(Long.valueOf(params.get("fileKy")));
			boolean intStatusFetch = queryBatchFileDao.update("schedule.fo-UpdateBatchFileInfo43", batchFileInfoParam);
			if (!intStatusFetch) {
				log.error("FOPJ-975 单笔退回导入：批次文件状态4---->3失败batchNum:" + params.get("batchNum"));
				throw new Exception("操作失败,该批次可能已经被他人处理,请重新查询列表, batchNum:" + params.get("batchNum"));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	/**
	 * 确认导入
	 * 
	 * @param params
	 * @throws PossException
	 */
	public void sureImportRdTx(Map<String, String> params) throws PossException {

		// TODO 确认导入银行处理结果，需要校验金额、卡号等信息
		try {

			// 6更新批次文件信息为已导入
			BatchFileInfo batchFileInfoParam = new BatchFileInfo();
			batchFileInfoParam.setBatchNum(params.get("batchNum"));
			batchFileInfoParam.setFileKy(Long.valueOf(params.get("fileKy")));
			batchFileInfoParam.setBatchFileStatus(5L);
			batchFileInfoParam.setSureimportTime(new Date());
			boolean intStatusFetch = queryBatchFileDao.update("schedule.fo-UpdateBatchFileInfo45", batchFileInfoParam);
			if (!intStatusFetch) {
				log.error("FOPJ-975 单笔确认导入：批次文件状态4---->5 无需回滚事务,异步处理,batchNum:" + params.get("batchNum"));
				throw new Exception("操作失败,该批次可能已经被他人处理,请重新查询列表,  batchNum:" + params.get("batchNum"));
			}
			params.put("busiFlag", "101,102");
			params.put("category", "2");
			log.info("send account msg params: " + params);
			List<WithdrawReconcileResult> reconcileResults = wdReconcileResultDao.queryWdRcResultList(params);
			if (log.isInfoEnabled()) {
				log.info("trade info reconcileResults: " + reconcileResults);
			}
			HashMap<String, String> inParams;

			// ********************
			String bankCode = "";
			String key = "";
			AccountingDto accountingDto = null;
			WithdrawOrder order = null;

			Map<String, Long> amountMap = new HashMap<String, Long>();
			List<String> acctcodeList = new ArrayList<String>();
			for (WithdrawReconcileResult result : reconcileResults) {
				String busiFlag = String.valueOf(result.getBusiFlag());
				String orderSeq = String.valueOf(result.getRightOrderSeq());

				List<WithdrawOrder> orderList = this.withdrawWorkDao.findByQuery("WF.queryWithdrawOrderById", Long.valueOf(orderSeq));
				if (orderList != null && orderList.size() == 1) {
					order = new WithdrawOrder();
					order = orderList.get(0);
					Long orderType = order.getBusiType(); // 订单业务类型
					bankCode = order.getWithdrawBankCode(); // 出款银行

					accountingDto = new AccountingDto();
					accountingDto.setOrderId(orderSeq);
					accountingDto.setAmount(order.getAmount());
					accountingDto.setOrderAmount(order.getAmount());
					accountingDto.setBankCode(bankCode); // 提现银行ID

					if (OrderType.WITHDRAW.getValue() == orderType) {
						if (busiFlag.equals("101")) {
							accountingDto.setPayer(order.getMemberCode());
							key = String.valueOf(orderType) + "-" + busiFlag;
						} else {
							accountingDto.setPayee(order.getMemberCode());
							key = String.valueOf(orderType);
						}
					} else if (OrderType.PAY2BANK.getValue() == orderType) {
						if (busiFlag.equals("101")) {
							accountingDto.setPayer(order.getMemberCode());
							key = String.valueOf(orderType) + "-" + busiFlag;
						} else {
							accountingDto.setPayee(order.getMemberCode());
							key = String.valueOf(orderType);
						}
					} else if (OrderType.BATCHPAY2BANK.getValue() == orderType) {
						if (busiFlag.equals("101")) {
							accountingDto.setPayer(order.getMemberCode());
							key = String.valueOf(orderType) + "-" + busiFlag;
						} else {
							accountingDto.setPayee(order.getMemberCode());
							key = String.valueOf(orderType);
						}
					}
					AccountingService accountingService = accountingServiceMap.get(key);
					List<AccountingEntryDetailDto> withdrawOrderAccAmountList = accountingService.generateDealEntry(accountingDto);

					for (AccountingEntryDetailDto dto : withdrawOrderAccAmountList) {
						if (dto.getMaBlanceBy() == 2) {
							Long amount = (amountMap.get(dto.getAcctcode()) != null) ? amountMap.get(dto.getAcctcode()) : 0L;
							amountMap.put(dto.getAcctcode(), amount + dto.getValue());
							acctcodeList.add(dto.getAcctcode());
						}
					}
				}
			}

			for (String acctcode : acctcodeList) {
				Long amount = amountMap.get(acctcode);
				Long account = accountQueryService.queryBalanceByAcctCode(acctcode);
				log.info("---->请求金额：" + amount + ";---->" + acctcode + "中间账户余额：" + account);
				if (amount > account) {
					throw new PossException(acctcode + "账户余额不足,当前余额为" + account * 0.001 + "元", ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE);
				}

			}
			// ********************

			for (WithdrawReconcileResult result : reconcileResults) {
				inParams = new HashMap<String, String>(2);
				inParams.put("issucc", String.valueOf(result.getBusiFlag()));
				inParams.put("orderId", String.valueOf(result.getRightOrderSeq()));
				if (log.isInfoEnabled()) {
					log.info("send mq to accounting...." + inParams);
				}
				batchSendAccountFacadeService.sendMq2BatchAccount(inParams);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	public void singleImportConfirmRdTx(String orderId, String issucc, String resultKy) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", orderId);
			map.put("issucc", issucc);
			boolean accounting = false;
			accounting = this.handBatchFileService.importConfirmRdTx(map);
			if (!accounting) {
				return;
			}
			Long busiFlag = null;
			if (issucc.equals("101")) {
				busiFlag = new Long(101);
			} else {
				busiFlag = new Long(102);
			}
			WithdrawReconcileResult result = new WithdrawReconcileResult();
			result.setResultKy(Long.parseLong(resultKy));
			result.setBusiFlag(busiFlag);
			// 标记为已处理
			result.setStatus(2l);
			this.wdReconcileResultDao.update("fo-rc.fundout-withdraw-update-reconcile-result", result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void batchSureImport(String batImportPrms) throws PossException {
		if (batImportPrms != null) {
			String[] pars = batImportPrms.split("##");
			if (pars != null) {
				for (int i = 0; i < pars.length; i++) {
					String[] parameters = pars[i].split("::");
					Map<String, String> map = new HashMap<String, String>();
					map.put("batchNum", parameters[0]);
					map.put("bankCode", parameters[1]);
					this.sureImportRdTx(map);
				}
			}
		}
	}

	protected Log log = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.result.WdReconcileResultService#
	 * refuseReviewImportRdTx(java.util.Map)
	 */
	@Override
	public void refuseReviewImportRdTx(WithdrawImportFileDTO importFileDTO) throws PossException {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("batchNum", importFileDTO.getBatchNum());
			params.put("fileKy", String.valueOf(importFileDTO.getFileKy()));
			params.put("bankCode", importFileDTO.getBankCode());
			params.put("category", "1");

			wdReconcileResultDao.deleteWdImportFileRecord(params);
			wdReconcileResultDao.deleteWdImportFileResult(params);
			wdReconcileResultDao.deleteWdImportFile(params);

			// 更新批次文件信息表为已下载
			BatchFileInfo batchFileInfoParam = new BatchFileInfo();
			batchFileInfoParam.setBatchNum(importFileDTO.getBatchNum());
			batchFileInfoParam.setFileKy(importFileDTO.getFileKy());
			batchFileInfoParam.setBatchFileStatus(3L);
			boolean intStatusFetch = queryBatchFileDao.update("schedule.fo-UpdateBatchFileInfo93", batchFileInfoParam);
			if (!intStatusFetch) {
				log.error("退回导入：批次文件状态9---->3失败batchNum:" + params.get("batchNum"));
				throw new Exception("操作失败,该批次可能已经被他人处理,请重新查询列表,batchNum:" + params.get("batchNum"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}
}
