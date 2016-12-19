/** @Description 
 * @project 	poss-reconcile
 * @file 		FileAbstraceCreator.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
 */
package com.pay.poss.refund.common.fileprocess.creator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.refund.model.FileInfo;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see
 */
public abstract class FileAbstraceCreator {
	
	protected final transient  Log logger = LogFactory.getLog(getClass());
	
	private Map<String,String> filePathMap ;

	public Map<String,String> getFilePathMap() {
		return filePathMap;
	}

	public void setFilePathMap(Map<String,String> filePathMap) {
		
		this.filePathMap = filePathMap;
	}
	/**
	 * 获取缓存信息,确定上传路径地址 
	 */
	static{
		//TODO 加载缓存信息
	}
	/**
	 * 得到文件
	 * @param fileInfo
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	abstract public String getFilePath(FileInfo fileInfo);

	abstract public String createFileFolder(FileInfo fileInfo);

	abstract public String createFileId();

	abstract public String getFileFolder();
	
//	abstract publi String

}
