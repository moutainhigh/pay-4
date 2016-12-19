package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.util.Date;

import com.pay.poss.personmanager.enums.AcctStatusEnums;
import com.pay.poss.personmanager.enums.IdcStatusEnums;
import com.pay.poss.personmanager.enums.MemberEnums;

public class PersonMemberSearchDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long memberCode; // 会员号
	private String loginName;// 登陆名
	private String userName;// 会员真实姓名
	private Long status;// 会员状态
	private Long serviceLevelCode;// 会员等级
	private Date createDate;// 注册时间
	private Date updateDate;// 更新时间
	private Long acctStatus;// 账户状态
	private Long idcStatus;// 实名认证状态
	private Integer pageEndRow;// 结束行
	private Integer pageStartRow;// 起始行

	private String statusName;// 会员状态 (中文)
	private String acctStatusName;// 账户状态(中文)
	private String idcStatusName;// 实名认证
	private Long cerType;// 身份认证类型
	private String cerCode;// 身份证号码
	private Date registerStartDate;// 开始注册时间
	private Date registerEndDate;// 注册结束时间

	public Date getRegisterStartDate() {
		return registerStartDate;
	}

	public void setRegisterStartDate(Date registerStartDate) {
		this.registerStartDate = registerStartDate;
	}

	public Date getRegisterEndDate() {
		return registerEndDate;
	}

	public void setRegisterEndDate(Date registerEndDate) {
		this.registerEndDate = registerEndDate;
	}

	public Long getCerType() {
		return cerType;
	}

	public void setCerType(Long cerType) {
		this.cerType = cerType;
	}

	public String getCerCode() {
		return cerCode;
	}

	public void setCerCode(String cerCode) {
		this.cerCode = cerCode;
	}

	public String getIdcStatusName() {
		if (null == idcStatus) {
			idcStatusName = IdcStatusEnums.UNVALIDATE.getDescription();
		}
		else {
			idcStatusName = IdcStatusEnums.getByCode(idcStatus.intValue()).getDescription();
		}
		return idcStatusName;
	}

	public void setIdcStatusName(String idcStatusName) {
		this.idcStatusName = idcStatusName;
	}

	public String getAcctStatusName() {
		if (null == acctStatus) {
			return null;
		}
		acctStatusName = AcctStatusEnums.getByCode(acctStatus.intValue()).getDescription();
		return acctStatusName;
	}

	public void setAcctStatusName(String acctStatusName) {
		this.acctStatusName = acctStatusName;
	}

	public String getStatusName() {
		statusName = MemberEnums.getName(status.intValue());
		return statusName;

	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public Long getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(Long serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
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

	public Long getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(Long acctStatus) {
		this.acctStatus = acctStatus;
	}

	public Long getIdcStatus() {
		return idcStatus;
	}

	public void setIdcStatus(Long idcStatus) {
		this.idcStatus = idcStatus;
	}

}
