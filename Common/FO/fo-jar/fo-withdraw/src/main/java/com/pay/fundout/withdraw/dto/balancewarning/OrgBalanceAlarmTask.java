package com.pay.fundout.withdraw.dto.balancewarning;

import java.util.Date;

/**
 * 机构余额预警任务表
 * 
 * @author Administrator
 * 
 */
public class OrgBalanceAlarmTask {

	private Long recordNo; // 任务编号
	private Long memberCode; // 商户会员号
	private String memberName; // 商户名
	private Long balance; // 当前余额
	private String msgContent; // 消息内容
	private String contactInfo; // 联系方式
	private Date createDate; // 创建时间
	private Date updateDate; // 更新时间

	public Long getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Long recordNo) {
		this.recordNo = recordNo;
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

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
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
