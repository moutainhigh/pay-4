package com.pay.poss.passwordreset.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

@SuppressWarnings("serial")
public class PasswordResetDto implements MutableDto {
	private Long id;
	private Integer passwordType; // 密码类型
	private String formNumber; // 申请单编号
	private Integer status; // 密码类型
	private String loginName; // 会员登录号
	private String proposerName; // 经办人姓名
	private String memberName; // 会员姓名
	private String idcard; // 经办人身份证
	private String mobile; // 经办人联系电话
	private String proposerObverseUrl; // 经办人身份证正面
	private String proposerReverseurl; // 经办人身份证背面
	private String legalObverseUrl; // 法人身份证正面
	private String legalReverseUrl; // 法人身份证背面
	private String licenceUrl; // 公司营业执照正本复印件
	private String formUrl; // 申请表影像
	private String remark; // 备注
	private String value1;
	private String value2;
	private Date gmtCreate;
	private Date gmtModified;
	private String creator;
	private String modifier;
	private String isDeleted;
	private String statusName;
	private Integer queryStatus; // 非历史查询状态(status为1未审核的)
	private Integer queryHisStatus; // 历史查询状态
	private Long memberCode; // 由loginName得到

	private String strGmtCreate; // 用于格式化后在页面显示的字段
	private String strGmtModified;
	private String hours24; // 是否24小时过期

	private String typeName; // 重置类型(登录密码重置还是支付密码重置)
	private String disposeFlag; // 处理结果(通过或者拒绝)

	private String startDate; // 查询历史的时间段
	private String endDate; // 查询历史的时间段

	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行

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

	public Integer getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(Integer queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getMemberName() {
		if (null != memberName && !"".equals(memberName)) {
			String str = memberName.trim();
			return str;
		} else {
			return memberName;
		}
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

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
		if (null != formNumber && !"".equals(formNumber)) {
			String str = formNumber.trim();
			return str;
		} else {
			return formNumber;
		}
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	public String getLoginName() {
		if (null != loginName && !"".equals(loginName)) {
			String str = loginName.trim();
			return str.toLowerCase();
		} else {
			return loginName;
		}
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

	public String getStrGmtCreate() {
		return strGmtCreate;
	}

	public void setStrGmtCreate(String strGmtCreate) {
		this.strGmtCreate = strGmtCreate;
	}

	public String getStrGmtModified() {
		return strGmtModified;
	}

	public void setStrGmtModified(String strGmtModified) {
		this.strGmtModified = strGmtModified;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDisposeFlag() {
		return disposeFlag;
	}

	public void setDisposeFlag(String disposeFlag) {
		this.disposeFlag = disposeFlag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getQueryHisStatus() {
		return queryHisStatus;
	}

	public void setQueryHisStatus(Integer queryHisStatus) {
		this.queryHisStatus = queryHisStatus;
	}

	public String getHours24() {
		return hours24;
	}

	public void setHours24(String hours24) {
		this.hours24 = hours24;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
}
