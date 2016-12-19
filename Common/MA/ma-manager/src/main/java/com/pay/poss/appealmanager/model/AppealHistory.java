package com.pay.poss.appealmanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class AppealHistory  extends BaseObject{
  
	private static final long serialVersionUID = 1L;
    private Long appealHistoryId;
    private Long appealId;
    private String appealStatusCode;
    private Long operatorId;
    private Long operatorDeptCode;
    private String remark;
    private Date createTime;

    public Long getAppealHistoryId() {
        return appealHistoryId;
    }

    public void setAppealHistoryId(Long appealHistoryId) {
        this.appealHistoryId = appealHistoryId;
    }

	
	public Long getAppealId() {
		return appealId;
	}

	public void setAppealId(Long appealId) {
		this.appealId = appealId;
	}

	public String getAppealStatusCode() {
        return appealStatusCode;
    }

    public void setAppealStatusCode(String appealStatusCode) {
        this.appealStatusCode = appealStatusCode;
    }

  
    public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getOperatorDeptCode() {
		return operatorDeptCode;
	}

	public void setOperatorDeptCode(Long operatorDeptCode) {
		this.operatorDeptCode = operatorDeptCode;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}