package com.pay.poss.base.schedule.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.ISequenceable;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchInfo;
import com.pay.poss.base.model.BatchRule;
import com.pay.poss.base.model.Task;

public abstract class AbstractTaskHandler implements ITaskHandler {

	protected Log log = LogFactory.getLog(getClass());
	protected BaseDAOImpl daoService = (BaseDAOImpl) ContextService
			.getBean(BaseDAO.BEAN_ID);
	protected Set<String> ALLOW_TASK_TYPE = new HashSet<String>();
	private PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) ContextService
			.getBean("possTransactionManager");
	private TransactionTemplate transactionTemplate = new TransactionTemplate(
			platformTransactionManager);
	private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public String execute(Task task) {
		BatchRule batchRule = getBatchRule(task);
		if (batchRule == null) {
			log.warn("不存在对应的规则，系统将忽略此任务的调度运行 [" + task + "]");
			return null;
		}

		try {
			// 产生新批次号
			generateBatchNum(batchRule, task);
		} catch (Exception e) {
			log.error("execute error:", e);
		}

		// 事务方式处理批次工单
		handleWorkOrderOfBatch(batchRule, task);

		Map<String, String> fileInfo = new HashMap<String, String>();
		fileInfo.put("BATCH_FILE_PATH",
				CommonConfiguration.getStrProperties("batchFilePath"));
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("BATCH_NUM", task.getNewBatchNum());
		fileInfo.put("BATCH_TASK_TYPE", task.getCallType());
		// 自动生成批次
		if (task.getType() == Constants.BATCH_BUILD_AUTO) {
			fileInfo.put("BATCH_BUILD_AUTO", "BATCH_BUILD_AUTO");
		}
		try {
			createFile(daoService, fileInfo);
	//		createFileRecord(daoService,task);
			updateScheduleState(task, "2");
		} catch (PossException e) {
			log.error("生成批次对应文件错误 [" + task + "]", e);
			updateScheduleState(task, "1");
		}

		return task.getNewBatchNum();
	}

	private void handleWorkOrderOfBatch(final BatchRule batchRule,
			final Task task) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					// 保存批次信息
					saveBatchInfo(batchRule, task);

					// 更新批次号
					updateWorkOrderBatchNum(batchRule, task);

					// 关联批次关系
					saveBatchRelation(task);
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("处理批次文件错误 [" + task + "]", e);
				}
			}
		});
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

	@SuppressWarnings("unchecked")
	public void saveBatchInfo(BatchRule batchRule, Task task)
			throws PossException {
		// 重建文件,不修改批次信息
		if (task.getType() == Constants.BATCH_FILE_BUILD) {
			return;
			// 重新构建的批次号
		} else if (task.getType() == Constants.BATCH_REBUILD) {
			BatchInfo batchInfo = new BatchInfo();
			batchInfo.setStatus(6L);
			batchInfo.setBatchNum(task.getBatchNum());
			/**
			 * FOPJ-975重成.批次表：批次状态3--->6 防并发
			 */
			// daoService.update("schedule.fo-UpdateBatchInfo", batchInfo);
			boolean intFetchRow = daoService.update(
					"schedule.fo-UpdateBatchInfo36", batchInfo);
			if (!intFetchRow) {
				log.error("FOPJ-975批次表：批次状态3--->6 更新失败 BATCHNUM:"
						+ task.getBatchNum());
				throw new PossException("FOPJ-975批次表：批次状态3--->6 更新失败 BATCHNUM:"
						+ task.getBatchNum(),
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			}			
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

	private void generateBatchNum(BatchRule batchRule, Task task)
			throws Exception {
		String newBatchNum = task.getBatchNum();

		int taskInnerType = task.getType();

		ISequenceable batchNumGenetor = (ISequenceable) daoService.getSequenceGenerator();
		long seqVal = batchNumGenetor.nextLongValue();
		String code = dataFormat.format(new Date())
				+ String.format("%04d", seqVal % 10000l);

		// 自动批次，需要通过规则产生批次号,前缀A
		if (taskInnerType == Constants.BATCH_BUILD_AUTO) {
			newBatchNum = "A_" + code;
			// 关键，保证批次号重用
			task.setBatchNum(newBatchNum);
		}
		// 手工批次,前缀M
		if (taskInnerType == Constants.BATCH_BUILD_MANUAL) {
			newBatchNum = "M_" + code;
			// 关键，保证批次号重用
			task.setBatchNum(newBatchNum);
		}
		// 重建批次,前缀R
		if (taskInnerType == Constants.BATCH_REBUILD) {
			task.setBatchName("BATCH_REBUILD");
			newBatchNum = "R_" + code;
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
		return ((ISequenceable) daoService.getSequenceGenerator()).nextStringValue();
	}

	private BatchRule getBatchRule(Task task) {
		BatchRule result = null;
		if (task.getType() == Constants.BATCH_BUILD_MANUAL) {
			result = new BatchRule();
			result.setRuleKy(new Long(task.getBusiCode()));
			result.setRuleName("RULE-MANUAL-NEW");
			return result;
		}
		if (task.getType() == Constants.BATCH_BUILD_AUTO) {
			result = new BatchRule();
			result.setRuleKy(new Long(task.getBusiCode()));
			result.setRuleName("RULE-AUTO-NEW");
			return result;
		}
		if (task.getType() == Constants.BATCH_REBUILD
				|| task.getType() == Constants.BATCH_FILE_BUILD) {
			try {
				BatchInfo batchInfo = new BatchInfo();
				batchInfo.setBatchNum(task.getBatchNum());
				batchInfo = (BatchInfo) this.daoService.findObjectByCriteria(
						"schedule.fo-QueryBatchInfo", batchInfo);

				result = new BatchRule();
				result.setRuleKy(batchInfo.getRuleKy());
				result.setRuleName("RULE-REBUILD-OLD");
				return result;
			} catch (Exception e) {
				log.error("查找批次信息出现错误 [" + task + "]", e);
				return null;
			}
		}
		return result;
	}

	/**
	 * @author Jonathen Ni
	 * @param batchNum
	 * @date 2010-11-03
	 * @return
	 */
	private BatchRule findRuleByBatchNum(String batchNum) {
		BatchRule rule = null;
		if (batchNum != null) {
			rule = new BatchRule();
			BatchInfo batchInfo = new BatchInfo();
			batchInfo.setBatchNum(batchNum);
			batchInfo = (BatchInfo) this.daoService.findObjectByCriteria(
					"fo-QueryBatchInfo", batchInfo);
			if (batchInfo != null)
				rule.setRuleKy(batchInfo.getRuleKy());
		}
		return rule;
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
