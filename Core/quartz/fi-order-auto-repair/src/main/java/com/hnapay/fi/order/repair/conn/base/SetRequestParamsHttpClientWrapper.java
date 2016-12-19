/**
 * SetRequestParamsHttpClientWrapper.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

//import com.ibatis.common.logging.Log;

/**
 * 设置请求参数（不是querystring）的包装器 latest modified time : 2011-10-8 上午10:12:57
 * 
 * @author bigknife
 */
public class SetRequestParamsHttpClientWrapper extends HttpClientWrapper {
	protected Log log = LogFactory.getLog(getClass());
	private Map<String, Object> paramMap;
	private Map<String, Integer> intParamMap;
	private Map<String, Long> longParamMap;
	private Map<String, Boolean> booleanParamMap;
	private Map<String, Double> doubleParamMap;
	
	/**
	 * @param paramMap the paramMap to set
	 */
	public Map<String, Object> getParamMap() {
		return this.paramMap;
	}
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	public Map<String, Integer> getIntParamMap() {
		return intParamMap;
	}
	public void setIntParamMap(Map<String, Integer> intParamMap) {
		this.intParamMap = intParamMap;
	}
	public Map<String, Long> getLongParamMap() {
		return longParamMap;
	}
	public void setLongParamMap(Map<String, Long> longParamMap) {
		this.longParamMap = longParamMap;
	}
	public Map<String, Boolean> getBooleanParamMap() {
		return booleanParamMap;
	}
	public void setBooleanParamMap(Map<String, Boolean> booleanParamMap) {
		this.booleanParamMap = booleanParamMap;
	}
	public Map<String, Double> getDoubleParamMap() {
		return doubleParamMap;
	}
	public void setDoubleParamMap(Map<String, Double> doubleParamMap) {
		this.doubleParamMap = doubleParamMap;
	}
	@Override
	protected HttpClientWrapper createInstance() {
		SetRequestParamsHttpClientWrapper instance = new SetRequestParamsHttpClientWrapper();
		if(this.paramMap != null){
			instance.paramMap = new HashMap<String, Object>();
			instance.paramMap.putAll(this.paramMap);
		}
		if(this.intParamMap != null){
			instance.intParamMap = new HashMap<String, Integer>();
			instance.intParamMap.putAll(this.intParamMap);
		}
		if(this.longParamMap != null){
			instance.longParamMap = new HashMap<String, Long>();
			instance.longParamMap.putAll(this.longParamMap);
		}
		if(this.booleanParamMap != null){
			instance.booleanParamMap = new HashMap<String, Boolean>();
			instance.booleanParamMap.putAll(this.booleanParamMap);
		}
		if(this.doubleParamMap != null){
			instance.doubleParamMap = new HashMap<String, Double>();
			instance.doubleParamMap.putAll(this.doubleParamMap);
		}
		return instance;
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException,
			ClientProtocolException {
		setHttpParams(request);
		return super.execute(request);
	}

	@Override
	public <T> T execute(HttpUriRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException,
			ClientProtocolException {
		setHttpParams(request);
		return super.execute(request, responseHandler);
	}
	
	/**
	 * 设置请求参数
	 * @param request
	 */
	private void setHttpParams(HttpUriRequest request){
		if(intParamMap != null){
			for(Entry<String, Integer> p : intParamMap.entrySet()){
				request.getParams().setIntParameter(p.getKey(), p.getValue());
			}
		}
		if(longParamMap != null){
			for(Entry<String, Long> p : longParamMap.entrySet()){
				request.getParams().setLongParameter(p.getKey(), p.getValue());
			}
		}
		if(doubleParamMap != null){
			for(Entry<String, Double> p : doubleParamMap.entrySet()){
				request.getParams().setDoubleParameter(p.getKey(), p.getValue());
			}
		}
		if(booleanParamMap != null){
			for(Entry<String, Boolean> p : booleanParamMap.entrySet()){
				request.getParams().setBooleanParameter(p.getKey(), p.getValue());
			}
		}
		if(paramMap != null){
			for(Entry<String, Object> p : paramMap.entrySet()){
				//System.out.println("p.getKey()"+p.getKey()+ "p.getValue()="+p.getValue());
				request.getParams().setParameter(p.getKey(), p.getValue());
			}
		}
	}
}
