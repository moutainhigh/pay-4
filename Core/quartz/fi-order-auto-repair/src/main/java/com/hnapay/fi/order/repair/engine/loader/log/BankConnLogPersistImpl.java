/**
 * BankConnLogPersistImpl.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader.log;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 银行请求日志持久化实现
 * latest modified time : 2011-9-5 上午11:45:27
 * @author bigknife
 */
public class BankConnLogPersistImpl extends SqlMapClientDaoSupport implements IBankConnLogPersist {
	private Log log = LogFactory.getLog(getClass());
	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.engine.loader.log.IBankConnLogPersist#insert(com.hnapay.fi.order.repair.engine.loader.log.BankConnLog)
	 */
	@Override
	public void insert(BankConnLog bankLog) {
		try {
			getSqlMapClient().insert("order.autorepaire.insert",bankLog);
		} catch (SQLException e) {
			log.error("银行返回日志入库出错,要记录的日志为："+bankLog.toString(),e);
		}
	}

}
