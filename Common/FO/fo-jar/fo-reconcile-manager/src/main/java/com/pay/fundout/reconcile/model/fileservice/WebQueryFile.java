 /** @Description 
 * @project 	fo-reconcile-manager
 * @file 		WebQueryFileDTO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
*/
package com.pay.fundout.reconcile.model.fileservice;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p>查询条件Model</p>
 * @author Henry.Zeng
 * @since 2010-10-2
 * @see 
 */
public class WebQueryFile extends BaseObject{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 银行科目编号
	 */
	private String orgCode;
	
	/**
	 * 起始时间
	 */
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	private Date endDate ;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 业务状态
	 */
	private Integer busiStatus;
	
	private String fileIds;
	
	public String getFileIds() {
	    return fileIds;
	}
	public void setFileIds(String fileIds) {
	    this.fileIds = fileIds;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getBusiStatus() {
		return busiStatus;
	}
	public void setBusiStatus(Integer busiStatus) {
		this.busiStatus = busiStatus;
	} 
	
	


}
