/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.common.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.multipart.MultipartFile;

import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.service.common.ImageSizer;
import com.pay.base.service.common.ImgFileUpAndDownLoadService;
import com.pay.base.service.common.UploadForm;









/**
 * 
 * @author jinhaha
 * @version $Id: ImgFileUpAndDownLoadImpl.java, v 0.1 2010-12-31 下午03:10:54
 *           Exp $
 */

public class ImgFileUpAndDownLoadImpl implements ImgFileUpAndDownLoadService {
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
	public String upLoad(MultipartFile file, String filename, String chlidPath)
			throws IOException {
		if (file == null || StringUtils.isBlank(filename)) {
			return null;
		}
		String rootPath = AppConf.get(AppConf.uploadFileRootPath);
			
		if (StringUtils.isBlank(rootPath)) {
			rootPath = "/data/upload/temp/" + chlidPath;
		} else {
			rootPath = rootPath + chlidPath;
		}
		//file.transferTo(new File("d:"+rootPath+"/xxxxx.jpg"));
		String maPath = creatFolder(rootPath);
		if (StringUtils.isBlank(file.getOriginalFilename())) {
			return null;
		}
		
		String newFileName = this.replaceFileName(file.getOriginalFilename(),
				filename);
		String filePath = maPath + "/" + newFileName;
		String oldResultPath="/"+chlidPath+"/" + newFileName;
		this.writeInputStream(file.getInputStream(), filePath);
		String urlPath=maPath;
		File targetFile=new File(filePath);
		int width=300;
		int height=300;
		String resizeExt=ImageSizer.resizePic(urlPath, targetFile, width, height);
		if(StringUtils.isNotBlank(resizeExt)){
			String fileResultPath="/"+chlidPath+"/" +resizeExt;
			this.delete(oldResultPath);
			return fileResultPath;
		}
		
		return oldResultPath;
		
	}
	
	@Override
	public String[] batchUpLoad(UploadForm uploadForm)throws IOException {
		if (uploadForm==null || uploadForm.getFile() == null || StringUtils.isBlank(uploadForm.getChlidPath())) {
			return null;
		}
		String rootPath = MessageConvertFactory.getMessage(AppConf.uploadFileRootPath);
		String rootUploadPath=rootPath;
		String chlidPath=	uploadForm.getChlidPath();
		if (StringUtils.isBlank(rootPath)) {
			rootPath = "/opt/tk/upload/temp/" + chlidPath;
		} else {
			rootPath = rootPath + chlidPath;
		}
		//file.transferTo(new File("d:"+rootPath+"/xxxxx.jpg"));
		String maPath = creatFolder(rootPath);
		
		String newFileName = "";
		String filePath = "";
		String[] fileResultPath=null;
		List<MultipartFile> file=uploadForm.getFile();
		if(file!=null && file.size()>0){
			fileResultPath=new String[file.size()];
			int i=0;
			for(MultipartFile mf:file){
				newFileName = this.replaceFileName(mf.getOriginalFilename(),
						this.getRondomFileName());
				fileResultPath[i]="/"+chlidPath+"/" + newFileName;
				filePath = maPath + "/" + newFileName;
				this.writeInputStream(mf.getInputStream(), filePath);
				
				if(uploadForm.isResize()){
					String urlPath=maPath;
					File targetFile=new File(filePath);
					int width=uploadForm.getResizeWidth();
					int height=uploadForm.getResizeHeight();
					String resizeExt=ImageSizer.resizePic(urlPath, targetFile, width, height);
					fileResultPath[i]="/"+chlidPath+"/" +resizeExt;
				}
				
				if(uploadForm.isPress()){
					File targetImg=new File(rootUploadPath+fileResultPath[i]);
					
					ImageSizer.pressText(uploadForm.getPressText(), targetImg.getPath(), 
							uploadForm.getFontName(), uploadForm.getFontStyle(), uploadForm.getFontSize());
				}
				
				i++;
			     
			}
		}
		
	
		
		
		return fileResultPath;
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
	
	  public boolean delete(String delPath){
		  String rootPath = MessageConvertFactory.getMessage(AppConf.uploadFileRootPath);
		  rootPath=rootPath.substring(0, rootPath.length()-1);
		  delPath=rootPath+delPath;
	        File deldir=new File(delPath);
	        logger.debug("要删除的文件的路径："+ delPath);
	        if(deldir.exists()) {
	            deldir.delete();
	            return true;
	        }
	        return false;
	    }
	
	public  boolean validateImageFileType(MultipartFile file){
        if(file!=null && file.getSize()>0){
            List<String> arrowType = Arrays.asList("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");
            List<String> arrowExtension = Arrays.asList("gif","jpg","bmp","png");
            String ext = getExt(file);
            return arrowType.contains(file.getContentType().toLowerCase()) && arrowExtension.contains(ext);
        }
        return false;
    }
    
    public  String getExt(MultipartFile file){
    	String uploadFileName=file.getOriginalFilename();
        return uploadFileName.substring(uploadFileName.lastIndexOf('.')+1).toLowerCase();
    }
	
	 public boolean checkImgSuffix(MultipartFile file){
	        String [] upStr={"jpg","jpeg","png","bmp","gif"};
	        String uploadName=file.getContentType();
	        if(StringUtils.isNotBlank(uploadName)){
	        	for(int i=0;i<upStr.length;i++){
		            if(uploadName.toLowerCase().contains((upStr[i]))){
		                return true;
		            }
		        }
	        }
	        
	        return false;
	    }
	    
	    /**
	     * 上传文件的大小 以M为单位 
	     * @param formfile
	     * @return
	     */
	    public double getFileSizeAtM(MultipartFile file){
	        double fileSize =(double) file.getSize()/ (1024 * 1024);
	        return fileSize;
	    }
	    
	    /**
	     * 上传文件的大小 以kb为单位 
	     * @param formfile
	     * @return
	     */
	    public double getFileSizeAtKB(MultipartFile file){
	        double fileSize =(double) file.getSize()/1024;
	        return fileSize;
	    }
	    
	    
	    public  String getRondomFileName(){
	        return UUID.randomUUID().toString();
	    }
}
