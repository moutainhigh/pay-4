package com.pay.txncore.facade.dto;

import java.util.Date;

public class MaOperatorDTO {
	// 操作员主键
	private long operatorId;
	// 操作员登录名
	private String identity;
	// 会员号(管理员会员号)
	private long memberCode;
	// 操作员姓名
	private String name;
	// 状态
	private Integer status;
	// 邮件
	private String email;
	// 联系电话
	private String phone;
	// 手机号
	private String mobile;
	// 登录密码
	private String password;
	// 证件号
	private String certCode;
	// 创建日期
	private Date createDate;
	// 修改日期
	private Date updateDate;
	// 部门
	private String depart;
	// 备注
	private String note;
	// 证件类型
	private Long certType;

	private String payPassWord;

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(String payPassWord) {
		this.payPassWord = payPassWord;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
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

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getCertType() {
		return certType;
	}

	public void setCertType(Long certType) {
		this.certType = certType;
	}
}