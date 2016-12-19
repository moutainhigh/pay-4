package com.pay.poss.refund.common.model;

import com.pay.inf.model.BaseObject;

public class FileInfo extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String basePath; // 根目录
	private String fileFormat; // 文件格式 例：.xsl
	private String businessType; // 业务类型
	private String fileType; // 文件类型
	private String batchNum; // 批次号

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
}
