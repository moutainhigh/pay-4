/**
 * IHttpResponseHandler.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn;

import java.util.List;

import com.hnapay.fi.dto.batchrepair.api.BankOrderDTO;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;

/**
 * Http响应处理，一种是连接异常，一种是200的连接，自定义了响应状态码
 * latest modified time : 2011-8-23 上午10:04:56
 * @author bigknife
 */
public interface IHttpResponseHandler {
	/**
	 * 判断响应是否
	 * @param response
	 * @return
	 */
	boolean isSuccessful(String response);
	/**
	 * 处理连接异常
	 * @param e
	 * @throws Exception
	 */
	void handleConnectException(Exception e) throws ConnectException;
	
	/**
	 * 处理业务上正常的响应
	 * @param response
	 * @return 正常数据封装成IBankData的list
	 */
	List<BankOrderDTO> handleOkResponse(String response);
	
	/**
	 * 处理业务上错误的响应
	 * @param response
	 */
	void handleErrorResponse(String response);
	
}
