package com.pay.poss.authenticmanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class IdcVerifyBase extends BaseObject{
 
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
    private Integer status;

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
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

	public Integer getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}