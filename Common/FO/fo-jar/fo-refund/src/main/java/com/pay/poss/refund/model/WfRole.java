/**
 *  File: MemberInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-19      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.poss.refund.model;


/**封装和传递会员信息
 * @author Sunsea.Li
 *
 */
public class WfRole  {
  private Long	wfRoleKey;
  private Long	roleKey;
  private String	roleName;
  private Long	nodeKey;
  private String	nodeName;
  private String	status;
  private String  remark;
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public String getNodeName() {
	return nodeName;
}
public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
}
public Long getWfRoleKey() {
	return wfRoleKey;
}
public void setWfRoleKey(Long wfRoleKey) {
	this.wfRoleKey = wfRoleKey;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public Long getRoleKey() {
	return roleKey;
}
public void setRoleKey(Long roleKey) {
	this.roleKey = roleKey;
}
public Long getNodeKey() {
	return nodeKey;
}
public void setNodeKey(Long nodeKey) {
	this.nodeKey = nodeKey;
}


}
