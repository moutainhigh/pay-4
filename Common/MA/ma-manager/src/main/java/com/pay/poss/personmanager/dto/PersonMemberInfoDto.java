package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.util.Date;

import com.pay.poss.personmanager.enums.AcctStatusEnums;
import com.pay.poss.personmanager.enums.IdcStatusEnums;
import com.pay.poss.personmanager.enums.MemberEnums;

public class PersonMemberInfoDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private String loginName;			// 登陆名
	private Long memberCode; 			// 会员号
	private String userName;			// 会员真实姓名
	private Long status;			    // 会员状态
	private Date createDate;			// 注册时间
	private Date updateDate;			// 更新时间
	private String email;				//会员email
	private String mobile ;				//会员手机
	
	private String statusName;
	private String createDateName;

	private String levelCode;    //安全级别
	private String ssoId;    //社区账户
	
	
	private String address;      //地址
	private String cerCode;      //身份证号码
	private String bankAcct;      //银行账号
	private String isPaperFile;      //身份证验证等级
	
	
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	
	private String sex;
	private String profession;
	private String country;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getStatusName() {
		if(null != status && !"".equals(status)){
			statusName = MemberEnums.getByCode(status.intValue()).getDescription();
			return statusName;
		}else{
			return "";
		}
	}
	public void setStatusName(String statusName) {
		
		this.statusName = statusName;
	}
	
	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCreateDateName() {
		return createDateName;
	}
	public void setCreateDateName(String createDateName) {
		this.createDateName = createDateName;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCerCode() {
		return cerCode;
	}
	public void setCerCode(String cerCode) {
		this.cerCode = cerCode;
	}
	public String getBankAcct() {
		return bankAcct;
	}
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public String getIsPaperFile() {
		return isPaperFile;
	}
	public void setIsPaperFile(String isPaperFile) {
		this.isPaperFile = isPaperFile;
	}
}
