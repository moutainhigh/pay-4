package com.pay.base.model;

import java.util.Date;

import com.pay.app.model.Model;

/**
 * 会员登陆密码，支付密码验证次数
 * 
 * @author jim_chen
 * @version 2010-11-19
 */
public class MemberOperate implements Model{

	private Long id;
	private Long memberCode;// 会员号
	private Long type;// 类型(1登录密码2支付密码)
	private Long accType;// 账户类型
	private Long failTime;//出错次数
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String operatorId;// 操作员Id
	private String value2;// 备用字段2
	private Long memberType;//会员类型 1-个人 2-企业
	private Long value4;//备用字段4
	private String value5;//备用字段5
	private Date lastLoginTime;//上一次登录时间
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getAccType() {
		return accType;
	}

	public void setAccType(Long accType) {
		this.accType = accType;
	}

	

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	

	

	public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Long getMemberType() {
        return memberType;
    }

    public void setMemberType(Long memberType) {
        this.memberType = memberType;
    }

    public Long getValue4() {
    	return value4;
    }

	public void setValue4(Long value4) {
    	this.value4 = value4;
    }

	public String getValue5() {
    	return value5;
    }

	public void setValue5(String value5) {
    	this.value5 = value5;
    }

	public Date getLastLoginTime() {
    	return lastLoginTime;
    }

	public void setLastLoginTime(Date lastLoginTime) {
    	this.lastLoginTime = lastLoginTime;
    }
	
	public Long getFailTime() {
    	return failTime;
    }

	public void setFailTime(Long failTime) {
    	this.failTime = failTime;
    }

	public Date getCreateTime() {
    	return createTime;
    }

	public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }

	public Date getUpdateTime() {
    	return updateTime;
    }

	public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }
	public void setPrimaryKey(Long id) {
        setId(id);
    }
}
