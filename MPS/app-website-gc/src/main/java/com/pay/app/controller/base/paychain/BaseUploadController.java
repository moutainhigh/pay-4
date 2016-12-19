package com.pay.app.controller.base.paychain;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.common.ImgResonse;
import com.pay.base.service.paychain.PayChainUploadService;
import com.pay.util.JSonUtil;
import com.pay.app.controller.common.UploadHelper;

public class BaseUploadController extends MultiActionController {
	
	private PayChainUploadService payChainUploadService;
	private static final Log logger = LogFactory.getLog(BaseUploadController.class);

	
	public ModelAndView doUpload(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		 	MultipartHttpServletRequest multipartRequest  = (MultipartHttpServletRequest) request;
		 	ResultDto rDto=new ResultDto();
		 	PrintWriter pw = response.getWriter();
		 	response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			try {
				if(UploadHelper.validateUploadNum()){
					MultipartFile imgFile  =  multipartRequest.getFile("userfile"); 
				    rDto= payChainUploadService.doUploadFileRnTx(imgFile);
				    if(rDto.isResultStatus()){
				    	 ImgResonse respImg=(ImgResonse)rDto.getObject();
						 UploadHelper.generateUploadNum(respImg.getImgId(),respImg.getImgUrl());
				    }
				   
				}else{
					rDto.setResultStatus(false);
					rDto.setErrorNum(ErrorCodeEnum.UPLOAD_NUM_TO_MAX);
				}
			} catch (Exception e) {
				logger.error("BaseUploadController.doUpload throws error",e);
				rDto.setResultStatus(false);
				rDto.setErrorNum(ErrorCodeEnum.UPLOAD_FAILE);
			}
			//String json = BeanUtil.bean2JSON(rDto);
			String json=JSonUtil.toJSonString(rDto);
			logger.info(json);
			pw.write(json);
			pw.close();
			return null;
	        
	 }
	
	
	public ModelAndView doRemovePic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultDto rDto=new ResultDto();
		PrintWriter pw = response.getWriter();
	 	response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Long picId=Long.valueOf(request.getParameter("picId"));
			if(UploadHelper.checkPicId(picId)){
				boolean result=payChainUploadService.deletePic(picId);
				if(result){
					rDto.setResultStatus(true);
					UploadHelper.removeUploadNum(picId);
				}else{
					rDto.setResultStatus(false);
					rDto.setErrorNum(ErrorCodeEnum.UPLOAD_PIC_REMOVE_FAILE);
				}
					
			}else{
				rDto.setResultStatus(false);
				rDto.setErrorNum(ErrorCodeEnum.UPLOAD_PIC_IS_NOT_EXSITS);
			}
		} catch (Exception e) {
			logger.error("BaseUploadController.doRemove throws error",e);
			rDto.setResultStatus(false);
			rDto.setErrorNum(ErrorCodeEnum.UPLOAD_PIC_REMOVE_FAILE);
		}
		String json=JSonUtil.toJSonString(rDto);
		logger.info(json);
		pw.write(json);
		pw.close();
		return null;
	}
	
	public void setPayChainUploadService(PayChainUploadService payChainUploadService) {
		this.payChainUploadService = payChainUploadService;
	}
	
	
	
	
	public boolean validateUploadNum(){
		return false;
	}
	

}
