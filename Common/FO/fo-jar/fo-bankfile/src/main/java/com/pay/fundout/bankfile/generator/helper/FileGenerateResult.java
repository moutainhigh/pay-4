/**
 *  <p>File: FileParseResult.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.helper;

import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.inf.model.BaseObject;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.model.BatchFileInfo;


/**
 * <p></p>
 * @author zengli
 * @since 2011-6-1
 * @see 
 */
public class FileGenerateResult extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String templateName;


	private String templatePath;
	
	private String genFullPath;
	
	private String genFileEncoding;

	private String genBasePath;
	
	private String batchNum ;
	
	private List<FileDetailMode> detailList;
	
	private String userKy;
	
	private String fileName;
	
	private String fileSubfix;
	
	/**
	 * @return the fileSubfix
	 */
	public String getFileSubfix() {
		return fileSubfix;
	}

	/**
	 * @param fileSubfix the fileSubfix to set
	 */
	public void setFileSubfix(String fileSubfix) {
		this.fileSubfix = fileSubfix;
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
	 * @return the userKy
	 */
	public String getUserKy() {
		return userKy;
	}

	private Map<String,Object> ctxMap;
	

	public  FileGenerateResult(Map<String,String> fileInfo,List<FileDetailMode> bankDetailInfoList){
		genBasePath = fileInfo.get("BATCH_FILE_PATH");
		batchNum = fileInfo.get("BATCH_NUM");
		detailList = bankDetailInfoList;
		templatePath = CommonConfiguration.getStrProperties("genfile.template.path");
		userKy = fileInfo.get("USER_KY");
	}

	/**
	 * @return the genBasePath
	 */
	public String getGenBasePath() {
		return genBasePath;
	}

	public void setGenBasePath(String genBasePath) {
		this.genBasePath = genBasePath;
	}

	/**
	 * @return the detailList
	 */
	public List<FileDetailMode> getDetailList() {
		return detailList;
	}

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}
	
	private BatchFileInfo batchFileInfo = null;

	/**
	 * @return the batchFileInfo
	 */
	public BatchFileInfo getBatchFileInfo() {
		return batchFileInfo;
	}

	/**
	 * @param batchFileInfo the batchFileInfo to set
	 */
	public void setBatchFileInfo(BatchFileInfo batchFileInfo) {
		this.batchFileInfo = batchFileInfo;
	}
	
	
	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @return the ctxMap
	 */
	public Map<String, Object> getCtxMap() {
		return ctxMap;
	}

	/**
	 * @param ctxMap the ctxMap to set
	 */
	public void setCtxMap(Map<String, Object> ctxMap) {
		this.ctxMap = ctxMap;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatePath() {
		return templatePath;
	}
	/**
	 * @return the genFullPath
	 */
	public String getGenFullPath() {
		return genFullPath;
	}

	/**
	 * @param genFullPath the genFullPath to set
	 */
	public void setGenFullPath(String genFullPath) {
		this.genFullPath = genFullPath;
	}

	/**
	 * @return the genFileEncoding
	 */
	public String getGenFileEncoding() {
		return genFileEncoding;
	}

	/**
	 * @param genFileEncoding the genFileEncoding to set
	 */
	public void setGenFileEncoding(String genFileEncoding) {
		this.genFileEncoding = genFileEncoding;
	}
	

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
