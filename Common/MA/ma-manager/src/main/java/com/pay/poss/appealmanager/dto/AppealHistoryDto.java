package com.pay.poss.appealmanager.dto;


public class AppealHistoryDto {
  
	
    private String appealHistoryId;
    private String appealId;
    private String appealStatusCode;
    private String operatorId;
    private String operatorDeptCode;
    private String remark;
    private String createTime;
    
	public String getAppealHistoryId() {
		return appealHistoryId;
	}
	public void setAppealHistoryId(String appealHistoryId) {
		this.appealHistoryId = appealHistoryId;
	}
	public String getAppealId() {
		return appealId;
	}
	public void setAppealId(String appealId) {
		this.appealId = appealId;
	}
	public String getAppealStatusCode() {
		return appealStatusCode;
	}
	public void setAppealStatusCode(String appealStatusCode) {
		this.appealStatusCode = appealStatusCode;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorDeptCode() {
		return operatorDeptCode;
	}
	public void setOperatorDeptCode(String operatorDeptCode) {
		this.operatorDeptCode = operatorDeptCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

   
}