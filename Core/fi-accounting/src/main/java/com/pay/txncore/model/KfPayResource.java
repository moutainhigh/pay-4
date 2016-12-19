package com.pay.txncore.model;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the KF_PAY_RESOURCE database table.
 * 
 */
public class KfPayResource implements Serializable {
	private static final long serialVersionUID = 1L;

	private long resourceNo;

	private String batchNo;

	private String fileName;

	private String filePath;

	private long fileType;

	private String url;
	private Date createData;
	private String batchNos;
	public String getBatchNos() {
		return batchNos;
	}

	public void setBatchNos(String batchNos) {
		this.batchNos = batchNos;
	}

	public KfPayResource() {
	}

	public long getResourceNo() {
		return this.resourceNo;
	}

	public void setResourceNo(long resourceNo) {
		this.resourceNo = resourceNo;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileType() {
		return this.fileType;
	}

	public void setFileType(long fileType) {
		this.fileType = fileType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateData() {
		return createData;
	}

	public void setCreateData(Date createData) {
		this.createData = createData;
	}

	@Override
	public String toString() {
		return "KfPayResource [resourceNo=" + resourceNo + ", batchNo=" + batchNo + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", fileType=" + fileType + ", url=" + url + ", createData=" + createData
				+ "]";
	}

}