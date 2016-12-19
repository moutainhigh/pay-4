/**
 * IJob.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.job;

/**
 * Job 接口
 * latest modified time : 2011-8-24 上午11:17:22
 * @author bigknife
 */
public interface IJob {
	/**
	 * 获取job 名称
	 * @return
	 */
	String getJobName();
	
	/**
	 * job 工作方法
	 */
	void work();
}
