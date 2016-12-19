
package com.pay.poss.dto;

import java.util.Date;

/**
 * 商户下单邮件通知实体类
 * @author davis.guo at 2016-08-31
 */
public class OrderEmailNotifyDTO {
	
	private Long id ;
	private Long memberCode;//会员号
	private Long merchantCode;//商户号
	private String merchantName;//商户名称
	private String loginName;//登录名
	
	private String emailCompany ;//公司email
	private String emailNotify ;//通知email
	private Integer openFlag ;//0-未开通, 1-已开通
	private Integer operateStatus ;//防止并发操作状态标识:0-操作已完成,1-正在执行操作中;
	private Date createDate ;//创建时间
	private Date updateDate ;//修改时间
	private String operator ;//操作员
	private Long maxTradeOrderNo ;//当前最大网关订单号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmailCompany() {
		return emailCompany;
	}

	public void setEmailCompany(String emailCompany) {
		this.emailCompany = emailCompany;
	}

	public String getEmailNotify() {
		return emailNotify;
	}

	public void setEmailNotify(String emailNotify) {
		this.emailNotify = emailNotify;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public Integer getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getMaxTradeOrderNo() {
		return maxTradeOrderNo;
	}

	public void setMaxTradeOrderNo(Long maxTradeOrderNo) {
		this.maxTradeOrderNo = maxTradeOrderNo;
	}

	@Override
	public String toString() {
		return "EmailNotifyDTO [id=" + id + ", memberCode=" + memberCode + ", merchantCode=" + merchantCode + ", merchantName=" + merchantName
				+ ", loginName=" + loginName + ", emailCompany=" + emailCompany + ", emailNotify=" + emailNotify + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", openFlag=" + openFlag + ", operator=" + operator + ", maxTradeOrderNo=" + maxTradeOrderNo + "]";
	}
	
	
}
