/**
 *  <p>File: BatchFileRecord.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.batchinfo.service.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p>生成的黑名单文件(xml)</p>
 * @author limeng
 * @since 2012-6-12
 * @see 
 */
public class BlackListFile extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * 主键
	 */
	private Long id;
	
	/*
	 * 文件名，规则：成员单位编码＋年月日时分秒.xml
	 */
	private String name;
	
	/*
	 * xml文件中支持存放的黑名单最大条目
	 */
	private int maxLength;
	
	/*
	 * 文件中当前条目数
	 */
	private int currentLength;
	
	/*
	 * 文件下载状态，0表示未下载，1表示已下载
	 */
	private String downloadStatus;
	
	/*
	 * 文件创建时间
	 */
	private Date createTime;
	
	/*
	 * 文件最新更新时间
	 */
	private Date updateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getCurrentLength() {
		return currentLength;
	}

	public void setCurrentLength(int currentLength) {
		this.currentLength = currentLength;
	}

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}