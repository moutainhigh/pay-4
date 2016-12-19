package com.pay.poss.base.model;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 工作线程池
 * 
 * @author hlv
 * @lastModify Feb 8, 2010
 * @version 0.8
 * 
 */
public class TaskThreadPool {
	private final Log logger = LogFactory.getLog(TaskThreadPool.class);
	// JDK 工作线程
	private ThreadPoolExecutor pool;
	// 核心线程容量
	private int corePoolSize;
	// 最小线程数
	private int maximumPoolSize;
	// 线程池容量
	private int queueCapacity;
	// 线程闲置时间（当线程的闲置时间超过此值，将被视为闲置线程被回收）
	private long keepAliveTime;
	// 当工作线程池无法处理新任务时指定的拒绝任务处理器
	private RejectedExecutionHandler rejectedExecutionHandler;

	public TaskThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueCapacity) {
		pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
	}

	public void execute(Task task) {
		pool.execute(task);
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return rejectedExecutionHandler;
	}

	public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
	}
	public int getActiveCount() {
		return pool.getActiveCount();
	}
}
