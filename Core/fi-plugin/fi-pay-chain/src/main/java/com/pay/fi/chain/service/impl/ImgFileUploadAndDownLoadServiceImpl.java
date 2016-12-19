/**
 * 
 */
package com.pay.fi.chain.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.pay.fi.chain.model.UploadForm;
import com.pay.fi.chain.service.ImageSizer;
import com.pay.fi.chain.service.ImgFileUpAndDownLoadService;
import com.pay.fi.chain.util.PayLinkConf;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;

/**
 * @author PengJiangbo
 *
 */
public class ImgFileUploadAndDownLoadServiceImpl implements
		ImgFileUpAndDownLoadService {

	private static final Log logger = LogFactory.getLog(ImgFileUploadAndDownLoadServiceImpl.class) ;
	//阿里云存储密钥
	private String ossKey;
	//阿里云存储子目录
	private String ossSubDir;
	//阿里云存储根目录
	private String ossRootDir;
	@Override
	public String upLoad(final MultipartFile file, final String filename, final String chlidPath, final Long memberCode)
			throws IOException {
		if (file == null || StringUtils.isBlank(filename)) {
			return null;
		}
		//String rootPath = "/data/upload/" ;   //AppConf.get(AppConf.uploadFileRootPath);
		String rootPath = PayLinkConf.get(PayLinkConf.payLinkShopTermPath) ;
		if (StringUtils.isBlank(rootPath)) {
			rootPath = "/ipayfile/" + ossSubDir + "/" + memberCode ;
		} else {
			rootPath = rootPath + ossSubDir + "/" + memberCode ;
		}
		//file.transferTo(new File("d:"+rootPath+"/xxxxx.jpg"));
		String maPath = creatFolder(rootPath);
		if (StringUtils.isBlank(file.getOriginalFilename())) {
			return null;
		}
		
		String newFileName = this.replaceFileName(file.getOriginalFilename(),
				filename);
		String filePath = maPath + "/" + newFileName;
		String oldResultPath="/"+ossSubDir+"/" + memberCode + "/" + newFileName;
		this.writeInputStream(file.getInputStream(), filePath);
		String urlPath=maPath;
		File targetFile=new File(filePath);
		int width=300;
		int height=300;
		String resizeExt=ImageSizer.resizePic(urlPath, targetFile, width, height);
		if(StringUtils.isNotBlank(resizeExt)){
			String fileResultPath="/"+ossSubDir+"/" + memberCode + "/" +resizeExt;
			this.delete(oldResultPath);
			this.saveFileOnAliyun(fileResultPath,new File("/ipayfile" + fileResultPath));
			return fileResultPath;
		}
		this.saveFileOnAliyun(oldResultPath,targetFile);
		return oldResultPath;
		
	}
	
	private void saveFileOnAliyun(String dbRelativePath,File dest){
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken;
		try {
			rawToken = oss.init(ossSubDir);
			OSSClient client = oss.getOSSClient();
			client.putObject(rawToken.getString("bucket"), ossRootDir+dbRelativePath, dest);
		} catch (UnsupportedOperationException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (MyOSSException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (IOException e) {
			logger.error("oss error:"+e.getMessage());
		}
		if(dest.exists()){
			dest.delete();
		}
	}
	@Override
	public boolean rename(final String filePath, final String srcFileName,
			final String newFileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public  boolean validateImageFileType(final MultipartFile file){
        if(file!=null && file.getSize()>0){
            List<String> arrowType = Arrays.asList("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");
            List<String> arrowExtension = Arrays.asList("gif","jpg","bmp","png");
            String ext = getExt(file);
            return arrowType.contains(file.getContentType().toLowerCase()) && arrowExtension.contains(ext);
        }
        return false;
    }
    
    public  String getExt(final MultipartFile file){
    	String uploadFileName=file.getOriginalFilename();
        return uploadFileName.substring(uploadFileName.lastIndexOf('.')+1).toLowerCase();
    }

    /**
     * 上传文件的大小 以M为单位 
     * @param formfile
     * @return
     */
    @Override
	public double getFileSizeAtM(final MultipartFile file){
        double fileSize =(double) file.getSize()/ (1024 * 1024);
        return fileSize;
    }
    
    /**
     * 上传文件的大小 以kb为单位 
     * @param formfile
     * @return
     */
    @Override
	public double getFileSizeAtKB(final MultipartFile file){
        double fileSize =(double) file.getSize()/1024;
        return fileSize;
    }

	 @Override
	public  String getRondomFileName(){
        return UUID.randomUUID().toString();
    }

	@Override
	public String[] batchUpLoad(final UploadForm uploadForm) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String delPath){
		  //String rootPath = "/data/upload/";
		  String rootPath = PayLinkConf.get(PayLinkConf.payLinkShopTermPath) ;
		  rootPath=rootPath.substring(0, rootPath.length()-1);
		  delPath=rootPath+delPath;
	      File deldir=new File(delPath);
	      logger.debug("要删除的文件的路径："+ delPath);
	      if(deldir.exists()) {
	          deldir.delete();
	          return true;
	      }
	      return true;
	  }

	@Override
	public boolean downLoad(final HttpServletResponse response, final String srcFile,
			final String newFileName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param srcPath
	 * @param newFile
	 * @param thisDateFile
	 * @return
	 */
	private String creatFolder(final String srcPath) {

		File srcFolder = new File(srcPath); // 一级文件夹
		
		if (!srcFolder.exists()) {
			//srcFolder.mkdir();
			srcFolder.mkdirs(); 
		}
		return srcFolder.getPath();
	}
	/**
	 * 写入文件
	 * 
	 * @param stream
	 * @param filePath
	 * @throws IOException
	 */
	private void writeInputStream(final InputStream stream, final String filePath)
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
	 * 替换文件前缀名称
	 * 
	 * @param filename
	 * @param newFileName
	 * @return
	 */
	private String replaceFileName(final String filename, final String newFileName) {
		int index = filename.lastIndexOf(".");
		return filename.replace(filename.substring(0, index), newFileName);
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	public String getOssSubDir() {
		return ossSubDir;
	}
	public void setOssSubDir(String ossSubDir) {
		this.ossSubDir = ossSubDir;
	}
	public String getOssRootDir() {
		return ossRootDir;
	}
	public void setOssRootDir(String ossRootDir) {
		this.ossRootDir = ossRootDir;
	}
	
}
