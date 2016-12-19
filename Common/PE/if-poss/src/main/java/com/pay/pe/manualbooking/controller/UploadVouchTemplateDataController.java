package com.pay.pe.manualbooking.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.manualbooking.dao.VouchDataLoader;
import com.pay.pe.manualbooking.dao.VouchValidator;
import com.pay.pe.manualbooking.dao.impl.VouchDataLoaderImpl;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;
import com.pay.pe.manualbooking.exception.VouchDataInvalidException;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;

import org.springframework.web.servlet.mvc.SimpleFormController;

public class UploadVouchTemplateDataController extends SimpleFormController{

	private static final Log LOG = LogFactory.getLog(UploadVouchTemplateDataController.class);
	public static final String TEMP_VOUCH_DATA = "TEMP_VOUCH_DATA";
	
	private List<VouchValidator> vouchValidators;
	
	private String failureView;
	
	public String getFailureView() {
		return failureView;
	}

	public void setFailureView(String failureView) {
		this.failureView = failureView;
	}

	public void setVouchValidators(List<VouchValidator> validators) {
		this.vouchValidators = validators;
	}

	public UploadVouchTemplateDataController() {}
	
	public UploadVouchTemplateDataController(List<VouchValidator> validators) {
		this.vouchValidators = validators;
		
	}
	
	/**
	 * 用户上传手工记帐申请文件， 系统接受并且验证。
	 */
	protected ModelAndView onSubmit(
	        HttpServletRequest request,
	        HttpServletResponse response,
	        Object command,
	        BindException errors) throws Exception {
			LOG.info("Start");
		
			super.onSubmit(request, response, command, errors);
			
			ModelAndView failureModelAndView = new ModelAndView(failureView)
				.addObject("appContext", request.getContextPath());
			
			ModelAndView successModelAndView = new ModelAndView(getSuccessView())
			.addObject("appContext", request.getContextPath());

			List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
			
	        VouchDataFileUploadBean bean = (VouchDataFileUploadBean) command;
	        MultipartFile file = bean.getFile();
	        
	        //no file or file is empty
	        if (file == null || file.isEmpty()) {
	        	LOG.debug("No file or file is empty");
	        	messages.add(VouchValidateMessage.createFileIsEmptyMessage());
	        	return failureModelAndView.addObject("messages", messages);
	        }

	        //file is not excel
	        String contentType = file.getContentType();
	        //if (!file.getOriginalFilename().endsWith(".xls") 
	        //		|| (contentType != null && contentType.indexOf("excel") == -1)) {
	        if (!file.getOriginalFilename().endsWith(".xls")) {	
	        	LOG.debug("File is not excel");
	        	messages.add(VouchValidateMessage.createFileNotExcelMessage());
	        	return failureModelAndView.addObject("messages", messages);
	        }
	        
	        VouchDataDto vouchDataDto = null;
	        try {
	        	VouchDataLoader vouchDataLoader = getVouchDataLoader(file.getInputStream());
				vouchDataDto = vouchDataLoader.getData();
			} catch (VouchDataInvalidException e) {
				LOG.debug("Vouch data is not correct!", e);
				messages.addAll(e.getMessages());
				return failureModelAndView.addObject("messages", messages);
			} catch (VouchDataBuildException e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchValidateMessage.createValidateMessage("文件内容有错！"));
				return failureModelAndView.addObject("messages", messages);
			} catch (Exception e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchValidateMessage.createValidateMessage("文件内容有错！"));
				return failureModelAndView.addObject("messages", messages);
			}
			saveTempVouchData(request, vouchDataDto);
			LOG.info("End");
			return successModelAndView.addObject("vouchData", vouchDataDto);
	    }
	
	private void saveTempVouchData(HttpServletRequest request, VouchDataDto vouchDataDto) {
		request.getSession(true).setAttribute(TEMP_VOUCH_DATA, vouchDataDto);
	}
	
	protected VouchDataLoader getVouchDataLoader(InputStream inputStream) {
		VouchDataLoader vouchDataLoader = VouchDataLoaderImpl.getInstance(inputStream, vouchValidators);
		return vouchDataLoader;
	}
}
