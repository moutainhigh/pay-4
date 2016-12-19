/**
 *  File: BspAuditCommand.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jul 4, 2011   terry ma      create
 */
package com.pay.controller.fo.bsp;

public class BspAuditCommand {

	private String startTime;
	private String endTime;
	private String memberName;
	private String pager_offset;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPager_offset() {
		return pager_offset;
	}
	public void setPager_offset(String pager_offset) {
		this.pager_offset = pager_offset;
	}
	
	
}
