/**
 * IBankDataLoader.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine;

import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;

/**
 * 银行数据加载器
 * latest modified time : 2011-8-22 上午11:47:35
 * @author bigknife
 */
public interface IBankOrderLoader {
	/**
	 * 加载银行数据
	 * @return
	 * @throws ConnectException
	 */
	BatchBankOrderDTO load() throws ConnectException;
	
	/**
	 * 获取当前loader的渠道标志
	 * @return
	 */
	String getChannel();
}
