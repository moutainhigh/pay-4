package com.pay.txncore.crosspay.model;

import java.io.Serializable;

/**
 * 商户下单邮件通知查询条件
 * @author davis.guo at 2016-08-31
 */
public class OrderEmailNotifyCriteria implements Serializable {

	private static final long serialVersionUID = 6333485103792012541L;
	
	private Long id ;
	private Long memberCode;//会员号
	private Long merchantCode;//商户号
	private String merchantName;//商户名称
	private String loginName;//登录名
	
	private String emailCompany ;//公司email
	private String emailNotify ;//通知email
	private Integer openFlag ;//0-未开通, 1-已开通
	private Integer operateStatus ;//防止并发操作状态标识:0-操作已完成,1-正在执行操作中;
	private String startDate;//开始时间
	private String endDate;//结束时间
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

	public Integer getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	
    
}