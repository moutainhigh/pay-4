package com.pay.poss.operatelogmanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file PossOperate.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-11-11 tianqing_wang Create
 */
public class PossOperate extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	private Long  		operateId;      //操作记录主键
	private String      loginName;		//操作该记录登录者
	private String 		objectCode ;    //被操作对象(会员号或账户号等)
	private Date        loginDate;		//操作该记录登录者登录时间
	private String      loginIp;		//操作该记录登录者IP
	private String      browserVer;		//操作该记录登录者浏览器
	private String 		actionUrl;		//操作动作
	private int   		type;			//操作类型
	private int 		status;			//操作状态
	private Date		createDate ;
	private Date		updateDate;
	private String      updateOperator; //账户类型
	
	
	
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getUpdateOperator() {
		return updateOperator;
	}
	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}
	public Long getOperateId() {
		return operateId;
	}
	public void setOperateId(Long operateId) {
		this.operateId = operateId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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