/**
 * SingleBankDataLoader.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 批量银行数据查询接口
 * latest modified time : 2011-8-23 下午2:51:09
 * @author bigknife
 */
public abstract class BatchBankOrderLoader extends AbstractBaseBankOrderLoader {

	@Override
	protected final List<Map<String, String>> getLoadParametersList() {
		List<Map<String, String>> paramList = new ArrayList<Map<String,String>>();
		paramList.add(loadOneParameterMap());
		return paramList;
	}
	
	/**
	 * 批量查询的请求参数
	 * @return
	 */
	protected abstract Map<String, String> loadOneParameterMap ();
}
