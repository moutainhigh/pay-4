package com.pay.fundout.autofundout.custom.model;

public class AutofundoutMail {
	/**
	 * 收邮件 的地址
	 */
	private String EmailAddress;
	/**
	 * 邮件的主题
	 */
	private String  subject;
	/**
	 * 邮件的模板
	 */
	private Long  templateId;
	/**
	 * 收件人姓名
	 */
	private String payeeName;
	/**
	 * 自动提现类型
	 */
	private String autoType;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 失败原因
	 */
	private String failReason;
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getAutoType() {
		return autoType;
	}
	public void setAutoType(String autoType) {
		this.autoType = autoType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
