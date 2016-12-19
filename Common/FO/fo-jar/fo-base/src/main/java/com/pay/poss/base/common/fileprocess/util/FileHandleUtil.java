/** @Description 
 * @project 	poss-reconcile
 * @file 		FileHandleUtil.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
 */
package com.pay.poss.base.common.fileprocess.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.poss.base.common.fileprocess.dto.FileInfoDTO;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;


/**
 * <p>
 * 文件处理辅助类
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see
 */
public class FileHandleUtil {

	private static final Log logger = LogFactory.getLog(FileHandleUtil.class);

	public static final String DEFAULT_ENCODEING = "UTF-8";

	/**
	 * 得到文件目录,的文件目录格式是yyyyMMdd
	 */
	public static String getFileFolder() {
		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		final StringBuffer sb = new StringBuffer();
		sb.append(year).append(
				month < 10 ? ("0" + month) : String.valueOf(month)).append(
				day < 10 ? ("0" + day) : String.valueOf(day));
		logger.debug("getFileFolder.fileFolder"+sb.toString());
		return sb.toString();
	}
	
	
	public static void createFileFolder(final String filePath , final String fileFolder) {
		String fullPath = "";
		if (!filePath.endsWith(File.separator)) {
			fullPath = filePath + File.separator + fileFolder;
		}
		final File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.debug("FileHandleUtil.createFileFolder.fullPath"+fullPath);
	}
	
	/**
	 * 获取文件名。
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileName(final CommonsMultipartFile file) {
		final String fileName = file.getOriginalFilename();
		return getFileName(fileName);
	}

	/**
	 * 文件名转换。
	 * @param name
	 * @return
	 */
	public static String getFileName(final String name) {
		String fileName = "" + name;
		final int indexOfAntiSlash = fileName.lastIndexOf("\\");
		fileName = fileName.substring(indexOfAntiSlash + 1, fileName.length());
		final int indexOfSlash = fileName.lastIndexOf("/");
		fileName = fileName.substring(indexOfSlash + 1, fileName.length());
		return fileName;
	}

	/**
	 * 获取文件名后缀。
	 * 
	 * @param name
	 * @return
	 */
	public static String getFileNameNoPostfix(final String name) {
		final String fileName = getFileName(name);
		final String postfix = getPostfix(fileName);
		if (postfix.length() == 0) {
			return fileName;
		} else {
			return fileName.split(postfix)[0];
		}
	}

	/**
	 * 获取全路径。
	 * 
	 * @param fileInfo
	 * @return
	 * @throws PossUntxException 
	 */
	public static String getFullPath(final FileInfoDTO fileInfo) throws PossUntxException {
		final String postfix = getPostfix(fileInfo.getFileName());
		final StringBuilder sb = new StringBuilder();
		
		sb.append(fileInfo.getFilePath()).append(File.separator);
				
				if(fileInfo.getFileFolder() != null){
					sb.append(fileInfo.getFileFolder()).append(File.separator);
				}
				sb.append(getFileNameNoPostfix(fileInfo.getFileName())).append(postfix);

		return sb.toString();
	}

	/**
	 * 根据文件名得到文件的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPostfix(final String fileName) {
		final int idx = fileName.lastIndexOf(".");
		if (idx < 0) {
			return "";
		}
		return fileName.substring(idx);
	}

	/**
	 * 将字符串写入指定的文件通道中
	 * 
	 * @param outChannel
	 *            文件通道
	 * @param encode
	 *            字符编码，例如UTF-8,GBK
	 * @param s
	 *            字符串
	 * @throws PossUntxException 
	 */
	public static void writeFile(final FileChannel outChannel,  String encode,final String s) throws PossUntxException {
		if (s == null)
			return;

		if (encode == null || encode.trim().length() == 0)
			encode = DEFAULT_ENCODEING;
		try {
			byte[] bytes = s.getBytes(encode);
			ByteBuffer buf = ByteBuffer.allocate(bytes.length);
			buf.put(bytes);
			buf.flip(); // Flip the buffer ready for file write
			outChannel.write(buf);// Wtite the buffer to the file channel
		} catch (FileNotFoundException fne) {
			logger.error("找不到文件 [" + fne + "]");
			throw new PossUntxException("找不到文件"+fne.getMessage(),ExceptionCodeEnum.NOT_FOUND_FILE);
		} catch (UnsupportedEncodingException uee) {
			logger.error("不支持的编码类型 [" + uee + "]");
			throw new PossUntxException("不支持的编码类型"+uee.getMessage(),ExceptionCodeEnum.UNSUPPORTED_ENCODING);
		} catch (IOException ioe) {
			logger.error("IO流操作异常 [" + ioe + "]");
			throw new PossUntxException("IO流操作异常"+ioe.getMessage(),ExceptionCodeEnum.IO_EXCEPTION);
		}

	}
	
	
	public static String getPostFixForParser(final String fileName){
		if(fileName == null) return "";
		String fileNameTemp = getPostfix(fileName);
		fileNameTemp = fileNameTemp.substring(1,2).toUpperCase()+fileNameTemp.substring(2).toLowerCase();
		return fileNameTemp;
	}
}
