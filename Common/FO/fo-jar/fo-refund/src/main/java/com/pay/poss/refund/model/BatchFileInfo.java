 /** @Description 
 * @project 	poss-refund
 * @file 		BatchFileInfo.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

/**对应文件信息表信息
 * @author sunsea_li
 *
 */
import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

public class BatchFileInfo extends BaseObject {
	private static final long serialVersionUID = -3150923747413267025L;
	private Long fileKy;//主键
	private String batchNum;//批次号
    private String filePath;//文件路径
    private String fileName;//文件名称
    private Integer fileType;//文件类型。
							    /*11：内部文件1
							    12：内部文件2
							    21：外部文件1
							    22：外部文件2*/

    private BigDecimal allAmount;//总金额
    private Integer allCount;//总笔数
    private String bankCode;//银行编码
    private String operators;//操作人标识
    private Date updateTime;//操作时间。默认当前系统时间
    private Integer batchFileStatus;//状态
    private Integer dlBatchCount;//下载批次汇文件次数
    private Integer dlBatchBusiCount;//下载批次业务汇款单次数
    private Integer dlBankCount;//下载银行文件次数
    private Integer dlBankBusiCount;//下载银行业务汇款单次数
    private Date generateTime;//生成时间
    private Date downloadTime;//下载时间
    private Date importTime;//导入时间
    private Date sureimportTime;//确认导入时间
    
    private Long allAmountLong;//long型总金额
    private String batchFileStatusDesc;//状态描述
    private Long notMatchCount;//不匹配笔数
    
    private Integer status; //批次状态
    private Integer dropStatus;//废除状态
    
    private Integer oldStatus; //原批次状态
    
	public Integer getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}
	public Integer getDropStatus() {
		return dropStatus;
	}
	public void setDropStatus(Integer dropStatus) {
		this.dropStatus = dropStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getNotMatchCount() {
		return notMatchCount;
	}
	public void setNotMatchCount(Long notMatchCount) {
		this.notMatchCount = notMatchCount;
	}
	public String getBatchFileStatusDesc() {
		return batchFileStatusDesc;
	}
	public void setBatchFileStatusDesc(String batchFileStatusDesc) {
		this.batchFileStatusDesc = batchFileStatusDesc;
	}
	public Long getAllAmountLong() {
		this.allAmountLong = allAmount.longValue();
		return allAmountLong;
	}
	public void setAllAmountLong(Long allAmountLong) {
		this.allAmountLong = allAmountLong;
	}
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public BigDecimal getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}
	public Integer getAllCount() {
		return allCount;
	}
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getBatchFileStatus() {
		return batchFileStatus;
	}
	public void setBatchFileStatus(Integer batchFileStatus) {
		this.batchFileStatus = batchFileStatus;
	}
	public Integer getDlBatchCount() {
		return dlBatchCount;
	}
	public void setDlBatchCount(Integer dlBatchCount) {
		this.dlBatchCount = dlBatchCount;
	}
	public Integer getDlBatchBusiCount() {
		return dlBatchBusiCount;
	}
	public void setDlBatchBusiCount(Integer dlBatchBusiCount) {
		this.dlBatchBusiCount = dlBatchBusiCount;
	}
	public Integer getDlBankCount() {
		return dlBankCount;
	}
	public void setDlBankCount(Integer dlBankCount) {
		this.dlBankCount = dlBankCount;
	}
	public Integer getDlBankBusiCount() {
		return dlBankBusiCount;
	}
	public void setDlBankBusiCount(Integer dlBankBusiCount) {
		this.dlBankBusiCount = dlBankBusiCount;
	}
	public Date getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
	public Date getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	public Date getSureimportTime() {
		return sureimportTime;
	}
	public void setSureimportTime(Date sureimportTime) {
		this.sureimportTime = sureimportTime;
	}
}