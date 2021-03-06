/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.file;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载服务
 * @author zhi.wang
 * @version $Id: FileUpAndDownLoadService.java, v 0.1 2010-12-31 下午02:57:18 zhi.wang Exp $
 */
public interface FileUpAndDownLoadService {
	
	/**
	 * 文件上传
	 *
	 * @param file
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public String upLoad(MultipartFile file,String filename,String appealCode) throws IOException;
	/**
	 * 文件下载
	 *
	 * @param response
	 * @param srcFile
	 * @param newFileName
	 * @return
	 * @throws Exception
	 */
	public boolean downLoad(HttpServletResponse response,String srcFile,String newFileName ) throws Exception;
	
	/**
	 * 重命名文件夹名称
	 *
	 * @param filePath  文件夹所在路径
	 * @param srcFileName 原文件名
	 * @param newFileName 新文件名
	 * @return
	 */
	public boolean rename(String filePath,String srcFileName,String newFileName);
}
