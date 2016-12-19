package com.pay.poss.refund.schedule.task.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.batchinfo.service.genbatch.BatchFileGenerateService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;
import com.pay.poss.base.schedule.task.AbstractTaskHandler;
import com.pay.poss.refund.model.RefundBatchDTO;
import com.pay.poss.refund.model.RefundBatchInfoDTO;

public class RefundTaskHandler extends AbstractTaskHandler {
	private static Log logger = LogFactory.getLog(RefundTaskHandler.class);

	private BatchFileGenerateService fileGenerateService = (BatchFileGenerateService) ContextService
			.getBean("fo-batchinfo-refundFileGenerateService");
	private static RefundTaskHandler instance = new RefundTaskHandler();

	private RefundTaskHandler() {
		ALLOW_TASK_TYPE.add(Constants.TASK_TYPE_REFUND);
	}

	public static RefundTaskHandler getInstance() {
		return instance;
	}

	@Override
	protected void createFile(BaseDAO daoService, Map<String, String> fileInfo)
			throws PossException {
		/*
		 * List<RefundBatchInfoDTO> masterInfos =
		 * calcMasterInfo(fileInfo.get("BATCH_NUM")); if(masterInfos != null &&
		 * masterInfos.size() ==1){ return; } for (RefundBatchInfoDTO
		 * refundBatchInfoDTO : masterInfos) { // new
		 * MasterFileOperateWorker(daoService, fileInfo,
		 * refundBatchInfoDTO).start(); // if
		 * ("TOTAL_BANK".equals(refundBatchInfoDTO.getBankCode())==false) { //
		 * List<RefundBatchDTO> refundBatchList =
		 * calcDetailInfo(fileInfo.get("BATCH_NUM"
		 * ),refundBatchInfoDTO.getBankCode()); // new
		 * DetailFileOperateWorker(daoService, fileInfo, refundBatchList,
		 * refundBatchInfoDTO, detailFileHandler).start(); // }
		 * 
		 * 
		 * //FIXME 吕宏修改：暂时不用多线程 try { new MasterFileOperateWorker(daoService,
		 * fileInfo, refundBatchInfoDTO).creatorMasterFile(); if
		 * ("TOTAL_BANK".equals(refundBatchInfoDTO.getBankCode())==false) {
		 * List<RefundBatchDTO> refundBatchList =
		 * calcDetailInfo(fileInfo.get("BATCH_NUM"
		 * ),refundBatchInfoDTO.getBankCode()); new
		 * DetailFileOperateWorker(daoService, fileInfo, refundBatchList,
		 * refundBatchInfoDTO, detailFileHandler).fileOperate(); } } catch
		 * (Exception e) {
		 * log.error("生成文件出现错误 [银行编码"+refundBatchInfoDTO.getBankCode()+"]",e); }
		 * }
		 */
		logger.info("============" + fileInfo);
		fileGenerateService.generateBatchFile(fileInfo);

	}
	
	@Override
	protected void updateWOBatchNumWithAuto(BatchRule batchRule, Task task)
			throws PossException {
		daoService.update("refund.batch.updateWOBatchNumWithAuto",
				task.getNewBatchNum());
	}

	@Override
	protected void updateWOBatchNumWithManual(String newBatchNum,
			String orderSeqsSql) throws PossException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", newBatchNum);
		params.put("seqSql", orderSeqsSql);
		daoService.update("refund.batch.updateWOBatchNumWithManual", params);
	}

	@Override
	protected void updateWOBatchNumWithRebuild(String batchNum,
			String newBatchNum) throws PossException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", batchNum);
		params.put("newBatchNum", newBatchNum);
		daoService.update("refund.batch.updateWOBatchNumWithRebuild", params);
	}

	// 查询详细
	public List<RefundBatchDTO> calcDetailInfo(String batchNum, String bank) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BANK_CODE", bank);
		params.put("BATCH_NUM", batchNum);
		return (List<RefundBatchDTO>) daoService.findByQuery(
				"refund.batch.selectRefundBatchDTOByBatchNumAndBankCode",
				params);
	}

	public List<RefundBatchInfoDTO> calcMasterInfo(String batchNum)
			throws PossException {
		try {
			List<RefundBatchInfoDTO> result = (List<RefundBatchInfoDTO>) daoService
					.findByQuery("refund.batch.calcMasterInfoByBatchNum",
							batchNum);

			int totalCount = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			for (RefundBatchInfoDTO refundBatchInfoDTO : result) {
				refundBatchInfoDTO.setBatchNum(batchNum);

				totalCount = totalCount + refundBatchInfoDTO.getTotalCount();
				totalAmount = totalAmount.add(refundBatchInfoDTO
						.getTotalAmount());
			}

			RefundBatchInfoDTO temp = new RefundBatchInfoDTO();
			temp.setBankCode("TOTAL_BANK");
			temp.setBatchNum(batchNum);
			temp.setTotalAmount(totalAmount);
			temp.setTotalCount(totalCount);

			result.add(temp);

			return result;
		} catch (Exception e) {
			log.error("计算概要信息出现错误 [batchNum=" + batchNum + "]", e);
			throw new PossException("计算概要信息出现错误 [batchNum=" + batchNum + "]",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}


}
