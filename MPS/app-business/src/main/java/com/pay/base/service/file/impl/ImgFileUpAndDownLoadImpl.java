/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pay.app.common.helper.AppConf;
import com.pay.base.service.file.FileUpAndDownLoadService;

/**
 * 
 * @author zhi.wang
 * @version $Id: ImgFileUpAndDownLoadImpl.java, v 0.1 2010-12-31 下午03:10:54
 *          zhi.wang Exp $
 */
public class ImgFileUpAndDownLoadImpl implements FileUpAndDownLoadService {
	private static final Log logger = LogFactory
			.getLog(ImgFileUpAndDownLoadImpl.class);

	/**
	 * @param response
	 * @param srcFile
	 * @param newFileName
	 * @throws Exception
	 * @see com.pay.base.service.file.FileUpAndDownLoadService#downLoad(javax.servlet.http.HttpServletResponse,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public boolean downLoad(HttpServletResponse response, String srcFile,
			String newFileName) throws Exception {
		if (StringUtils.isNotBlank(srcFile)
				&& StringUtils.isNotBlank(newFileName)) {
			response.setContentType("image/jpeg");
			response.setCharacterEncoding("UTF-8");

			String fileName = FilenameUtils.getName(srcFile);
			String suffex = fileName.substring(fileName.lastIndexOf("."));
			String filedisplay = URLEncoder.encode(newFileName + suffex,
					"UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filedisplay);
			OutputStream outp = response.getOutputStream();
			FileInputStream in = new FileInputStream(srcFile);
			byte[] b = new byte[2048];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
			outp.close();
			return true;
		}
		return false;
	}

	/**
	 * @param path
	 * @param filename
	 * @throws IOException
	 * @see com.pay.base.service.file.FileUpAndDownLoadService#upLoad(org.springframework.web.multipart.MultipartFile,
	 *      java.lang.String)
	 */
	@Override
	public String upLoad(MultipartFile file, String filename, String appealCode)
			throws IOException {
		if (file == null || StringUtils.isBlank(filename)) {
			return null;
		}
		String rootPath = AppConf.get(AppConf.maUploadFilePath);
		if (StringUtils.isBlank(rootPath)) {
			rootPath = "/opt/upload/ma/appeal/" + appealCode;
		} else {
			rootPath = rootPath + appealCode;
		}
		String maPath = creatFolder(rootPath);
		if (StringUtils.isBlank(file.getOriginalFilename())) {
			return null;
		}
		String newFileName = this.replaceFileName(file.getOriginalFilename(),
				filename);
		String filePath = maPath + "/" + newFileName;
		this.writeInputStream(file.getInputStream(), filePath);
		return filePath;
	}

	/**
	 * 替换文件前缀名称
	 * 
	 * @param filename
	 * @param newFileName
	 * @return
	 */
	private String replaceFileName(String filename, String newFileName) {
		int index = filename.lastIndexOf(".");
		return filename.replace(filename.substring(0, index), newFileName);
	}

	/**
	 * 写入文件
	 * 
	 * @param stream
	 * @param filePath
	 * @throws IOException
	 */
	private void writeInputStream(InputStream stream, String filePath)
			throws IOException {
		FileOutputStream fs = new FileOutputStream(filePath);
		byte[] buffer = new byte[1024 * 1024 * 5];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param srcPath
	 * @param newFile
	 * @param thisDateFile
	 * @return
	 */
	private String creatFolder(String srcPath) {

		File srcFolder = new File(srcPath); // 一级文件夹
		if (!srcFolder.exists()) {
			srcFolder.mkdir();
		}
		return srcFolder.getPath();
	}

	@Override
	public boolean rename(String filePath,String srcFileName, String newFileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始重命名文件夹名称,filePath=" + filePath + ",srcFileName"+srcFileName+",newFileName="
					+ newFileName);
		}
		boolean result = false;
		if (StringUtils.isNotBlank(filePath)
				&& StringUtils.isNotBlank(newFileName) && StringUtils.isNotBlank(srcFileName)) {
			File fl = new File(filePath); // 这里写上发替换的文件夹路径,注意使用双斜杠
			String[] files = fl.list();
			if(files == null || files.length < 1){
				logger.warn("重命名文件夹名称,filePath=" + filePath+"下无文件!");
				return result;
			}
			File f = null;
			String filename = "";
			for (String file : files) {
				f = new File(fl, file);
				filename = f.getName();
				if(StringUtils.equals(filename,srcFileName)){
					f.renameTo(new File(fl.getAbsolutePath() + "\\"
							+ filename.replace(srcFileName, newFileName)));
					if (logger.isDebugEnabled()) {
						logger.debug("重命名文件夹名称,filePath=" + filePath +",srcFileName"+srcFileName+",newFileName="
								+ newFileName+"成功！");
					}
					result = true;
					break;
				}

			}
		}
		return result;
	}
}
