/**
 * BankConnLog.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader.log;

import java.util.Date;
import java.util.UUID;

/**
 * 银行连接日志信息
 * latest modified time : 2011-9-5 上午10:47:04
 * @author bigknife
 */
public class BankConnLog {
	private String channel;
	private String url;
	private String requestBody;
	private Date startTime = new Date();
	private Date endTime;
	private String status;
	private String response;
	private Date createTime;
	private String id;
	
	public BankConnLog(){
		this.id = UUID.randomUUID().toString();
	}
	
	
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}


	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}




	/**
	 * @return the requestBody
	 */
	public String getRequestBody() {
		return requestBody;
	}


	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{channel=");
		buf.append(channel);
		buf.append(",startTime=");
		buf.append(startTime);
		buf.append(",endTime=");
		buf.append(endTime);
		buf.append(",status=");
		buf.append(status);
		buf.append(",requestBody=");
		buf.append(requestBody);
		buf.append(",url=");
		buf.append(url);
		buf.append(",response=");
		buf.append(response);
		buf.append("}");
		return buf.toString();
	}
}
