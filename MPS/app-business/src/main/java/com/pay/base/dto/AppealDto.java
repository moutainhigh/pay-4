package com.pay.base.dto;

import java.util.Date;

public class AppealDto {
	
	    private Long appealId;
	    private String appealCode;  // 申诉号
	    private String customerName;
	    private String callPhone;
	    private String linkPhone;
	    private String linkEmail;
	    private Date occurTime;
	    private String appealBody;   //申诉内容
	    private Integer isUrgency;
	    private Integer isNeedReply;
	    private String appealSourceCode;     //申诉来源
	    private String businessTypeCode;
	    private String productBigTypeCode;
	    private String productLittleTypeCode;
	    private String customerReplyCode;
	    private Integer isSelfDuty;
	    private String appealStatusCode;      //申诉状态
	    private Date createTime;            //创建时间
	    private Date updateTime;            //更新时间
	    private String appealTypeCode;
	    private String appealLevelCode;
	    private String appealDeptCode;
	    
	    
		public Long getAppealId() {
			return appealId;
		}
		public void setAppealId(Long appealId) {
			this.appealId = appealId;
		}
		public String getAppealCode() {
			return appealCode;
		}
		public void setAppealCode(String appealCode) {
			this.appealCode = appealCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCallPhone() {
			return callPhone;
		}
		public void setCallPhone(String callPhone) {
			this.callPhone = callPhone;
		}
		public String getLinkPhone() {
			return linkPhone;
		}
		public void setLinkPhone(String linkPhone) {
			this.linkPhone = linkPhone;
		}
		public String getLinkEmail() {
			return linkEmail;
		}
		public void setLinkEmail(String linkEmail) {
			this.linkEmail = linkEmail;
		}
		public Date getOccurTime() {
			return occurTime;
		}
		public void setOccurTime(Date occurTime) {
			this.occurTime = occurTime;
		}
		public String getAppealBody() {
			return appealBody;
		}
		public void setAppealBody(String appealBody) {
			this.appealBody = appealBody;
		}
		public String getTitle(){
			if(appealBody == null){
				return "";
			}
			int i = appealBody.indexOf(", 内容:");
			if(i != -1){
				int j = appealBody.indexOf("标题:");
				if(j != -1){
					j = j+3;
				}else{
					j = 0;
				}
				return appealBody.substring(j, i);
			}
			return "";
		}
		public String getContent(){
			
			if(appealBody == null){
				return "";
			}
			int i = appealBody.indexOf(", 内容:");
			if(i != -1){
				
				return appealBody.substring(i + ", 内容:".length());
			}
			return appealBody;
		}
		public Integer getIsUrgency() {
			return isUrgency;
		}
		public void setIsUrgency(Integer isUrgency) {
			this.isUrgency = isUrgency;
		}
		public Integer getIsNeedReply() {
			return isNeedReply;
		}
		public void setIsNeedReply(Integer isNeedReply) {
			this.isNeedReply = isNeedReply;
		}
		public String getAppealSourceCode() {
			return appealSourceCode;
		}
		public void setAppealSourceCode(String appealSourceCode) {
			this.appealSourceCode = appealSourceCode;
		}
		public String getBusinessTypeCode() {
			return businessTypeCode;
		}
		public void setBusinessTypeCode(String businessTypeCode) {
			this.businessTypeCode = businessTypeCode;
		}
		public String getProductBigTypeCode() {
			return productBigTypeCode;
		}
		public void setProductBigTypeCode(String productBigTypeCode) {
			this.productBigTypeCode = productBigTypeCode;
		}
		public String getProductLittleTypeCode() {
			return productLittleTypeCode;
		}
		public void setProductLittleTypeCode(String productLittleTypeCode) {
			this.productLittleTypeCode = productLittleTypeCode;
		}
		public String getCustomerReplyCode() {
			return customerReplyCode;
		}
		public void setCustomerReplyCode(String customerReplyCode) {
			this.customerReplyCode = customerReplyCode;
		}
		public Integer getIsSelfDuty() {
			return isSelfDuty;
		}
		public void setIsSelfDuty(Integer isSelfDuty) {
			this.isSelfDuty = isSelfDuty;
		}
		public String getAppealStatusCode() {
			return appealStatusCode;
		}
		public void setAppealStatusCode(String appealStatusCode) {
			this.appealStatusCode = appealStatusCode;
		}

		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public String getAppealTypeCode() {
			return appealTypeCode;
		}
		public void setAppealTypeCode(String appealTypeCode) {
			this.appealTypeCode = appealTypeCode;
		}
		public String getAppealLevelCode() {
			return appealLevelCode;
		}
		public void setAppealLevelCode(String appealLevelCode) {
			this.appealLevelCode = appealLevelCode;
		}
		public String getAppealDeptCode() {
			return appealDeptCode;
		}
		public void setAppealDeptCode(String appealDeptCode) {
			this.appealDeptCode = appealDeptCode;
		}
	    
}
