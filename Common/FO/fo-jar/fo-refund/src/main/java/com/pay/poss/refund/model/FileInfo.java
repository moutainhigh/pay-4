 /** @Description 
 * @project 	poss-reconcile
 * @file 		FileInfo.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-15		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.math.BigInteger;

import com.pay.inf.model.BaseObject;

/**
 * <p></p>
 * @author sunsea.li
 * @since 2010-9-15
 * @see 
 */
public class FileInfo extends BaseObject {


	/**
	 * SID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 文件对应的id
	 */
	private String fileId;

	/**
	 * 文件大小
	 */
	private BigInteger fileSize;

	/**
	 * 文件存放的路径
	 */
	private String filePath;

	/**
	 * 文件存放的文件夹对应对账文件的providerCode
	 */
	private String fileFolder;

	/**
	 * 文件类型
	 */
	private String fileType;

	/**
	 * 获取文件存放的文件夹
	 * @return fileFolder
	 */
	public String getFileFolder() {
		return fileFolder;
	}

	/**
	 * 设置文件存放的文件夹
	 * @param fileFolder 文件存放的文件夹
	 */
	public void setFileFolder(String fileFolder) {
		this.fileFolder = fileFolder;
	}

	/**
	 * 获取文件对应的id
	 * @return fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * 设置文件对应的id
	 * @param fileId 文件对应的id
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * 获取文件名
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置文件名
	 * @param fileName 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取文件存放的路径
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件存放的路径
	 * @param filePath 文件存放的路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取文件大小
	 * @return fileSize
	 */
	public BigInteger getFileSize() {
		return fileSize;
	}

	/**
	 * 设置文件大小
	 * @param fileSize 文件大小
	 */
	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 获取文件类型
	 * @return fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 设置文件类型
	 * @param fileType 文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


}
