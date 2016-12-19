 /** @Description 
 * @project 	poss-refund
 * @file 		RefundImportFile.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**对应银行状态文件导入表信息
 * @author sunsea_li
 *
 */
public class RefundImportFile extends BaseObject {
	private static final long serialVersionUID = -1304076874263592537L;
	private Long fileKy;//主键
	private String batchNum;//批次号
    private String bankCode;//银行编号
    private String fileName;//状态文件名称
    private Date uploadTime;//上传时间戳
    private String operators;//当前操作员登录号
    private Integer status;//
							    /*1：上传完毕
							    2：上传失败
							    2：解析成功
							    3：解析失败
							    4：导入数据库成功
							    5：对账完成
							    6：对账失败 */
    private String errorTips;//错误提示
    
	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorTips() {
		return errorTips;
	}

	public void setErrorTips(String errorTips) {
		this.errorTips = errorTips;
	}
}