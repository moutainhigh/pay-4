package com.pay.acc.member.model;

import java.util.Date;

/**
 * 会员登陆密码，支付密码验证次数
 * 
 * @author jim_chen
 * @version 2010-11-19
 */
public class MemberOperate {

	private Long id;
	private Long memberCode;// 会员号
	private Long type;// 类型(1登录密码2支付密码)
	private Integer accType;// 账户类型
	private Long failTime;//出错次数
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String value1;// 备用字段1
	private String value2;// 备用字段2
	private Long value3;//备用字段3
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

	public Integer getAccType() {
		return accType;
	}

	public void setAccType(Integer accType) {
		this.accType = accType;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	

	public Long getValue3() {
    	return value3;
    }

	public void setValue3(Long value3) {
    	this.value3 = value3;
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
}
