/** @Description 处理文件 上传操作 
 * @project 	poss-reconcile
 * @file 		FileHandler.java 
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
 */
package com.pay.poss.base.common.fileprocess.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.poss.base.common.fileprocess.dto.FileInfoDTO;
import com.pay.poss.base.common.fileprocess.util.FileHandleUtil;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.DateUtil;


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
	
	private final static FileHandler fileHandler = new FileHandler();
	
	private FileHandler(){};
	
	public static FileHandler getFileHandler(){
		return fileHandler;
	}
	
	private transient  Log logger = LogFactory.getLog(getClass());

	/**
	 * 上传文件处理。 传入上传文件的输入流及输出文件定义，经过预处理后保存文件，
	 * 
	 * @param inputStream
	 *            文件输入流对象
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	public boolean uploadFile(final InputStream inputStream, final FileInfoDTO fileInfo)  {
			try {
				this.beforeSave(fileInfo);
				this.writeFile(inputStream, fileInfo);
				return true;
			} catch (PossUntxException e) {
				logger.error(e.getMessage(),e);
				return false;
			}

	}
	
	/**
	 * 上传文件处理操作
	 * @param multipartFile
	 * @return
	 * @throws PossUntxException
	 */
	public boolean uploadFile(final CommonsMultipartFile multipartFile ) {
		try{
			FileInfoDTO fileInfo = new FileInfoDTO();
			fileInfo.setFileName(multipartFile.getOriginalFilename());
			this.beforeSave(fileInfo);
			this.writeFile(multipartFile.getInputStream(), fileInfo);
			return true;
		}catch(PossUntxException ex){
			logger.error(ex.getMessage(),ex);
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return false;
		}
		
	}
	
	/**
	 * 下载文件操作
	 * @param fileInfo
	 * @throws PossUntxException
	 * @author Henry.Zeng
	 * @see
	 */
	public InputStream downloadFile(final FileInfoDTO fileInfo ) throws PossUntxException{
		return this.readFile(fileInfo);
	}
	/**
	 * 删除文件操作
	 * @param fileInfo
	 * @throws PossUntxException
	 * @author Henry.Zeng
	 * @see
	 */
	public boolean deleteFile(final FileInfoDTO fileInfo) throws PossUntxException{
		File file = null;
		try {
			file = new File(URLDecoder.decode(fileInfo.getFilePath(), "UTF-8"));
			if(file.isFile()){
				file.delete();
			}
			return true;
		} catch (UnsupportedEncodingException e) {
			logger.error("删除文件异常"+e.getMessage(),e);
			return false;
		}
		
	}
	
	
	
	
	/**
	 * 文件保存前的预处理。
	 * 
	 * @param fileInfo
	 *            文件定义对象
	 * @throws PossUntxException 
	 */
	private void beforeSave(final FileInfoDTO fileInfo) throws PossUntxException {
		String fileFolder = fileInfo.getFileFolder();
		
		if(fileFolder == null ) {
			fileFolder = FileHandleUtil.getFileFolder();
		}
		
		//String filePath = StringUtils.isEmpty(fileInfo.getFilePath())?CommonConfiguration("filePath"):fileInfo.getFilePath();
		String filePath =CommonConfiguration.getStrProperties("photoPath");
		FileHandleUtil.createFileFolder(filePath, fileFolder);
		fileInfo.setFileFolder(fileFolder);
		fileInfo.setFilePath(filePath);
	}

	private InputStream readFile(final FileInfoDTO fileInfo) throws PossUntxException{
		InputStream in = null;
		try {
			in = this.getInputStream(fileInfo);
			
			int bytesRead = 0;
			final byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
				in.read(buffer, 0, bytesRead);
			}
			return in;
		} catch (IOException ioe) {
			logger.error("IO流操作异常 [" + ioe + "]");
			throw new PossUntxException("IO流操作异常" + ioe.getMessage(),
					ExceptionCodeEnum.IO_EXCEPTION);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				logger.error("IO流操作异常 [" + ioe + "]");
				throw new PossUntxException("IO流操作异常" + ioe.getMessage(),
						ExceptionCodeEnum.IO_EXCEPTION);
			}
		}
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
	private void writeFile(final InputStream inputStream, final FileInfoDTO fileInfo) throws PossUntxException {

		OutputStream out = null;
		try {
			out = this.getOutputStream(fileInfo);

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
	public OutputStream getOutputStream(final FileInfoDTO fileInfo) throws PossUntxException {
		final String fullPath = FileHandleUtil.getFullPath(fileInfo);
		try {
			return new BufferedOutputStream( new FileOutputStream(fullPath));
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
	public InputStream getInputStream(final FileInfoDTO fileInfo) throws PossUntxException {
		final String fullPath = FileHandleUtil.getFullPath(fileInfo);
		try {
			return new BufferedInputStream(  new FileInputStream(fullPath));
		} catch (FileNotFoundException e) {
			logger.error("找不到文件 [" + e + "]" + fullPath);
			throw new PossUntxException("找不到文件" + e.getMessage(),
					ExceptionCodeEnum.NOT_FOUND_FILE);
		}
	}

	public String getFilePath(){
		String filePath = CommonConfiguration.getStrProperties("filePath");
		String currDate = DateUtil.formatDateTime("yyyyMMdd",new Date());
		if(filePath.endsWith("/") || filePath.equals(File.separator)){
			filePath = filePath + currDate;
		}else{
			filePath = filePath + File.separator + currDate;
		}
		return filePath;
	}
}
