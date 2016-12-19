package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.util.Date;

public class UserLogDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long logId;
	private Long operatorId;// 操作员主键
	private String loginName;// 登陆名
	private String loginIp;// 登陆IP
	private Date loginDate;// 登陆日期
	private String browserVer;// 浏览器
	private String actionUrl;// 操作动作
	private Long memberCode;
	private String name;
	private Date createDate;
	private Date updateDate;
	// 1.登录2.支付3.付款4.充值5.确认付款6.余额查询7.交易查询8.设置安全问题9.修改支付密码10.订阅通知11.添加联系人12.找回支付密码13.修改问候语14.提现15.补全资料
	private String logType;
	private String ipSite;// IP参考位置

	private Integer pageEndRow;// 结束行
	private Integer pageStartRow;// 起始行

	private Date loginStartDate;// 登陆开始时间
	private Date loginEndDate;// 登陆结束时间

	public Date getLoginStartDate() {
		return loginStartDate;
	}

	public void setLoginStartDate(Date loginStartDate) {
		this.loginStartDate = loginStartDate;
	}
	
	public Date getLoginEndDate() {
		return loginEndDate;
	}

	public void setLoginEndDate(Date loginEndDate) {
		this.loginEndDate = loginEndDate;
	}

	public String getIpSite() {
		return ipSite;
	}

	public void setIpSite(String ipSite) {
		this.ipSite = ipSite;
	}

	public Integer getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public Integer getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getBrowserVer() {
		return browserVer;
	}

	public void setBrowserVer(String browserVer) {
		this.browserVer = browserVer;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

}
