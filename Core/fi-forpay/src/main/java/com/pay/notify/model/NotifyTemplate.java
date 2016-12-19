package com.pay.notify.model;

/***
 * 邮件模板实体类
 * @author Davis.guo at 2016年9月8日
 *
 */
public class NotifyTemplate {

	// 主键
	private long id;//必填字段，在代码中定义，查询条件
	// 内容
	private String content;//必填字段，邮件模板内容
	// 备注
	private String memo;

	private String subject;//必填字段，邮件主题
	
	//状态
	private String status;//必填字段，默认为1
	
	private String partnerId;//必填字段，默认为0
	//业务类型，如果邮件发送成功过后，邮件服务器会通过该值缓存模板数据，如果模板数据有变更，一定要修改这个值
	private String busType;//必填字段，自定义值。联合查询条件之一
	
	//模板类型  http、邮件、微信
	private String tempType;//必填字段，email,weixin...。联合查询条件之一
	
	private String params;//必填字段，模板中的数据参数变量，自定义值以逗号分开如: websiteName,bodyContent。联合查询条件之一
	
	

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTempType() {
		return tempType;
	}

	public void setTempType(String tempType) {
		this.tempType = tempType;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String desc) {
		this.memo = desc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "NotifyTemplate [id=" + id + ", content=" + content + ", memo="
				+ memo + ", subject=" + subject + ", status=" + status
				+ ", partnerId=" + partnerId + ", busType=" + busType
				+ ", tempType=" + tempType + "]";
	}
}
