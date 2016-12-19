/**
 *  File: ResWorkflow.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-23    jason_wang      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class ResWorkflow extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 683251175394474525L;
	
	private Long nodeKy;		//NODE_KY	NUMBER			
	private String nodeCode;	//NODE_CODE	VARCHAR2(64)			
	private String nodeName;	//NODE_NAME	VARCHAR2(64)			
	private String workflowName;	//WORKFLOW_NAME	VARCHAR2(64)			
	private String workflowCode;	//WORKFLOW_CODE	VARCHAR2(64)			
	private Integer position;		//POSITION	NUMBER			
	private Integer status;			//STATUS	NUMBER(3)		1	
	private String remark;			//REMARK	VARCHAR2(200)	Y		
	
	public Long getNodeKy() {
		return nodeKy;
	}
	public void setNodeKy(Long nodeKy) {
		this.nodeKy = nodeKy;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getWorkflowCode() {
		return workflowCode;
	}
	public void setWorkflowCode(String workflowCode) {
		this.workflowCode = workflowCode;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
