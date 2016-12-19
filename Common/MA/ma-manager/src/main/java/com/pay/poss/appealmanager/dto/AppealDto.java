package com.pay.poss.appealmanager.dto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-19		gungun_zhang			Create
 */

public class AppealDto {
	//appeal
    private String appealId;
    private String appealCode;
    private String customerName;
    private String callPhone;
    private String linkPhone;
    private String linkEmail;
    private String occurTime;
    private String appealBody;
    private String isUrgency;
    private String isNeedReply;
    private String appealSourceCode;
    private String businessTypeCode;
    private String productBigTypeCode;
    private String productLittleTypeCode;
    private String customerReplyCode;
    private String appealTypeCode;
    private String appealLevelCode;
    private String appealDeptCode;
    private String isSelfDuty;
    private String appealStatusCode;

    private String remark;
    private String createTime;
    private String updateTime;
    //appealHistory
    private String operatorId;
    private String operatorDeptCode;
    
	public String getAppealId() {
		return appealId;
	}
	public void setAppealId(String appealId) {
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
	public String getLinkEmail() {
		return linkEmail;
	}
	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getAppealBody() {
		return appealBody;
	}
	public void setAppealBody(String appealBody) {
		this.appealBody = appealBody;
	}
	public String getFormatAppealBody(){
		if(appealBody == null){
			return "";
		}
		
		if(appealBody.contains("标题:") && appealBody.contains("内容:")){
			return appealBody.replace("标题:", "标题:\n").replace("内容:", "\n内容:\n");
		}else{
			return appealBody;
		}
	}
	public String getIsUrgency() {
		return isUrgency;
	}
	public void setIsUrgency(String isUrgency) {
		this.isUrgency = isUrgency;
	}
	public String getIsNeedReply() {
		return isNeedReply;
	}
	public void setIsNeedReply(String isNeedReply) {
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
	public String getIsSelfDuty() {
		return isSelfDuty;
	}
	public void setIsSelfDuty(String isSelfDuty) {
		this.isSelfDuty = isSelfDuty;
	}
	public String getAppealStatusCode() {
		return appealStatusCode;
	}
	public void setAppealStatusCode(String appealStatusCode) {
		this.appealStatusCode = appealStatusCode;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
    
}