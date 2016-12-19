/**
 *  <p>File: FileParseResult.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.parser.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel;

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-1
 * @see 
 */
public class FileParseResult {
	
	private InputStream targetFile;
	
	/**
	 * @param targetFile the targetFile to set
	 */
	public void setTargetFile(InputStream targetFile) {
		this.targetFile = targetFile;
	}

	public InputStream getTargetFile(){
		return targetFile;
	}
	
	
	private List<String> errorMsg = new ArrayList<String>();
	


	/**
	 * @return the errorMsg
	 */
	public List<String> getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}


	private List<WithdrawImportRecordModel> detailList = new ArrayList<WithdrawImportRecordModel>();

	/**
	 * @return the detailList
	 */
	public List<WithdrawImportRecordModel> getDetailList() {
		return detailList;
	}

	public void addDeailList(WithdrawImportRecordModel importRecordModel){
		importRecordModel.setBatchNum(batchNum);
		importRecordModel.setBankCode(bankCode);
		importRecordModel.setGFileKy(gFileKy);
		importRecordModel.setFileKy(fileKy);
		detailList.add(importRecordModel);
	}
	
	private String batchNum;
	
	private String bankCode;
	
	private Long gFileKy;
	
	private Long fileKy;
	
	private String workName;

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}


	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @param gFileKy the gFileKy to set
	 */
	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}
	//add by davis.guo at 2016-09-06
	public Long getgFileKy() {
		return gFileKy;
	}
	
}
