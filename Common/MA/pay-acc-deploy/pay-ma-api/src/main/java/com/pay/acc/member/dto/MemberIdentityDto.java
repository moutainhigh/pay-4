package com.pay.acc.member.dto;

import java.io.Serializable;
import java.util.Date;

/*
 * 客户标识
 * @author peng jiong
 */
public final class MemberIdentityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1792650116809456318L;

	private Long memberCode;

	// 标识类型
	private Integer idType;

	// 标识内容
	private String idContent;

	// 状态,0,未验证，1验证过，2验证中，-1没有
	private Integer status;

	// 创建日期
	private Date createDate;

	/**
	 * 是否是主标识.
	 *
	 */
	private Integer isPrimaryId;

	private Date validationDate;

	private Date updateDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public String getIdContent() {
		return idContent;
	}

	public void setIdContent(final String idContent) {
		this.idContent = idContent;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(final Integer idType) {
		this.idType = idType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * @return isPrimaryID
	 */
	public Integer getIsPrimaryId() {
		return isPrimaryId;
	}

	/**
	 * @param isPrimaryID
	 *            要设置的 isPrimaryID
	 */
	public void setIsPrimaryId(Integer isPrimaryId) {
		this.isPrimaryId = isPrimaryId;
	}

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("createDate->" + createDate + ";");
		sb.append("idContent->" + idContent + ";");
		sb.append("idtype->" + idType + ";");
		sb.append("isPrimaryId->" + isPrimaryId + ";");
		sb.append("status->" + status + ";");
		sb.append("validationDate->" + validationDate + ";");
		return sb.toString();
	}
}
