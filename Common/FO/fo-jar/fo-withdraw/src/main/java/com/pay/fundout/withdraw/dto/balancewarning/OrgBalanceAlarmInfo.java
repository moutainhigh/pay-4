package com.pay.fundout.withdraw.dto.balancewarning;

import java.util.Date;

/**
 * 机构余额预警信息表
 * 
 * @author Administrator
 * 
 */
public class OrgBalanceAlarmInfo {

	private Long memberCode; // 商户会员号
	private String memberName; // 商户名
	private Date beginDate; // 下次预警时间
	private String beginDateStr; //下次预警时间
	private Long minBalance; // 下限金额
	private Integer cycleType; // 报警周期0：半小时1：一小时2：按天
	private String cycleValue;// 报警周期值如周期为按天该字段为时间点取值范围为0-23
	private String mobile; // 手机号多个手机号以,号隔开
	private String email; // 邮箱多个邮箱号以,号隔开
	private Date deadline; // 服务截止日期
	private Integer status; // 状态 0:无效 1：有效
	private String smsTemplate; // 短信模板
	private String emailTemplate; // 邮件模板
	private Date createDate; // 创建时间
	private Date updateDate; // 更新时间

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Long getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(Long minBalance) {
		this.minBalance = minBalance;
	}

	public Integer getCycleType() {
		return cycleType;
	}

	public void setCycleType(Integer cycleType) {
		this.cycleType = cycleType;
	}

	public String getCycleValue() {
		return cycleValue;
	}

	public void setCycleValue(String cycleValue) {
		this.cycleValue = cycleValue;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
