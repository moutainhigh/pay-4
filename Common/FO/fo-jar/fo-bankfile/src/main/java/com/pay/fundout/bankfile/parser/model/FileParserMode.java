/**
 *  File: FileParserMode.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.bankfile.parser.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class FileParserMode extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3136695356485959065L;
	
	private Long fileNo;	//文件编号
	
	private Integer status;	//文件状态
	
	private String batchNum;//批次号
	
	private String bankCode;//银行编号
	
	private String fileName;//文件名称
	
	private Date uploadTime;//更新时间
	
	private String errorTips;//错误信息
	
	private String operators;//操作员
	
	private  Long  category;//类别1复核2结果
	
	private Integer fileBusiType;//文件类型0对私1对公
	
	private Long gFileKy;//生成文件ky

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the errorTips
	 */
	public String getErrorTips() {
		return errorTips;
	}

	/**
	 * @param errorTips the errorTips to set
	 */
	public void setErrorTips(String errorTips) {
		this.errorTips = errorTips;
	}

	/**
	 * @return the operators
	 */
	public String getOperators() {
		return operators;
	}

	/**
	 * @param operators the operators to set
	 */
	public void setOperators(String operators) {
		this.operators = operators;
	}

	/**
	 * @return the category
	 */
	public Long getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Long category) {
		this.category = category;
	}

	/**
	 * @return the fileBusiType
	 */
	public Integer getFileBusiType() {
		return fileBusiType;
	}

	/**
	 * @param fileBusiType the fileBusiType to set
	 */
	public void setFileBusiType(Integer fileBusiType) {
		this.fileBusiType = fileBusiType;
	}

	/**
	 * @return the gFileKy
	 */
	public Long getgFileKy() {
		return gFileKy;
	}

	/**
	 * @param gFileKy the gFileKy to set
	 */
	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}

}
