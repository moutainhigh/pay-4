/**
 * BaseOrderRepairJob.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hnapay.fi.order.repair.engine.IOrderRepairEngine;

/**
 * latest modified time : 2011-8-24 上午11:19:26
 * @author bigknife
 */
public abstract class BaseOrderRepairJob implements IJob {
	private IOrderRepairEngine orderRepairEngine;
	private Log log = LogFactory.getLog(getClass());
	private String jobName;
	private String description;
	private String repairFlag;
	
	public String getRepairFlag() {
		return repairFlag;
	}

	public void setRepairFlag(String repairFlag) {
		this.repairFlag = repairFlag;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param orderRepairEngine the orderRepairEngine to set
	 */
	public void setOrderRepairEngine(IOrderRepairEngine orderRepairEngine) {
		this.orderRepairEngine = orderRepairEngine;
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.job.IJob#getJobName()
	 */
	@Override
	public String getJobName() {
		return jobName;
	}
	
	

	/**
	 * @return the orderRepairEngine
	 */
	public IOrderRepairEngine getOrderRepairEngine() {
		return orderRepairEngine;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	/**
	 * 记录info级别日志
	 * @param message
	 */
	public void logInfo(String message){
		if(log.isInfoEnabled()){
			log.info(jobName + " : " + message);
		}
	}
	/**
	 * 记录debug级别日志
	 * @param message
	 */
	public void logDebug(String message){
		if(log.isDebugEnabled()){
			log.debug(jobName + " : " + message);
		}
	}
	/**
	 * 记录error级别日志
	 * @param message
	 */
	public void logError(String message){
		if(log.isErrorEnabled()){
			log.error(jobName + " : " + message);
		}
	}
	/**
	 * 记录error级别日志
	 * @param message
	 */
	public void logWarn(String message){
		if(log.isWarnEnabled()){
			log.warn(jobName + " : " + message);
		}
	}
	/**
	 * 记录error级别日志
	 * @param message
	 */
	public void logError(String message,Exception ex){
		if(log.isErrorEnabled()){
			log.error(jobName + " : " + message, ex);
		}
	}
	
	@Override
	public final void work() {
		try{
			logInfo(jobName + " start work ...");
			logInfo(description);
			innerWork();
			logInfo(jobName + " work successfully");
		}catch(Exception ex){
			logError(jobName + " work error.",ex);
		}finally{
			logInfo(jobName + " end work.");
		}
	}
	/**
	 * 真正的job工作部分
	 * @throws Exception
	 */
	protected abstract void innerWork() throws Exception;
}
