/**
 *  File: MemberInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-19      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import java.util.List;

import com.pay.inf.model.BaseObject;

/**封装和传递会员信息
 * @author Sunsea.Li
 *
 */
public class WfRoleDTO extends BaseObject {
	private static final long serialVersionUID = 4371676212192595831L;
	
	private String nodeId;		
	private String nodeName;	
    private String parentId;
    private List<WfRoleDTO>  childs;
	public List<WfRoleDTO> getChilds() {
		return childs;
	}
	public void setChilds(List<WfRoleDTO> childs) {
		this.childs = childs;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}	
	
}
