package com.pay.base.dto;

import java.util.Date;

public class AppealHistoryDto {
	
	    private Long appealHistoryId;
	    private Long appealId;
	    private String appealStatusCode;
	    private Long operatorId;
	    private String remark;
	    private Date createTime;
	    private Long operatorDeptCode;
	  
	  
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
		public Long getOperatorDeptCode() {
			return operatorDeptCode;
		}
		public void setOperatorDeptCode(Long operatorDeptCode) {
			this.operatorDeptCode = operatorDeptCode;
		}
	  
}
