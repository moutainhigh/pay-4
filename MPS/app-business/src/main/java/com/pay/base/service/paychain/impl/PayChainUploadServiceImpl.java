package com.pay.base.service.paychain.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pay.app.common.helper.AppConf;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.ContextPicture;
import com.pay.base.service.common.ImgFileUpAndDownLoadService;
import com.pay.base.service.common.ImgResonse;
import com.pay.base.service.contextPic.ContextPicService;
import com.pay.base.service.paychain.PayChainUploadService;



public class PayChainUploadServiceImpl implements PayChainUploadService {

	private ContextPicService contextPicService;
	private ImgFileUpAndDownLoadService uploadFileService;
	
	private static final Log logger = LogFactory.getLog(PayChainUploadServiceImpl.class);
	private static final String childPath="zfl";
	
	/* (non-Javadoc)
	 * @see com.pay.base.service.paychain.impl.PayChainUploadService#doUploadFileRnTx(org.springframework.web.multipart.MultipartFile)
	 */
	public  ResultDto doUploadFileRnTx(MultipartFile file) throws Exception{
		ResultDto rDto=validateFile(file);
		ImgResonse respImg=null;
		if(rDto.isResultStatus()){
			String fileName=uploadFileService.getRondomFileName();
			String pathUrl=uploadFileService.upLoad(file, fileName, childPath);
			if(StringUtils.isNotBlank(pathUrl)){
				ContextPicture cp=new ContextPicture();
				cp.setPictureName(fileName);
				cp.setPictureStatus(0);
				cp.setPictureType(2);
				cp.setPictureUrl(pathUrl);
				cp.setProductType(4);
				Long picId=contextPicService.createContextPic(cp);
				if(picId!=null && picId>0){
					respImg=new ImgResonse();
					respImg.setImgId(picId);
					respImg.setImgUrl(pathUrl);
					rDto.setResultStatus(true);
					rDto.setObject(respImg);
				}else{
					rDto.setErrorNum(ErrorCodeEnum.UPLOAD_FAILE);
				}
			}
		}
		return rDto;
	}
	
	
	private ResultDto validateFile(MultipartFile imgFile){
		double imgSize=Double.valueOf(AppConf.get(AppConf.uploadFileMaxSize)); //图片允许大小(单位M)
		ResultDto rDto=new ResultDto();
		if(imgFile!=null && StringUtils.isNotBlank(imgFile.getName()) && imgFile.getSize()>0){
			if(!uploadFileService.validateImageFileType(imgFile)){
				rDto.setErrorNum(ErrorCodeEnum.UPLOAD_TYPE_FAILE);
			}else if(uploadFileService.getFileSizeAtM(imgFile)>imgSize){
				rDto.setResultStatus(false);
				rDto.setErrorMsg(ErrorCodeEnum.UPLOAD_SIZE_FAILE.getMessage()+imgSize+"m");
			}else{
				 rDto.setResultStatus(true);
			}
		}else{
			 rDto.setResultStatus(false);
			 rDto.setErrorNum(ErrorCodeEnum.UPLOAD_FILE_EMPTY); 
		}
		return rDto;
	}
	
	
	public boolean  deletePic(Long picId){
		ContextPicture cp=contextPicService.queryPicListById(picId);
		if(cp!=null && contextPicService.deleteByPicId(picId)){
			return uploadFileService.delete(cp.getPictureUrl());
		}
		return false;
	}
	
	
	public void setContextPicService(ContextPicService contextPicService) {
		this.contextPicService = contextPicService;
	}
	public void setUploadFileService(ImgFileUpAndDownLoadService uploadFileService) {
		this.uploadFileService = uploadFileService;
	}
	
	
}
