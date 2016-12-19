package com.pay.fundout.withdraw.schedule.task.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.batchinfo.service.genbatch.BatchFileGenerateService;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleConfigService;
import com.pay.fundout.withdraw.service.rulewithdrawbank.BatchRuleWithdrawBankService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;
import com.pay.poss.base.schedule.task.AbstractTaskHandler;

public class WithdrawTaskHandler extends AbstractTaskHandler {

	private static Log logger = LogFactory.getLog(WithdrawTaskHandler.class);

	private BatchRuleWithdrawBankService batchRuleWithdrawBankService = (BatchRuleWithdrawBankService) ContextService
			.getBean("fundout-rulewithdrawbank-BatchRuleWithdrawBankService");
	private BatchFileGenerateService fileService = (BatchFileGenerateService) ContextService
			.getBean("fo-batchinfo-withdrawFileGenerateService");
	private BatchRuleConfigService batchRuleConfigService = (BatchRuleConfigService) ContextService
			.getBean("fundout-ruleconfig-BatchRuleConfigService");
	private static WithdrawTaskHandler instance = new WithdrawTaskHandler();

	private WithdrawTaskHandler() {
		ALLOW_TASK_TYPE.add(Constants.TASK_TYPE_WITHDRAW);
	}

	public static WithdrawTaskHandler getInstance() {
		return instance;
	}

	protected void createFile(BaseDAO daoService, Map<String, String> fileInfo)
			throws PossException {
		fileService.generateBatchFile(fileInfo);
	}

	
	// 自动批次 更新批次号
	@SuppressWarnings("unchecked")
	protected void updateWOBatchNumWithAuto(BatchRule batchRule, Task task)
			throws PossException {
		// 通过规则ID查找符合规则的银行信息
		Long ruleId = Long.valueOf(task.getBusiCode());
		List<FundoutChannel> channels = batchRuleWithdrawBankService
				.getFundoutChannelByRuleId(ruleId);
		if (channels == null || channels.size() < 0) {
			return;
		}
		Long maxCount = null;
		RuleConfigQueryDTO ruleDto = batchRuleConfigService
				.getRuleConfigById(ruleId);
		if (ruleDto == null) {
			return;
		}
		maxCount = ruleDto.getMaxOrderCounts();
		for (FundoutChannel dto : channels) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("batchNum", task.getNewBatchNum());
			params.put("withdrawBankId", String.valueOf(dto.getBankId()));
			params.put("busiType", String.valueOf(dto.getBusinessCode()));
			params.put("withdrawType", String.valueOf(dto.getModeCode()));
			if (maxCount != null) {
				params.put("maxCount", maxCount);
			}
			if (logger.isInfoEnabled()) {
				logger.info("auto update batch params :" + params);
			}
			// params.put("maxCounts", dto.getMaxCounts());
			int result = 0;
			if (maxCount != null && maxCount.longValue() >= 0) { // TODO
																	// 为0是不需要执行
				boolean b = daoService.update(
						"wdfileservice.fundout-withdraw-update-batch-auto",
						params);
				maxCount = maxCount.longValue() - result;
			} else if (maxCount == null) {
				daoService.update(
						"wdfileservice.fundout-withdraw-update-batch-auto",
						params);
			}
			if (logger.isInfoEnabled()) {
				logger.info("auto update batch result :" + result);
			}
			if (maxCount != null && maxCount.longValue() <= 0) {
				break;
			}
		}
	}

	// 手工生成批次文件 更新批次号
	@Override
	protected void updateWOBatchNumWithManual(String newBatchNum,
			String orderSeqsSql) throws PossException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", newBatchNum);
		params.put("seqSql", orderSeqsSql);
		daoService.update("wdfileservice.fundout-withdraw-update-generate",
				params);
	}

	// 批次重建
	@Override
	protected void updateWOBatchNumWithRebuild(String batchNum,
			String newBatchNum) throws PossException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", batchNum);
		params.put("newBatchNum", newBatchNum);
		daoService.update("wdfileservice.fundout-withdraw-update-regenerate",
				params);
	}

	
}
