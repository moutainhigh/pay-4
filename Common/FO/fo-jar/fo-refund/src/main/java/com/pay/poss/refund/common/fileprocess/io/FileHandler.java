/** @Description 处理文件 上传操作 
 * @project 	poss-reconcile
 * @file 		FileHandler.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
 */
package com.pay.poss.refund.common.fileprocess.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.refund.common.fileprocess.creator.FileAbstraceCreator;
import com.pay.poss.refund.common.fileprocess.utils.FileHandleUtil;
import com.pay.poss.refund.model.FileInfo;

/**
 * <p>
 * 处理文件上传
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see
 */
public class FileHandler {

	private transient  Log logger = LogFactory.getLog(getClass());

	private transient Map<String, FileAbstraceCreator> fileCreatorMap;

	/**
	 * 上传文件处理。 传入上传文件的输入流及输出文件定义，经过预处理后保存文件，
	 * 
	 * @param inputStream
	 *            文件输入流对象
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	public void uploadFile(final InputStream inputStream, final FileInfo fileInfo) throws PossUntxException {
		
		this.beforeSave(fileInfo);

		this.saveFile(inputStream, fileInfo);

	}

	/**
	 * 文件保存前的预处理。
	 * 
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	protected void beforeSave(final FileInfo fileInfo) throws PossUntxException {
		prepareSave(fileInfo);
	}

	/**
	 * 文件保存前的预处理，设置文件定义对象的相应值。
	 * 
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	private void prepareSave(final FileInfo fileInfo) throws PossUntxException {

		final FileAbstraceCreator creator = (FileAbstraceCreator) this.fileCreatorMap.get("filePath");
		final String fileFolder = creator.createFileFolder(fileInfo);
		final String fileId = fileInfo.getFileName();//creator.createFileId();取对账文件名称
		final String filePath = creator.getFilePath(fileInfo);
		fileInfo.setFilePath(filePath);
		fileInfo.setFileFolder(fileFolder);
		fileInfo.setFileId(fileId);

	}

	/**
	 * 保存文件
	 * 
	 * @param inputStream
	 *            文件输入流对象
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	private void saveFile(final InputStream inputStream, final FileInfo fileInfo) throws PossUntxException {

		OutputStream out = null;
		try {
			out = this.getOutputStream(fileInfo);

			// byte[] buffer = new byte[1024];
			// while (inputStream.read(buffer) > 0) {
			// out.write(buffer);// 写入磁盘；
			// }
			int bytesRead = 0;
			final byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, bytesRead);// 将文件写入写入磁盘；
			}
		} catch (IOException ioe) {
			logger.error("IO流操作异常 [" + ioe + "]");
			throw new PossUntxException("IO流操作异常" + ioe.getMessage(),
					ExceptionCodeEnum.IO_EXCEPTION);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				logger.error("IO流操作异常 [" + ioe + "]");
				throw new PossUntxException("IO流操作异常" + ioe.getMessage(),
						ExceptionCodeEnum.IO_EXCEPTION);
			}
		}

	}

	/**
	 * 获取OutputStream用于写入
	 * 
	 * @param fileInfo
	 *            文件定义对象
	 * @return
	 * @throws PossUntxException 
	 * @throws Exception
	 *             
	 */
	public OutputStream getOutputStream(final FileInfo fileInfo) throws PossUntxException {
		final String fullPath = FileHandleUtil.getFullPath(fileInfo);

		try {
			return new FileOutputStream(fullPath);
		} catch (FileNotFoundException e) {
			logger.error("找不到文件 [" + e + "]" + fullPath);
			throw new PossUntxException("找不到文件" + e.getMessage(),
					ExceptionCodeEnum.NOT_FOUND_FILE);
		}
	}

	/**
	 * 根据给出的filePath、fileFolder和fileId 得到一个输出流
	 * 
	 * @param fileInfo
	 *            ：filePath、fileFolder、fileId
	 * @return
	 * @throws PossUntxException 
	 * @throws Exception
	 *             
	 */
	public InputStream getInputStream(final FileInfo fileInfo) throws PossUntxException {
		final String fullPath = FileHandleUtil.getFullPath(fileInfo);
		try {
			return new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			logger.error("找不到文件 [" + e + "]" + fullPath);
			throw new PossUntxException("找不到文件" + e.getMessage(),
					ExceptionCodeEnum.NOT_FOUND_FILE);
		}
	}

	public Map<String,FileAbstraceCreator> getFileCreatorMap() {
		return fileCreatorMap;
	}

	public void setFileCreatorMap(Map<String,FileAbstraceCreator> fileCreatorMap) {
		this.fileCreatorMap = fileCreatorMap;
	}
}
