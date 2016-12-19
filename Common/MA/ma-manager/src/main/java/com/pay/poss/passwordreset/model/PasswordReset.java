package com.pay.poss.passwordreset.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class PasswordReset extends BaseObject{
	private static final long serialVersionUID = 1L;
    private Long id;
	private Integer passwordType;
    private String formNumber; 
	private Integer status;
	private String loginName;
	private String proposerName;			//经办人姓名
	private String memberName;				//会员姓名
  
	private String idcard;
    private String mobile;
    private String proposerObverseUrl;
    private String proposerReverseurl;
    private String legalObverseUrl;
    private String legalReverseUrl;
    private String licenceUrl;
    private String formUrl;
    private String remark;
    
    private String value1;
    private String value2;
    
    private Date gmtCreate;
    private Date gmtModified;
    
    private String creator;
    private String modifier;
    private String isDeleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPasswordType() {
		return passwordType;
	}
	public void setPasswordType(Integer passwordType) {
		this.passwordType = passwordType;
	}
	public String getFormNumber() {
		return formNumber;
	}
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProposerObverseUrl() {
		return proposerObverseUrl;
	}
	public void setProposerObverseUrl(String proposerObverseUrl) {
		this.proposerObverseUrl = proposerObverseUrl;
	}
	public String getProposerReverseurl() {
		return proposerReverseurl;
	}
	public void setProposerReverseurl(String proposerReverseurl) {
		this.proposerReverseurl = proposerReverseurl;
	}
	public String getLegalObverseUrl() {
		return legalObverseUrl;
	}
	public void setLegalObverseUrl(String legalObverseUrl) {
		this.legalObverseUrl = legalObverseUrl;
	}
	public String getLegalReverseUrl() {
		return legalReverseUrl;
	}
	public void setLegalReverseUrl(String legalReverseUrl) {
		this.legalReverseUrl = legalReverseUrl;
	}
	public String getLicenceUrl() {
		return licenceUrl;
	}
	public void setLicenceUrl(String licenceUrl) {
		this.licenceUrl = licenceUrl;
	}
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	} 

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProposerName() {
		return proposerName;
	}
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}	
}
