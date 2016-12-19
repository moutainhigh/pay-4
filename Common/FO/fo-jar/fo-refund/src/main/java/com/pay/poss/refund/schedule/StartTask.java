package com.pay.poss.refund.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.Task;
import com.pay.poss.base.schedule.task.TaskManager;
import com.pay.poss.refund.schedule.task.handler.RefundTaskHandler;

public class StartTask {
	private static StartTask instance = new StartTask();
	private Log log = LogFactory.getLog(StartTask.class);
	private TaskManager taskMgr = ContextService.getTaskManager();
	private RefundTaskHandler taskHandler = RefundTaskHandler.getInstance();

	private StartTask() {
		if (taskMgr != null) {
			Set<String> allowTaskSet = taskHandler.getAllowTaskTypes();
			for (String taskType : allowTaskSet) {
				taskMgr.registTaskHandler(taskType, taskHandler);
			}
		}
	}

	public static StartTask getInstance() {
		return instance;
	}

	/**
	 * 自动调度构建批次
	 * 
	 * @param taskMsg
	 *            调度信息
	 * @return 构建成功返回true
	 */
	public String scheduleBuildBatch(String taskMsg) {
		Task task = buildTask(taskMsg, Constants.BATCH_BUILD_AUTO);
		return execute(task, false);
	}

	/**
	 * 重新构建批次
	 * 
	 * @param oldBatchNum
	 *            重新构建的批次号
	 * @return 构建成功返回true
	 */
	public String reBuildBatch(String oldBatchNum) {
		String taskInfo = this.generateTaskInfo("0", Constants.BATCH_REBUILD);
		Task rebuildTask = buildTask(taskInfo, Constants.BATCH_REBUILD);
		if (rebuildTask != null) {// 加入批次号
			rebuildTask.setBatchNum(oldBatchNum);
		}
		return execute(rebuildTask, false);
	}

	/**
	 * 重新构建批次对应文件
	 * 
	 * @param batchNum
	 *            重新构建的批次号
	 * @return 构建成功返回true
	 */
	public String reBuildFile(String batchNum) {
		// 构造手工任务
		String taskInfo = this.generateTaskInfo("0", Constants.BATCH_FILE_BUILD);
		Task fileTask = buildTask(taskInfo, Constants.BATCH_FILE_BUILD);
		if (fileTask != null) {// 加入批次号
			fileTask.setBatchNum(batchNum);
		}
		return execute(fileTask, true);
	}

	/**
	 * 手工构建批次
	 * 
	 * @param workorders
	 *            手工构建批次的工单流水
	 * @return 构建成功返回true
	 */
	public String manualBuildBatch(List<String> workorders) {
		// 构造手工任务
		String taskInfo = this.generateTaskInfo("0", Constants.BATCH_BUILD_MANUAL);
		Task manualTask = buildTask(taskInfo, Constants.BATCH_BUILD_MANUAL);
		if (manualTask != null) {// 加入工单
			manualTask.addManualWorkorder(workorders);
		}

		return execute(manualTask, false);
	}

	private String generateTaskInfo(String ruleKy, int batchActionType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = sdf.format(new Date(System.currentTimeMillis()));
		String manualSeq = batchActionType + currentTime + taskHandler.getManualSeq();
		return manualSeq + ":" + Constants.TASK_TYPE_REFUND + ":" + ruleKy + "::1:" + currentTime;
	}

	private Task buildTask(String taskInfo, int batchActionType) {
		// 任务流水:任务类型:规则号:规则参数:规则优先级:任务运行时间(yyyyMMddHHmmssSSS)
		String newTaskInfo = taskInfo;

		Assert.notNull("任务信息必须不为空", newTaskInfo);

		String[] taskInfoArra = newTaskInfo.split("[" + Constants.COL_SPLIT + "]", 6);

		String taskType = taskInfoArra[1];
		if (taskHandler.getAllowTaskTypes().contains(taskType) == false) {
			log.error("不支持任务类型 [" + taskType + "]");
			return null;
		}
		int taskPriority = 5;
		try {
			taskPriority = Integer.parseInt(taskInfoArra[4]);
		} catch (NumberFormatException e) {
			log.warn("无效任务优先级，系统将设置为默认优先级", e);
		}

		Task task = new Task(newTaskInfo, taskInfoArra[1]);
		task.setCallSeq(taskInfoArra[0]);
		task.setBusiCode(taskInfoArra[2]);
		task.setBusiParam(taskInfoArra[3]);
		task.setBusiPriority(taskPriority);
		task.setCallTime(taskInfoArra[5]);
		task.setStatus(1);
		task.setType(batchActionType);
		return task;
	}

	private String execute(Task taskInfo, boolean threadFlag) {
		if (taskInfo != null) {
			try {
				taskMgr.storeTaskInfo(taskInfo);
			} catch (PossException e) {
				log.error("保存任务信息失败 [" + taskInfo + "]", e);
				return "error";
			}
			return taskMgr.addTask(taskInfo, threadFlag);
		}
		return "ignor";
	}

}
