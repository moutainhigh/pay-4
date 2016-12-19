package com.pay.app.model;

import java.util.Date;

public class IdcVerifyGov implements Model  {
    private Long id;
    private Long idcVerifyBaseId;
    private String photo;
    private Date createdDate;
    private String errorCode;
    private String errorMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdcVerifyBaseId() {
        return idcVerifyBaseId;
    }

    public void setIdcVerifyBaseId(Long idcVerifyBaseId) {
        this.idcVerifyBaseId = idcVerifyBaseId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
	public void setPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		
	}
}