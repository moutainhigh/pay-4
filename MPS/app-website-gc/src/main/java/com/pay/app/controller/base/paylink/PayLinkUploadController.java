/**
 * 
 */
package com.pay.app.controller.base.paylink;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.common.UploadHelper;
import com.pay.fi.chain.dto.ResultDto;
import com.pay.fi.chain.model.ImgResonse;
import com.pay.fi.chain.service.PayLinkUploadService;
import com.pay.fi.chain.util.CommonErrorCodeEnum;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.util.JSonUtil;

/**
 * 
 * @author PengJiangbo
 *
 */
public class PayLinkUploadController extends MultiActionController {

	
	
	private PayLinkUploadService payLinkUploadService ;
	private static final Log logger = LogFactory.getLog(PayLinkUploadController.class);
	//阿里云存储密钥
	private String ossKey;
	//阿里云存储子目录
	private String ossSubDir;
	//阿里云存储根目录
	private String ossRootDir;
	
	public ModelAndView doUpload(final HttpServletRequest request, final HttpServletResponse response)  throws Exception{
		 	MultipartHttpServletRequest multipartRequest  = (MultipartHttpServletRequest) request;
		 	ResultDto rDto=new ResultDto();
		 	PrintWriter pw = response.getWriter();
		 	response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			try {
				MultipartFile imgFile  =  multipartRequest.getFile("userfile"); 
				Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession()) ;
			    rDto= payLinkUploadService.doUploadFileRnTx(imgFile, memberCode);
			    if(rDto.isResultStatus()){
			    	 ImgResonse respImg=(ImgResonse)rDto.getObject();
			    	 respImg.setImgUrl(this.getImgURL(respImg.getImgUrl()));
					 UploadHelper.generateUploadNum(respImg.getImgId(),respImg.getImgUrl());
				}
			} catch (Exception e) {
				logger.error("BaseUploadController.doUpload throws error",e);
				rDto.setResultStatus(false);
				rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_FAILE);
			}
			//String json = BeanUtil.bean2JSON(rDto);
			String json=JSonUtil.toJSonString(rDto);
			logger.info(json);
			pw.write(json);
			pw.close();
			return null;
	        
	 }
	private String getImgURL(String relativePath){
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken=null;
		OSSClient client=null;
		try {
			rawToken = oss.init(ossSubDir);
			client = oss.getOSSClient();
		} catch (UnsupportedOperationException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (MyOSSException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (IOException e) {
			logger.error("oss error:"+e.getMessage());
		}
		Date date=new Date(System.currentTimeMillis()+3600*1000*5);
		java.net.URL url=null;
		if(client!=null&&rawToken!=null&&org.apache.commons.lang.StringUtils.isNotBlank(relativePath)){
			url=client.generatePresignedUrl(rawToken.getString("bucket"), ossRootDir+relativePath, date);
			return url.toString();
		}
		return null;
	}
	
	public ModelAndView doRemovePic(final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		ResultDto rDto=new ResultDto();
		PrintWriter pw = response.getWriter();
	 	response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Long picId=Long.valueOf(request.getParameter("picId"));
//			if(UploadHelper.checkPicId(picId)){
				boolean result=payLinkUploadService.deletePic(picId);
				if(result){
					rDto.setResultStatus(true);
					UploadHelper.removeUploadNum(picId);
				}else{
					rDto.setResultStatus(false);
					rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_PIC_REMOVE_FAILE);
				}
					
//			}else{
//				rDto.setResultStatus(false);
//				rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_PIC_IS_NOT_EXSITS);
//			}
		} catch (Exception e) {
			logger.error("BaseUploadController.doRemove throws error",e);
			rDto.setResultStatus(false);
			rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_PIC_REMOVE_FAILE);
		}
		String json=JSonUtil.toJSonString(rDto);
		logger.info(json);
		pw.write(json);
		pw.close();
		return null;
	}

	
	public void setPayLinkUploadService(final PayLinkUploadService payLinkUploadService) {
		this.payLinkUploadService = payLinkUploadService;
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

	/*public boolean validateUploadNum(){
		return false;
	}*/
	
}
