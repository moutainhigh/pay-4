/**
 * 
 */
package com.pay.fi.chain.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.pay.fi.chain.model.UploadForm;


/**
 * @author PengJiangbo
 *
 */
public interface ImgFileUpAndDownLoadService {
	/**
	 * 文件上传
	 *
	 * @param file
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public String upLoad(MultipartFile file,String filename,String chlidPath, Long memberCode) throws IOException;
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
	
	
	 /**
	  * 验证是否为图片
	 * @param file
	 * @return
	 */
	public boolean validateImageFileType(MultipartFile file);
	
    public double getFileSizeAtM(MultipartFile file);
    
    public double getFileSizeAtKB(MultipartFile file);
    
    public String getRondomFileName();
    
	public String[] batchUpLoad(UploadForm uploadForm)throws IOException;
	
	  public boolean delete(String delPath);
}
