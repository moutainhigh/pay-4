package com.pay.acc.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 基于HTTP Client的消息服务
 * 
 * @author jinjun@f-road.com.cn
 * @date Oct 16, 2014 7:37:03 PM
 *
 */
public class HttpClientMessageServiceImpl implements MessageService {

	private Log logger = LogFactory.getLog(getClass());

	/**
	 * 字符编码
	 */
	private String charset="UTF-8";
	
	/**
	 * 默认为POST方式提交
	 */
	private String method = "POST";
	
	/**
	 * 是否URL ENCODING， 只有当GET方式提交才会使用该参数
	 */
	private String isUrlEncode = "false";
	
	@Override
	public String sendMessage(Server server,String message) {
		logger.info("[http报文发送]开始, 报文内容="+message);
		
		String result = null;
		
		HttpClient hc = new HttpClient(server.getUrl(), 5000, 5000);
		try {
			int status = hc.send(message, charset);
			if (200 == status) {
				result = hc.getResult();
				logger.info("[http报文发送]响应报文="+result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		logger.info("[http报文发送]结束");
		
		return result;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIsUrlEncode() {
		return isUrlEncode;
	}

	public void setIsUrlEncode(String isUrlEncode) {
		this.isUrlEncode = isUrlEncode;
	}

}
