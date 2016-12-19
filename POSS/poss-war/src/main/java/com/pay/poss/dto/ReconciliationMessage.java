/**
 * 
 */
package com.pay.poss.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.pay.file.parser.dto.FileParseResult;

/**
 * 对账单文件上传返回结果消息对象
 * @author Jiangbo.Peng
 *
 */
public class ReconciliationMessage {
	
	//总笔数
	private Integer totalRecord ;
	//文件解析结果
	private FileParseResult fileParseResult ;
	//private 
	//原始文件名
	private String orginalFileName; 
	
	//结果码
	private String responseCode ;
	//返回消息
	private String responseDesc ;
	
	
	/**
	 * @return the totalRecord
	 */
	
	/**
	 * @return the totalRecord
	 */
	public Integer getTotalRecord() {
		return totalRecord;
	}
	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @return the fileParseResult
	 */
	public FileParseResult getFileParseResult() {
		return fileParseResult;
	}
	/**
	 * @param fileParseResult the fileParseResult to set
	 */
	public void setFileParseResult(FileParseResult fileParseResult) {
		this.fileParseResult = fileParseResult;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseDesc
	 */
	public String getResponseDesc() {
		return responseDesc;
	}
	/**
	 * @param responseDesc the responseDesc to set
	 */
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	
	/**
	 * @return the orginalFileName
	 */
	public String getOrginalFileName() {
		return orginalFileName;
	}
	/**
	 * @param orginalFileName the orginalFileName to set
	 */
	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this) ;
	}
	
}
