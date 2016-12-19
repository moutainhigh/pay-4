/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.base.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.common.properties.CommonConfiguration;

/**
 *
 * @author zhi.wang
 * @version $Id: ImgFileUpAndDownLoadImpl.java, v 0.1 2010-12-31 下午03:10:54 zhi.wang Exp $
 */
public class ImgFileUtil {


	/** 
	 * @param path
	 * @param filename
	 * @throws IOException
	 * @see com.pay.base.service.file.FileUpAndDownLoadService#upLoad(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	public static String upLoadImg(MultipartFile file, String filename,String appealCode)
			throws IOException {
		if(file == null || StringUtils.isBlank(filename)){
			return null;
		}
		String rootPath = Constants.ADV_UPLOAD_PATH;
		rootPath = rootPath + appealCode;
		
        String maPath = creatFolder(rootPath);
        if(StringUtils.isBlank(file.getOriginalFilename())){
        	return null;
        }
		String newFileName = replaceFileName(file.getOriginalFilename(),filename); 
		String filePath = maPath+"/" + newFileName;
		writeInputStream(file.getInputStream(),filePath); 
		return rootPath + newFileName;
	}
	/**
	 * 替换文件前缀名称
	 *
	 * @param filename
	 * @param newFileName
	 * @return
	 */
	private static String replaceFileName(String filename,String newFileName) {
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
	private static void writeInputStream(InputStream stream,String filePath) throws IOException    {          
        FileOutputStream fs=new FileOutputStream(filePath);    
        byte[] buffer =new byte[1024*1024*5];    
        int bytesum = 0;    
        int byteread = 0;     
        while ((byteread=stream.read(buffer))!=-1)    
        {    
           bytesum+=byteread;    
           fs.write(buffer,0,byteread);    
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
	private static String creatFolder(String srcPath) {  
      
        File srcFolder = new File(srcPath);          		 //一级文件夹  
        if(!srcFolder.exists()) {  
        	srcFolder.mkdir();
        }
        return srcFolder.getPath();   
   }
	
	
	/**
	 * 文件删除
	 * @param srcPathAndFilename
	 * @return
	 */
	public static boolean deleteFile(String srcPathAndFilename) {
		File file = new File(srcPathAndFilename);      
        if(file.isFile() && file.exists()){      
            file.delete();      
            return true;      
        }else{      
            return false;      
        }      
	}
	
	
	
	
}
