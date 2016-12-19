package com.pay.poss.base.schedule.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.Task;
import com.pay.poss.base.model.TaskThreadPool;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public class TaskManager {
	private Log log = LogFactory.getLog(TaskManager.class);
	// 工作线程池
	private TaskThreadPool pool = new TaskThreadPool(2, 4, 60, 3);;
	// 任务处理器注册表
	private Map<String, ITaskHandler> taskHandlerRepository = new HashMap<String, ITaskHandler>();

	private BaseDAO daoService;

	private final ReentrantLock lock = new ReentrantLock();

	public void registTaskHandler(String taskType, ITaskHandler taskHandler) {
		try {
			lock.lock();
			taskHandlerRepository.put(taskType, taskHandler);
		} finally {
			lock.unlock();
		}
	}

	public ITaskHandler getTaskHandler(String taskType) {
		try {
			lock.lock();
			return taskHandlerRepository.get(taskType);
		} finally {
			lock.unlock();
		}
	}

	public void storeTaskInfo(Task task) throws PossException {
		try {
			daoService.create("schedule.insertTask", task);
		} catch (Exception e) {
			log.error("存入Task信息出现错误 [" + task + "]", e);
			throw new PossException("存入Task信息出现错误 [" + task + "]", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	public String addTask(Task task, boolean threadFlag) {
		ITaskHandler taskHandler = getTaskHandler(task.getCallType());
		if (taskHandler != null) {
			task.setTaskHandler(taskHandler);
			if (threadFlag) {
				pool.execute(task);
				return "true";
			} else {
				return taskHandler.execute(task);
			}
		}
		return "ignor";
	}
}
