 /** @Description 
 * @project 	poss-reconcile
 * @file 		FileInfo.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.dto.fileservice;

import java.math.BigInteger;

import com.pay.inf.model.BaseObject;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see 
 */
public class FileInfoDTO extends BaseObject {


	/**
	 * SID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件名
	 */
	private String fileName;

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
