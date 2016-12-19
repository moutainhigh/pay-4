package com.pay.acc.member.dto;

import java.io.Serializable;
import java.util.Date;

public class IdcVerifyDto implements Serializable {
	private static final long serialVersionUID = 1L;
    private Long id;
    private Long memberCode;
    private String name;
    private String paperNo;
    private String linkNo;
    private Integer verifyFlag;
    private Date createdDate;
    private Integer paperType;
    private Integer isPaperFile;
    private String photo;
    private Integer status;
    private String errorCode;
    private String errorMsg;
    
    
    
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	public String getLinkNo() {
		return linkNo;
	}
	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}
	public Integer getVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getPaperType() {
		return paperType;
	}
	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}
	public Integer getIsPaperFile() {
		return isPaperFile;
	}
	public void setIsPaperFile(Integer isPaperFile) {
		this.isPaperFile = isPaperFile;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	@Override
	public String toString() {
		return "IdcVerifyDto [createdDate=" + createdDate + ", errorCode="
				+ errorCode + ", errorMsg=" + errorMsg + ", id=" + id
				+ ", isPaperFile=" + isPaperFile + ", linkNo=" + linkNo
				+ ", memberCode=" + memberCode + ", name=" + name
				+ ", paperNo=" + paperNo + ", paperType=" + paperType
				+ ", photo=" + photo + ", status=" + status + ", verifyFlag="
				+ verifyFlag + "]";
	}

    
    
    
}