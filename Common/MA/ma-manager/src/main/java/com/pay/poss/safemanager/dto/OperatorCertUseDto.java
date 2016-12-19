/**
 * 
 */
package com.pay.poss.safemanager.dto;

import java.util.Date;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertUseDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-22		DaiDeRong			Create
 */
public class OperatorCertUseDto {
	
	private Long certManageId;
	private String usePalce;
	private Integer status ;
	private Date createDate;
	private String userDn;
	/**
	 * @return the usePalce
	 */
	public String getUsePalce() {
		return usePalce;
	}
	/**
	 * @param usePalce the usePalce to set
	 */
	public void setUsePalce(String usePalce) {
		this.usePalce = usePalce;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the userDn
	 */
	public String getUserDn() {
		return userDn;
	}
	/**
	 * @param userDn the userDn to set
	 */
	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}
	/**
	 * @return the certManageId
	 */
	public Long getCertManageId() {
		return certManageId;
	}
	/**
	 * @param certManageId the certManageId to set
	 */
	public void setCertManageId(Long certManageId) {
		this.certManageId = certManageId;
	}
	
	
	
	

}
