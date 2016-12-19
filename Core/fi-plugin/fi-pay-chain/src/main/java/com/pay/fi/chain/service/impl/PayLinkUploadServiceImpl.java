/**
 * 
 */
package com.pay.fi.chain.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pay.fi.chain.dto.ResultDto;
import com.pay.fi.chain.model.ImgResonse;
import com.pay.fi.chain.model.LogoPicture;
import com.pay.fi.chain.service.ImgFileUpAndDownLoadService;
import com.pay.fi.chain.service.LogoPicService;
import com.pay.fi.chain.service.PayLinkUploadService;
import com.pay.fi.chain.util.CommonErrorCodeEnum;

/**
 * @author PengJiangbo
 *
 */
public class PayLinkUploadServiceImpl implements PayLinkUploadService {

	private LogoPicService logoPicService;
	private ImgFileUpAndDownLoadService uploadFileService;
	
	private static final Log logger = LogFactory.getLog(PayLinkUploadServiceImpl.class);
	
	private static final String childPath="logo";
	
	
	@Override
	public  ResultDto doUploadFileRnTx(final MultipartFile file, final Long memberCode) throws Exception{
		ResultDto rDto=validateFile(file);
		ImgResonse respImg=null;
		if(rDto.isResultStatus()){
			String fileName=uploadFileService.getRondomFileName();
			String pathUrl=uploadFileService.upLoad(file, fileName, childPath, memberCode);
			if(StringUtils.isNotBlank(pathUrl)){
				LogoPicture cp=new LogoPicture(); 
				cp.setCreateTime(new Timestamp(new Date().getTime()));
				cp.setMemberCode(memberCode);
				//cp.setMerchantSite("http://test.I.com");
				cp.setPicturePath(pathUrl);
				Long picId=logoPicService.createContextPic(cp);
				if(picId!=null && picId>0){
					respImg=new ImgResonse();
					respImg.setImgId(picId);
					respImg.setImgUrl(pathUrl);
					rDto.setResultStatus(true);
					rDto.setObject(respImg);
				}else{
					rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_FAILE);
				}
			}
		}
		return rDto;
	}
	
	private ResultDto validateFile(final MultipartFile imgFile){
		double imgSize= 5 * 1024 * 1024 ; //图片允许大小(单位M)
		ResultDto rDto=new ResultDto();
		if(imgFile!=null && StringUtils.isNotBlank(imgFile.getName()) && imgFile.getSize()>0){
			if(!uploadFileService.validateImageFileType(imgFile)){
				rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_TYPE_FAILE);
			}else if(uploadFileService.getFileSizeAtM(imgFile)>imgSize){
				rDto.setResultStatus(false);
				rDto.setErrorMsg(CommonErrorCodeEnum.UPLOAD_SIZE_FAILE.getMessage()+imgSize+"m");
			}else{
				 rDto.setResultStatus(true);
			}
		}else{
			 rDto.setResultStatus(false);
			 rDto.setErrorNum(CommonErrorCodeEnum.UPLOAD_FILE_EMPTY); 
		}
		return rDto;
	}
	
	@Override
	public boolean  deletePic(final Long picId){
		LogoPicture cp=logoPicService.queryPicListById(picId);
		if(cp!=null && logoPicService.deleteByPicId(picId)){
			return uploadFileService.delete(cp.getPicturePath());
		}
		return false;
	}

	public void setLogoPicService(final LogoPicService logoPicService) {
		this.logoPicService = logoPicService;
	}

	public void setUploadFileService(final ImgFileUpAndDownLoadService uploadFileService) {
		this.uploadFileService = uploadFileService;
	}
	
}
