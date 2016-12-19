package com.pay.pe.model;

import java.util.Date;

public class ChannelBankSubjectMapping {

	/*
	 * 主键
	 */
	private Long id;
	
	/*
	 * 渠道编号
	 */
	private String channelCode;
	
	/*
	 * 渠道名称
	 */
	private String subject;
	
	/*
	 * 记账科目号,1-出款,2-充退
	 */
	private Integer type;
	
	/*
	 * 创建日期
	 */
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
