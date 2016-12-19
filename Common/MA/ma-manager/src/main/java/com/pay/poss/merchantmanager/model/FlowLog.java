package com.pay.poss.merchantmanager.model;

import java.util.Date;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		FlowLog.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */
public class FlowLog {
    private Long flowId;//主键
    private String depart;//审核部门
    private Long operatorId;//操作员ID
    private String name;//操作员名称
    private String note;//处理意见
    private Long status;//当前状态
    private Date audiDate;//处理时间
    private Long memberCode;//会员号
    private Date createDate;//数据创建时间
    private Date updateDate;//数据更新时间

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

  
    public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getAudiDate() {
        return audiDate;
    }

    public void setAudiDate(Date audiDate) {
        this.audiDate = audiDate;
    }

    public Long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}