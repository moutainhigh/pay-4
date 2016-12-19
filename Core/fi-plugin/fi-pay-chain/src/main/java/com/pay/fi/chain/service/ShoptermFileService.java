/**
 * 
 */
package com.pay.fi.chain.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pay.fi.chain.util.PayLinkConf;

/**
 * @author PengJiangbo
 *
 */
public class ShoptermFileService {
	private static final Log logger = LogFactory.getLog(ShoptermFileService.class) ;
	
	private static final String SHOP_TERM = "shopterm/" ;
	//写入文件
	private void writeInputStream(InputStream stream, String filePath) throws IOException{
		FileOutputStream fos = new FileOutputStream(filePath) ;
		byte[] buffer = new byte[1024 * 1024 * 5] ;
		int byteread = 0 ;
		while ((byteread = stream.read(buffer)) != -1) {
			fos.write(buffer, 0, byteread);
			fos.flush();
		}
		fos.close();
		stream.close();
	}
	
	//上传
	public String upload(MultipartFile file, String filename, String memberCode) {
		if(null == file || StringUtils.isBlank(filename)){
			return null ;
		}
		if(null == memberCode || "".equals(memberCode)){
			return null ;
		}
		String rootPath = PayLinkConf.get(PayLinkConf.payLinkShopTermPath) + SHOP_TERM + memberCode + "/" ;
		if(StringUtils.isBlank(file.getOriginalFilename())){
			return null ;
		}
		createFolder(rootPath) ;
		String filePath = rootPath + file.getOriginalFilename() ;
		try {
			writeInputStream(file.getInputStream(), filePath) ;
		} catch (IOException e) {
			logger.error("文件上传异常，IOException:"+ e.getMessage());
			e.printStackTrace();
		}
		return rootPath ;
	}
	
	public boolean validateFileType(MultipartFile file){
		if(null != file && file.getSize()>0){
			//String arrowType = "application/pdf" ;
			String arrowExtension = "pdf" ;
			String ext = getExt(file) ;
			//String type = file.getContentType().toLowerCase() ;
			return arrowExtension.contains(ext) ;
		}
		return false ;
	}
	//获取文件后缀
	public String getExt(MultipartFile file){
		String uploadFileName = file.getOriginalFilename() ;
		return uploadFileName.substring(uploadFileName.lastIndexOf('.') + 1).toLowerCase() ;
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
     * 创建文件夹
     * @param srcPath
     * @return
     */
    private String createFolder(String srcPath){
    	File srcFolder = new File(srcPath) ;
    	if(!srcFolder.exists()){
    		srcFolder.mkdirs() ;
    	}
    	return srcFolder.getPath() ;
    }
}
