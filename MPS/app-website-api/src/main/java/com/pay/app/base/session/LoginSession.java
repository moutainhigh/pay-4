/**
 *  File: LoginSession.java
 *  Description:
 *  Copyright 2006-2010 woyo Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-7-22   terry_ma     Create
 *
 */
package com.pay.app.base.session;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Jinhaha
 *
 */
public class LoginSession implements Serializable{

	private static final long serialVersionUID = -4573390616547142891L;
	private String userName;
	//会员登录名
	private String loginName;

	private String secondaryUsername;
	private String secondaryId;
	//会员真实姓名
	private String verifyName;
	//会员问候语
	private String salutatory;
	private Date updateDate;
	private Long balance;
	private String memberCode;
	private long fathermemberCode;
	
	//会员类型  1-个人会员 2-企业会员
	private int memberType;
	
	//会员安全等级
	private int securityLvl;
	private int securityCount=0;
	private String serialNo;
	//会员菜单类型     2-企业3-个人 10-交易中心
	private int scaleType=3;
	//企业会员操作员ID
	private Long operatorId=0L;
	// 操作员登录名
	private String operatorIdentity;
	private Long operatorExtId=0L;
	// 服务级别代码
	private Integer serviceLevel;
	//登录失败次数
	private Map<String,Object> loginFailNum = new HashMap<String,Object>();
	//是否数字证书用户
	private boolean isCertUser=false;
	//是否本地已安装数字证书
	private boolean isLocalInstall=false;
	
	/********针对交易中心新增*********/
	private Long bspUid;
	//交易中心管理员会员号
	private Long bspMemberCode;
	//交易中心管理员操作员Id
	private Long bspOperatorId;
	//交易中心会员身份标识 0-普通企业会员 1-交易中心管理员 2-交易商
	private int bspIdentity=0;
	//交易中心来源  1-普通企业会员  3-广钢
	private int bspOrgCode=1;
	//钢网账号 
	private String usteelName;
	//
	private String openID;
	
	public long getFathermemberCode() {
		return fathermemberCode;
	}
	
	public void setFathermemberCode(long fathermemberCode) {
		this.fathermemberCode = fathermemberCode;
	}
	
    public Long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public int getScaleType() {
        return scaleType;
    }
    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    public int getSecurityLvl() {
        return securityLvl;
    }
    public void setSecurityLvl(int securityLvl) {
        this.securityLvl = securityLvl;
    }
    public int getSecurityCount() {
        return securityCount;
    }
    public void setSecurityCount(int securityCount) {
        this.securityCount = securityCount;
    }
    private String activeStatus="0";
	
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getVerifyName() {
		return verifyName;
	}
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	public String getSalutatory() {
		return salutatory;
	}
	public void setSalutatory(String salutatory) {
		this.salutatory = salutatory;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSecondaryUsername() {
		return secondaryUsername;
	}
	public void setSecondaryUsername(String secondaryUsername) {
		this.secondaryUsername = secondaryUsername;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Map<String, Object> getLoginFailNum() {
		return loginFailNum;
	}
	public void setLoginFailNum(Map<String, Object> loginFailNum) {
		this.loginFailNum = loginFailNum;
	}
	public void setOperatorIdentity(String operatorIdentity) {
		this.operatorIdentity = operatorIdentity;
	}
	public String getOperatorIdentity() {
		return operatorIdentity;
	}
	public Long getOperatorExtId() {
		return operatorExtId;
	}
	public void setOperatorExtId(Long operatorExtId) {
		this.operatorExtId = operatorExtId;
	}
	public Integer getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(Integer serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public int getMemberType() {
		return memberType;
	}
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	public Long getBspMemberCode() {
		return bspMemberCode;
	}
	public void setBspMemberCode(Long bspMemberCode) {
		this.bspMemberCode = bspMemberCode;
	}
	public Long getBspOperatorId() {
		return bspOperatorId;
	}
	public void setBspOperatorId(Long bspOperatorId) {
		this.bspOperatorId = bspOperatorId;
	}
	public int getBspIdentity() {
		return bspIdentity;
	}
	public void setBspIdentity(int bspIdentity) {
		this.bspIdentity = bspIdentity;
	}
	public int getBspOrgCode() {
		return bspOrgCode;
	}
	public void setBspOrgCode(int bspOrgCode) {
		this.bspOrgCode = bspOrgCode;
	}
	public String getUsteelName() {
		return usteelName;
	}
	public void setUsteelName(String usteelName) {
		this.usteelName = usteelName;
	}
	public Long getBspUid() {
		return bspUid;
	}
	public void setBspUid(Long bspUid) {
		this.bspUid = bspUid;
	}
	public boolean isCertUser() {
		return isCertUser;
	}
	public void setCertUser(boolean isCertUser) {
		this.isCertUser = isCertUser;
	}
	public boolean isLocalInstall() {
		return isLocalInstall;
	}
	public void setLocalInstall(boolean isLocalInstall) {
		this.isLocalInstall = isLocalInstall;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	@Override
	public String toString() {
		return "LoginSession [userName=" + userName + ", loginName="
				+ loginName + ", secondaryUsername=" + secondaryUsername
				+ ", secondaryId=" + secondaryId + ", verifyName=" + verifyName
				+ ", salutatory=" + salutatory + ", updateDate=" + updateDate
				+ ", balance=" + balance + ", memberCode=" + memberCode
				+ ", memberType=" + memberType + ", securityLvl=" + securityLvl
				+ ", securityCount=" + securityCount + ", serialNo=" + serialNo
				+ ", scaleType=" + scaleType + ", operatorId=" + operatorId
				+ ", operatorIdentity=" + operatorIdentity + ", operatorExtId="
				+ operatorExtId + ", serviceLevel=" + serviceLevel
				+ ", loginFailNum=" + loginFailNum + ", isCertUser="
				+ isCertUser + ", isLocalInstall=" + isLocalInstall
				+ ", bspUid=" + bspUid + ", bspMemberCode=" + bspMemberCode
				+ ", bspOperatorId=" + bspOperatorId + ", bspIdentity="
				+ bspIdentity + ", bspOrgCode=" + bspOrgCode + ", usteelName="
				+ usteelName + ", openID=" + openID + ", activeStatus="
				+ activeStatus + "]";
	}
	
}
