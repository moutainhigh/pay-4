/**
 * IBankConnLogPersist.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader.log;

/**
 * 日志记录持久化
 * latest modified time : 2011-9-5 上午11:43:59
 * @author bigknife
 */
public interface IBankConnLogPersist {
	/**
	 * 插入银行连接日志
	 * @param log
	 */
	void insert(BankConnLog log);
}
