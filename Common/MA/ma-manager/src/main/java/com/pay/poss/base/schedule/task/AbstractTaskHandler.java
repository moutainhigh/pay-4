package com.pay.poss.base.schedule.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.ISequenceable;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchInfo;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public abstract class AbstractTaskHandler implements ITaskHandler {
	protected Log log = LogFactory.getLog(getClass());
	protected BaseDAO daoService = (BaseDAO) ContextService.getInstance()
			.getBean(BaseDAO.BEAN_ID);
	protected Set<String> ALLOW_TASK_TYPE = new HashSet<String>();

	@Override
	public String execute(Task task) {
		try {
			BatchRule batchRule = getBatchRule(task);
			if (batchRule == null) {
				log.warn("不存在对应的规则，系统将忽略此任务的调度运行 [" + task + "]");
				return null;
			}

			// 产生新批次号
			generateBatchNum(batchRule, task);

			// 保存批次信息
			saveBatchInfo(batchRule, task);

			// 更新批次号
			updateWorkOrderBatchNum(batchRule, task);

			// 关联批次关系
			saveBatchRelation(task);

			Map<String, String> fileInfo = new HashMap<String, String>();
			fileInfo.put("BATCH_FILE_PATH",
					CommonConfiguration.getStrProperties("batchFilePath"));
			fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
			fileInfo.put("BATCH_NUM", task.getNewBatchNum());
			fileInfo.put("BATCH_TASK_TYPE", task.getCallType());

			createFile(daoService, fileInfo);

			updateScheduleState(task, "2");

			return task.getNewBatchNum();
		} catch (PossException pe) {
			log.error("任务运行失败 [" + task + "]", pe);

			updateScheduleState(task, "1");

			return task.getNewBatchNum();
		}
	}

	public void updateScheduleState(Task task, String state) {
		String msg = "任务运行成功";
		if ("1".equals(state)) {
			msg = "任务运行失败";
		}
		try {
			log.info(msg + " [" + task + "]");
			Map<String, String> params = new HashMap<String, String>();
			params.put("scheduleKY", task.getScheduleKY() + "");
			params.put("execStatus", state);
			daoService.update("schedule.updateScheduleStatus", params);
		} catch (Exception e) {
			log.error(msg + " 保存任务信息出现错误 [" + task + "]", e);
		}
	}

	public void saveBatchRelation(Task task) throws PossException {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("oldBatchNum", task.getBatchNum());
			params.put("newBatchNum", task.getNewBatchNum());
			daoService.create("schedule.insertBatchRelation", params);
		} catch (Exception e) {
			log.error("插入批次关系出现错误 [" + task + "]", e);
			throw new PossException("插入批次关系出现错误 [" + task + "]",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	private void updateWorkOrderBatchNum(BatchRule batchRule, Task task)
			throws PossException {
		int taskInnerType = task.getType();

		// 自动批次，工单由规则提供
		if (taskInnerType == Constants.BATCH_BUILD_AUTO) {
			updateWOBatchNumWithAuto(batchRule, task);
		}

		// 手工批次,工单由用户选择列表提供
		if (taskInnerType == Constants.BATCH_BUILD_MANUAL) {
			String orderSeqsSql = buildSqlFromManualTask(task);
			updateWOBatchNumWithManual(task.getNewBatchNum(), orderSeqsSql);
		}

		// 重建批次,工单范围<=原批次范围
		if (taskInnerType == Constants.BATCH_REBUILD) {
			updateWOBatchNumWithRebuild(task.getBatchNum(),
					task.getNewBatchNum());
		}

		// 重建文件,工单不影响
		if (taskInnerType == Constants.BATCH_FILE_BUILD) {
			return;
		}
	}

	public void saveBatchInfo(BatchRule batchRule, Task task)
			throws PossException {
		// 重建文件,不修改批次信息
		if (task.getType() == Constants.BATCH_FILE_BUILD) {
			return;
		}

		try {
			BatchInfo batchInfo = new BatchInfo();
			batchInfo.setRuleKy(batchRule.getRuleKy());
			batchInfo.setRuleName(batchRule.getRuleName());
			batchInfo.setBatchNum(task.getNewBatchNum());
			batchInfo.setBatchType(task.getCallType());
			batchInfo.setBatchName(task.getBatchName());
			batchInfo.setStatus(2L);// 批次已生成
			batchInfo.setOperators("--");

			daoService.create("schedule.insertBatch", batchInfo);
		} catch (Exception e) {
			log.error("插入批次信息出现错误 [" + task + "]", e);
			throw new PossException("插入批次信息出现错误 [" + task + "]",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	private void generateBatchNum(BatchRule batchRule, Task task) {
		String newBatchNum = task.getBatchNum();

		int taskInnerType = task.getType();

		// 自动批次，需要通过规则产生批次号
		if (taskInnerType == Constants.BATCH_BUILD_AUTO) {
			String genetorClass = batchRule.getBatchNumGenetor();
			ISequenceable batchNumGenetor = (ISequenceable) ContextService
					.getInstance().getDataService().getData(genetorClass);
			newBatchNum = "A_" + batchNumGenetor.nextStringValue();
			// 关键，保证批次号重用
			task.setBatchNum(newBatchNum);
		}

		// 手工批次,前缀M
		if (taskInnerType == Constants.BATCH_BUILD_MANUAL) {
			ISequenceable batchNumGenetor = (ISequenceable) daoService;
			newBatchNum = "M_" + batchNumGenetor.nextStringValue();
			// 关键，保证批次号重用
			task.setBatchNum(newBatchNum);
		}

		// 重建批次,前缀RB
		if (taskInnerType == Constants.BATCH_REBUILD) {
			ISequenceable batchNumGenetor = (ISequenceable) daoService;
			newBatchNum = "RB_" + task.getBatchNum() + "_"
					+ batchNumGenetor.nextStringValue();
		}

		// 重建文件,批次与原批次须相同
		if (taskInnerType == Constants.BATCH_FILE_BUILD) {
			newBatchNum = task.getBatchNum();
		}

		// 保存新批次
		task.setNewBatchNum(newBatchNum);
	}

	@Override
	public Set<String> getAllowTaskTypes() {
		return ALLOW_TASK_TYPE;
	}

	public String getManualSeq() {
		return ((ISequenceable) daoService).nextStringValue();
	}

	private BatchRule getBatchRule(Task task) {
		BatchRule result = null;
		if (task.getType() == Constants.BATCH_BUILD_MANUAL) {
			result = new BatchRule();
			result.setRuleKy(1L);
			result.setRuleName("MANUAL-RULE-REFUND-01");
			return result;
		}
		if (task.getType() == Constants.BATCH_BUILD_AUTO) {
			result = findRuleByID(task.getBusiCode());
		}
		if (task.getType() == Constants.BATCH_REBUILD
				|| task.getType() == Constants.BATCH_FILE_BUILD) {
			String batchNum = task.getBatchNum();
			result = findRuleByBatchNum(batchNum);
		}
		return result;
	}

	private BatchRule findRuleByID(String busiCode) {
		return null;
	}

	private BatchRule findRuleByBatchNum(String batchNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public String buildSqlFromManualTask(Task task) {
		List<String> seqs = task.getManualWorkorder();
		if (seqs.isEmpty()) {
			return null;
		}
		StringBuffer result = new StringBuffer("(");
		for (String seq : seqs) {
			result.append(seq);
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		result.append(")");

		return result.toString();
	}

	/**
	 * 更新工单批次(自动批次)
	 * 
	 * @param batchRule
	 *            批次规则
	 * @param task
	 *            任务信息
	 * @throws PossException
	 */
	protected abstract void updateWOBatchNumWithAuto(BatchRule batchRule,
			Task task) throws PossException;

	/**
	 * 更新工单批次(手工批次)
	 * 
	 * @param newBatchNum
	 *            批次号
	 * @param orderSeqsSql
	 *            批次对应工单流水的SQL表达式
	 * @throws PossException
	 */
	protected abstract void updateWOBatchNumWithManual(String newBatchNum,
			String orderSeqsSql) throws PossException;

	/**
	 * 更新工单批次(重建批次)
	 * 
	 * @param batchNum
	 *            原批次号
	 * @param newBatchNum
	 *            新批次号
	 * @throws PossException
	 */
	protected abstract void updateWOBatchNumWithRebuild(String batchNum,
			String newBatchNum) throws PossException;

	/**
	 * 生成批次对应文件
	 * 
	 * @param daoService
	 *            数据库服务
	 * @param fileInfo
	 *            批次文件信息
	 * @throws PossException
	 */
	protected abstract void createFile(BaseDAO daoService,
			Map<String, String> fileInfo) throws PossException;
}
