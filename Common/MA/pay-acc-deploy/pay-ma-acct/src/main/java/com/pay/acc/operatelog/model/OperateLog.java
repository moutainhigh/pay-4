package com.pay.acc.operatelog.model;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private Long id;// 主键
	private String objectCode;// 被操作的对象（会员号/账户号）
	private String loginName;//操作员登陆名
	private Date loginDate;//登录时间
	private String loginIp;//登陆IP
	private String browserVer;//浏览器类型
	private String actionUrl;//操作动作
	private Long type;//类型
	private Long status;//状态
	private Date createDate;
	private Date updateDate;

	public Long getId() {
    	return id;
    }
	public void setId(Long id) {
    	this.id = id;
    }
	public String getObjectCode() {
    	return objectCode;
    }
	public void setObjectCode(String objectCode) {
    	this.objectCode = objectCode;
    }
	public String getLoginName() {
    	return loginName;
    }
	public void setLoginName(String loginName) {
    	this.loginName = loginName;
    }
	public Date getLoginDate() {
    	return loginDate;
    }
	public void setLoginDate(Date loginDate) {
    	this.loginDate = loginDate;
    }
	public String getLoginIp() {
    	return loginIp;
    }
	public void setLoginIp(String loginIp) {
    	this.loginIp = loginIp;
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
	public Long getType() {
    	return type;
    }
	public void setType(Long type) {
    	this.type = type;
    }
	public Long getStatus() {
    	return status;
    }
	public void setStatus(Long status) {
    	this.status = status;
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
