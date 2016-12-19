/**
 * BaseBankDataLoader.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.hnapay.fi.dto.batchrepair.api.BankOrderDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.enums.batchrepair.api.BatchBankOrderStatusEnum;
import com.hnapay.fi.order.repair.conn.IHttpClientWrapper;
import com.hnapay.fi.order.repair.conn.IHttpRequestBuilder;
import com.hnapay.fi.order.repair.conn.IHttpResponseHandler;
import com.hnapay.fi.order.repair.engine.IBankOrderLoader;
import com.hnapay.fi.order.repair.engine.IOrderRepairEngine;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;
import com.hnapay.fi.order.repair.engine.loader.log.BankConnLog;
import com.hnapay.fi.order.repair.engine.loader.log.IBankConnLogPersist;

/**
 * 基础的银行对象加载 latest modified time : 2011-8-23 上午9:44:39
 * 
 * @author bigknife
 */
public abstract class AbstractBaseBankOrderLoader implements IBankOrderLoader {

	protected Log log = LogFactory.getLog(getClass());

	private String channel;

	private IHttpClientWrapper httpClientWrapper;
	private IHttpRequestBuilder httpRequestBuilder;
	private IHttpResponseHandler httpResponseHandler;

	private static ThreadLocal<HttpClient> tlHc = new ThreadLocal<HttpClient>();

	private List<ILoadParametersGotListener> loadParametersGotListeners;
	private List<IBatchBankOrderGotListener> bankOrderGotListeners;
	
	private int connectTimeout =1000 * 60 *2;
	private int soTomeout = 1000 * 60 * 2;

	// 日志记录
	private IBankConnLogPersist bankConnLogPersist;

	// private HttpClient hc = new DefaultHttpClient();

	
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @param soTomeout the soTomeout to set
	 */
	public void setSoTomeout(int soTomeout) {
		this.soTomeout = soTomeout;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @param bankConnLogPersist
	 *            the bankConnLogPersist to set
	 */
	public void setBankConnLogPersist(IBankConnLogPersist bankConnLogPersist) {
		this.bankConnLogPersist = bankConnLogPersist;
	}

	/**
	 * @param loadParametersGotListeners
	 *            the loadParametersGotListeners to set
	 */
	public void setLoadParametersGotListeners(
			List<ILoadParametersGotListener> loadParametersGotListeners) {
		this.loadParametersGotListeners = loadParametersGotListeners;
	}

	/**
	 * @param bankOrderGotListeners
	 *            the bankOrderGotListeners to set
	 */
	public void setBankOrderGotListeners(
			List<IBatchBankOrderGotListener> bankOrderGotListeners) {
		this.bankOrderGotListeners = bankOrderGotListeners;
	}

	/**
	 * @param httpResponseHandler
	 *            the httpResponseHandler to set
	 */
	public void setHttpResponseHandler(IHttpResponseHandler httpResponseHandler) {
		this.httpResponseHandler = httpResponseHandler;
	}

	/**
	 * @param httpRequestBuilder
	 *            the httpRequestBuilder to set
	 */
	public void setHttpRequestBuilder(IHttpRequestBuilder httpRequestBuilder) {
		this.httpRequestBuilder = httpRequestBuilder;
	}

	/**
	 * @param httpClientWrapper
	 *            the httpClientWrapper to set
	 */
	public void setHttpClientWrapper(IHttpClientWrapper httpClientWrapper) {
		this.httpClientWrapper = httpClientWrapper;
	}

	/**
	 * 初始化
	 */
	private void init() {
		HttpClient hc = new DefaultHttpClient();

		if (httpClientWrapper != null) {
			hc = httpClientWrapper.wrap(hc);
		}
		
		//设置超时时间
		HttpConnectionParams.setConnectionTimeout(hc.getParams(), connectTimeout);
		HttpConnectionParams.setSoTimeout(hc.getParams(), soTomeout);
		
		tlHc.set(hc);
		
		if (httpRequestBuilder == null) {
			throw new RuntimeException("httpRequestBuilder can't be null.");
		}
	}

	/**
	 * 执行加载过程
	 * 
	 * @return
	 * @throws ConnectException
	 */
	protected List<BankOrderDTO> singleThreadLoad(Map<String, String> data)
			throws ConnectException {
		init();
		// 如果data为空，则不发送请求
		if (log.isDebugEnabled()) {
			log.debug("查询参数：" + data);
		}
		if (data == null || data.isEmpty()) {
			log.info(getChannel() + "渠道查询参数为空，不会发起HTTP请求加载银行数据。");
			return null;
		}

		// 记录银行返回报文信息
		BankConnLog bankConnLog = new BankConnLog();
		bankConnLog.setChannel(getChannel());
		if (data != null) {
			bankConnLog.setRequestBody(data.toString());
		}
		HttpUriRequest request = getAndInitRequest(data, bankConnLog);
		try {
			HttpClient hc = tlHc.get();
			if(log.isDebugEnabled()){
				log.debug(Thread.currentThread().getId() + "/" + Thread.currentThread().getName() + "获取的HttpClient对象" + hc);
			}
			String resp = hc.execute(request, new BasicResponseHandler());
			bankConnLog.setResponse(resp);
			bankConnLog.setStatus("200");
			bankConnLog.setEndTime(new Date());
			//log.info("请求返回信息报文："+ resp);

			if (httpResponseHandler.isSuccessful(resp)) {
				List<BankOrderDTO> bankOrderDTOs = httpResponseHandler
						.handleOkResponse(resp);
				return bankOrderDTOs;
			}
			httpResponseHandler.handleErrorResponse(resp);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			bankConnLog.setStatus("500");

			StringBuffer buf = new StringBuffer();
			buf.append(e.getMessage());

			StackTraceElement[] traceEls = e.getStackTrace();
			for(StackTraceElement trace : traceEls){
				buf.append(trace.toString());
				buf.append("\n\r");
			}
			bankConnLog.setResponse(buf.toString());
			
			bankConnLog.setEndTime(new Date());
			throw new ConnectException(getChannel() + " 渠道http client 连接错误.", e);
		} finally {
			// 关闭连接
			HttpClient hc = tlHc.get();
			tlHc.remove();
			hc.getConnectionManager().shutdown();
			// 记录日志
			// bankConnLog.setCreateTime(new Date());
			log.info("渠道连接日志：" + bankConnLog);
			if (bankConnLogPersist != null) {
				bankConnLogPersist.insert(bankConnLog);
			} else {
				log.warn("没有配置bankConnLogPersist,无法持久化到数据库");
			}
		}
	}

	/**
	 * 创建批量银行订单对账数据对象,并设置必填项
	 * 
	 * @return
	 */
	protected BatchBankOrderDTO createBatchBankOrderDTO(
			List<BankOrderDTO> bankOrders) {
		if (bankOrders == null || bankOrders.isEmpty()) {
			return null;
		}

		BatchBankOrderDTO dto = new BatchBankOrderDTO();
		dto.setOperator(IOrderRepairEngine.OPERAOTR);
		dto.setBankOrderList(bankOrders);

		dto.setCountNum(bankOrders.size());
		dto.setStat(BatchBankOrderStatusEnum.SUCCESS);
		dto.setDateTime(new Date());

		long amount = 0;
		Date now = new Date();
		for (BankOrderDTO order : bankOrders) {
			amount += order.getAmount();
			// 设置datetime
			order.setDateTime(now);
		}
		dto.setCountAmount(amount);

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.fi.order.repair.engine.IBankDataLoader#load()
	 */
	@Override
	public BatchBankOrderDTO load() throws ConnectException {
		List<Map<String, String>> parametersList = getLoadParametersList();

		// 增加观察者模式，一旦加载了请求参数，触发监听器
		if (loadParametersGotListeners != null
				&& !loadParametersGotListeners.isEmpty()) {
			for (ILoadParametersGotListener listener : loadParametersGotListeners) {
				listener.onLoadParametersGot(parametersList);
			}
		}
		List<BankOrderDTO> bankOrders = null;

		// 如果只有一个查询参数，则使用单线程加载
		if (parametersList == null || parametersList.size() == 1) {
			bankOrders = singleThreadLoad(parametersList == null ? null
					: parametersList.get(0));
		} else {
			// 多个查询参数使用多线程加载
			bankOrders = multiThreadLoad(parametersList);
		}
		// 创建返回对象
		
		BatchBankOrderDTO dto = createBatchBankOrderDTO(bankOrders);

		// 增加观察者模式，一旦获取了银行订单，触发监听器
		if (bankOrderGotListeners != null && !bankOrderGotListeners.isEmpty()) {
			for (IBatchBankOrderGotListener listener : bankOrderGotListeners) {
				listener.onBatchBankOrderGot(dto);
			}
		}

		return dto;

	}

	/**
	 * 多线程处理机制
	 * 
	 * @param parametersList
	 * @return
	 */
	protected List<BankOrderDTO> multiThreadLoad(
			List<Map<String, String>> parametersList) {
		throw new UnsupportedOperationException("must implemented by sub class");
	}

	// 目前仅支持get 和 post
	private HttpUriRequest getAndInitRequest(Map<String, String> data,
			BankConnLog bankConnLog) {
		String method = httpRequestBuilder.buildMethod();
		if ("POST".equalsIgnoreCase(method)) {
			HttpPost post = new HttpPost();
			HttpEntity entity = httpRequestBuilder.buildHttpEntity(data);

			if (entity != null) {
				post.setEntity(entity);
				bankConnLog.setRequestBody(parseEntityAsString(entity));
			}
			String url = httpRequestBuilder.buildURL();
			post.setURI(URI.create(url));
			bankConnLog.setUrl(url);
			return post;
		} else if ("GET".equalsIgnoreCase(method)) {
			httpRequestBuilder.buildHttpEntity(data);

			String url = httpRequestBuilder.buildURL();
			HttpGet get = new HttpGet(url);

			bankConnLog.setUrl(url);
			return get;
		}
		throw new RuntimeException("unsupport http method :" + method);
	}

	private String parseEntityAsString(HttpEntity entity) {
		try {
			InputStream in = entity.getContent();
			InputStreamReader isr = new InputStreamReader(in, entity
					.getContentEncoding().getValue());
			BufferedReader br = new BufferedReader(isr);

			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line).append("\n");
			}
			return buf.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "解析HTTP Entity出错:" + ex.getMessage();
		}
	}

	/**
	 * 获取一批查询参数，每个查询参数map 会产生一个http请求
	 * 
	 * @return
	 */
	protected List<Map<String, String>> getLoadParametersList() {
		throw new UnsupportedOperationException(
				"must implemented by sub classes");
	}
}
