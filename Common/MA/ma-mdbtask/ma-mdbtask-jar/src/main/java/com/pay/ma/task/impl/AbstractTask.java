/**
 * 
 */
package com.pay.ma.task.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.ma.task.Task;

/**
 * @author Administrator
 * 
 */
public abstract class AbstractTask implements Task {

	private Log log = LogFactory.getLog(AbstractTask.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.task.Task#executeTask()
	 */
	@Override
	public void executeTask() {
		if (log.isInfoEnabled()) {
			this.log.info("\n++++++++++++++++++++++跑批执行"+this.getTaskName()+"开始++++++++++++++++++++：\n");
		}
		//执行Task任务
		this.doTask();
		
		if (log.isInfoEnabled()) {
			this.log.info("\n++++++++++++++++++++++++跑批"+this.getTaskName()+"结束++++++++++++++++++++++：\n");
		}

	}

	/**
	 *执行Task任务 
	 */
	protected abstract void doTask();
	
	
	/**
	 * 获取正在执行任务的名称
	 * @return
	 */
	protected abstract String getTaskName();
	
	
	

}
