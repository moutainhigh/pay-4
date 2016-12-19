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
 * <p>反洗钱报文实体</p>
 * @author limeng
 * @since 2012-11-06
 * @see 
 */
public class MessageObject extends BaseObject {
	
	/*
	 * 主键
	 */
	private Long id;
	
	/*
	 * 报文文件名
	 */
	private String name;
	
	/*
	 * 报文分类，0表示可疑报文，1表示补充报文
	 */
	private Integer category;
	
	/*
	 * 具体报文种类，1表示普通报文，2表示纠错报文，3表示重发报文，4表示补报报文
	 */
	private Integer type;
	
	/*
	 * 报送时期，格式yyyyMMdd
	 */
	private String submitDate;
	
	/*
	 * 当日报送批次编号
	 */
	private String batchNo;
	
	/*
	 * 报文在该批次中的编号
	 */
	private String seqNo;
	
	/*
	 * 记录创建时间戳
	 */
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}