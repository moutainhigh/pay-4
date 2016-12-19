/**
 * ILoadParametersGotListener.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import java.util.List;
import java.util.Map;

/**
 * 查询请求参数已获取监听器
 * latest modified time : 2011-8-27 下午12:12:21
 * @author bigknife
 */
public interface ILoadParametersGotListener {
	void onLoadParametersGot(List<Map<String, String>> parametersList);
}
